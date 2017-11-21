package threewks.framework.usermanager.service;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.common.collect.ImmutableMap;
import com.threewks.thundr.logger.Logger;
import com.threewks.thundr.mail.Mailer;
import com.threewks.thundr.route.Route;
import com.threewks.thundr.route.Router;
import com.threewks.thundr.session.Session;
import com.threewks.thundr.view.handlebars.HandlebarsView;
import threewks.framework.usermanager.model.AppUser;
import threewks.framework.usermanager.model.MagicLink;
import threewks.framework.usermanager.repository.MagicLinkRepository;
import threewks.util.Assert;

import java.util.Map;

import static com.googlecode.objectify.ObjectifyService.ofy;
import static com.threewks.thundr.http.URLEncoder.encodeQueryComponent;
import static threewks.util.RandomUtil.secureRandomAlphanumeric;

public class MagicLinkService {

    private static final int MIN_BITS_OF_ENTROPY = 100;
    private static final int CODE_LENGTH = Math.round(MIN_BITS_OF_ENTROPY / Byte.SIZE);
    private static final int TWO_WEEKS_IN_MINUTES = 20160;

    private final MagicLinkRepository magicLinkRepository;
    private final AppUserService userService;
    private final Router router;
    private final Queue queue;
    private final Mailer mailer;
    private final String host;
    private final String mailerSenderEmail;

    private Integer magicLinkExpiryInMinutes;

    public MagicLinkService(MagicLinkRepository magicLinkRepository,
        AppUserService userService,
        Router router,
        Queue queue,
        Mailer mailer,
        String host,
        String mailerSenderEmail,
        String magicLinkExpiryInMinutes) {
        this.magicLinkRepository = magicLinkRepository;
        this.userService = userService;
        this.router = router;
        this.queue = queue;
        this.mailer = mailer;
        this.host = host;
        this.mailerSenderEmail = mailerSenderEmail;
        setMagicNumberExpiry(magicLinkExpiryInMinutes);
    }

    public String createLinkForUser(AppUser user, String next) {
        Assert.notBlank(next, "Cannot create MagicLink with empty nextPage");
        String magicLink = createLinkForUser(user);
        return String.format("%s?next=%s", magicLink, encodeQueryComponent(next));
    }

    public String createLinkForUser(AppUser user) {
        Assert.notNull(user, "Cannot apply MagicLink from a null AppUser");
        String code = generateCode();
        MagicLink magicLink = new MagicLink(code, user, magicLinkExpiryInMinutes);
        magicLinkRepository.put(magicLink);
        return String.format("%s/login/%s", host, code);
    }

    public void requestMagicLink(String loginIdentifier, String next) {
        Route route = router.getNamedRoute("task.notify.sendMagicLink");
        TaskOptions taskOptions = TaskOptions.Builder
            .withUrl(route.getRoute())
            .param("loginIdentifier", loginIdentifier);
        if (next != null) {
            taskOptions = taskOptions.param("next", next);
        }
        queue.add(ofy().getTransaction(), taskOptions);
    }

    public void sendMagicLinkEmail(String loginIdentifier, String next) {
        AppUser user = userService.get(loginIdentifier);
        if (user == null) {
            Logger.error("Sending magic link failed. No such user %s", loginIdentifier);
            return; // fail silently so job doesn't get retry
        }

        String link = next == null
            ? createLinkForUser(user)
            : createLinkForUser(user, next);
        Map<String, Object> model = ImmutableMap.<String, Object>of(
            "magicLink", link);

        mailer.mail()
            .to(user.getEmail())
            .from(mailerSenderEmail)
            .subject("Your News Xtend X2 magic login link")
            .body(new HandlebarsView("user-login-email", model))
            .send();
    }

    public AppUser loginWithCode(Session session, String code) {
        String hashedCode = MagicLink.hash(code);
        MagicLink magicLink = magicLinkRepository.get(hashedCode);
        validateLink(code, magicLink);
        return userService.login(session, magicLink.getUser());
    }

    private void validateLink(String code, MagicLink magicLink) {
        if (magicLink == null) {
            throw new MagicLinkException("Unknown magic code: %s", code);
        }
        if (magicLink.hasExpired()) {
            throw new MagicLinkException("Magic code is no longer valid: %s", code);
        }
    }

    private String generateCode() {
        return secureRandomAlphanumeric(CODE_LENGTH);
    }


    private void setMagicNumberExpiry(String magicLinkExpiryInMinutes) {
        Logger.info(String.format("Magic link expiry set to %s minutes", magicLinkExpiryInMinutes));
        try {
            this.magicLinkExpiryInMinutes = new Integer(magicLinkExpiryInMinutes);
        } catch (NumberFormatException e) {
            this.magicLinkExpiryInMinutes = TWO_WEEKS_IN_MINUTES;
        }
    }

}
