package threewks.framework.usermanager.controller;

import com.threewks.thundr.http.StatusCode;
import com.threewks.thundr.session.Session;
import com.threewks.thundr.view.json.JsonView;
import com.threewks.thundr.view.redirect.RedirectView;
import org.apache.commons.lang3.StringUtils;
import threewks.framework.usermanager.service.MagicLinkService;

public class MagicLinkController {

    private final MagicLinkService magicLinkService;

    public MagicLinkController(MagicLinkService magicLinkService) {
        this.magicLinkService = magicLinkService;
    }

    public JsonView requestMagicLink(String email, String next) {
        magicLinkService.requestMagicLink(email, next);
        return new JsonView(null).withStatusCode(StatusCode.NoContent);
    }

    public RedirectView login(Session session, String magicCode, String next) {
        magicLinkService.loginWithCode(session, magicCode);
        return new RedirectView(getNextPage(next));
    }

    private String getNextPage(String nextPage) {
        return StringUtils.isNotBlank(nextPage) ? nextPage : "/admin";
    }
}
