package threewks.framework.usermanager.service;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.common.collect.ImmutableMap;
import com.googlecode.objectify.Work;
import com.threewks.thundr.logger.Logger;
import com.threewks.thundr.mail.Mailer;
import com.threewks.thundr.route.Route;
import com.threewks.thundr.route.Router;
import com.threewks.thundr.user.Roles;
import com.threewks.thundr.view.handlebars.HandlebarsView;
import threewks.framework.usermanager.context.SecurityContextHolder;
import threewks.framework.usermanager.model.AppUser;
import threewks.framework.usermanager.model.LoginIdentifier;
import threewks.framework.usermanager.model.UserInviteLink;
import threewks.framework.usermanager.repository.UserInviteLinkRepository;
import threewks.util.Assert;

import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.googlecode.objectify.ObjectifyService.ofy;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static threewks.util.RandomUtil.secureRandomAlphanumeric;

public class UserInviteService {
    public static final Roles DEFAULT_ROLES = new Roles(Roles.User);
    public static final int MIN_PASSWORD_LENGTH = 8;

    private final UserInviteLinkRepository userInviteLinkRepository;
    private final LoginIdentifierService loginIdentifierService;
    private final AppUserService userService;
    private final Router router;
    private final Queue queue;
    private final Mailer mailer;
    private final String host;
    private final String mailerSenderEmail;

    public UserInviteService(UserInviteLinkRepository userInviteLinkRepository,
                             LoginIdentifierService loginIdentifierService,
                             AppUserService userService,
                             Router router,
                             Queue queue,
                             Mailer mailer,
                             String host,
                             String mailerSenderEmail) {
        this.userInviteLinkRepository = userInviteLinkRepository;
        this.loginIdentifierService = loginIdentifierService;
        this.userService = userService;
        this.router = router;
        this.queue = queue;
        this.mailer = mailer;
        this.host = host;
        this.mailerSenderEmail = mailerSenderEmail;
    }

    public AppUser invite(final String inviteeEmail, Roles inviteeRoles) {
        final AppUser issuer = SecurityContextHolder.get().getUser();
        Assert.notNull(issuer, "Cannot issue invite. Issuer must be provided.");
        Assert.notBlank(inviteeEmail, "Cannot issue invite. Invitee email address must be provided.");
        Assert.isEmail(inviteeEmail, "Cannot issue invite. Invitee email address must be a valid email address.");

        final Roles roles;
        if (inviteeRoles == null || inviteeRoles.getRoles().isEmpty()) {
            Logger.info("No roles provided. Defaulting to %s", Roles.User);
            roles = new Roles(Roles.User);
        } else {
            roles = inviteeRoles;
        }

        return ofy().transact(() -> {
            LoginIdentifier loginIdentifier = loginIdentifierService.get(inviteeEmail);

            AppUser user = loginIdentifier == null ? userService.registerInvited(inviteeEmail) : loginIdentifier.getUser();

            Assert.isFalse(user.isActive(), "Cannot issue invite. User account already activated.");

            String code = secureRandomAlphanumeric(100);
            UserInviteLink invite = new UserInviteLink(code, issuer, user.getEmail(), roles);
            userInviteLinkRepository.put(invite);

            queueUserInviteEmail(user.getEmail(), code);

            return user;
        });
    }

    public AppUser inviteIfRequired(String inviteeEmail, String... roles) {
        LoginIdentifier loginIdentifier = loginIdentifierService.get(inviteeEmail);
        if (loginIdentifier == null || !loginIdentifier.getUser().isActive()) {
            return invite(inviteeEmail, new Roles(roles));
        }
        return loginIdentifier.getUser();
    }

    public void sendInviteEmail(String email, String code) {
        Map<String, Object> model = ImmutableMap.<String, Object>of(
            "inviteLink", String.format("%s/register/%s", host, code));

        mailer.mail()
            .to(email)
            .from(mailerSenderEmail, "News Xtend")
            .subject("Welcome to News Xtend - complete your account setup")
            .body(new HandlebarsView("user-invite-email", model))
            .send();
    }

    public AppUser redeem(final String code, final String name, final String password) {
        checkArgument(isNotBlank(code), "Invite code is required");
        checkArgument(isNotBlank(name), "Name is required");
        checkArgument(isNotBlank(password), "Password is required");
        Assert.isTrue(password.length() >= MIN_PASSWORD_LENGTH,
            "Password must be %s characters or more in length", MIN_PASSWORD_LENGTH);

        final String hashedCode = UserInviteLink.hash(code);

        return ofy().transact(() -> {
            UserInviteLink invite = userInviteLinkRepository.get(hashedCode);

            Assert.notNull(invite, "Invalid invite code");
            Assert.isFalse(invite.isRedeemed(), "Invite code has already been redeemed");
            Assert.isFalse(invite.hasExpired(), "Invite code has expired. Please ask your administrator to issue you an new one.");

            invite.setRedeemed(true);
            userInviteLinkRepository.put(invite);

            AppUser user = userService.get(invite.getEmail());
            if (user == null) {
                user = userService.registerInvited(invite.getEmail());
            }

            Assert.isFalse(user.isActive(), "Cannot issue invite. User account already activated.");

            user.setRoles(invite.getRoles());
            user.setName(name);
            return userService.activate(user, password);
        });
    }

    private void queueUserInviteEmail(String email, String code) {
        Route route = router.getNamedRoute("task.notify.inviteUser");
        TaskOptions taskOptions = TaskOptions.Builder
            .withUrl(route.getRoute())
            .param("email", email)
            .param("code", code);
        queue.add(ofy().getTransaction(), taskOptions);
    }
}
