package threewks.framework.usermanager.controller;

import com.threewks.thundr.view.string.StringView;
import threewks.framework.usermanager.service.MagicLinkService;
import threewks.framework.usermanager.service.UserInviteService;

public class UserTaskQueueController {

    private final UserInviteService userInviteService;
    private final MagicLinkService magicLinkService;

    public UserTaskQueueController(UserInviteService userInviteService, MagicLinkService magicLinkService) {
        this.userInviteService = userInviteService;
        this.magicLinkService = magicLinkService;
    }

    public StringView sendUserInviteEmail(String email, String code) {
        userInviteService.sendInviteEmail(email, code);
        return new StringView("OK");
    }

    public StringView sendMagicLinkEmail(String loginIdentifier, String next) {
        magicLinkService.sendMagicLinkEmail(loginIdentifier, next);
        return new StringView("OK");
    }
}
