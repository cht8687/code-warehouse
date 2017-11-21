package threewks.framework.usermanager.controller;

import com.google.common.collect.Lists;
import com.threewks.thundr.http.exception.ForbiddenException;
import com.threewks.thundr.session.Session;
import com.threewks.thundr.user.Roles;
import com.threewks.thundr.user.controller.Authenticated;
import com.threewks.thundr.user.controller.Authorised;
import com.threewks.thundr.view.json.JsonView;
import threewks.framework.usermanager.controller.dto.UpdateUserRequestDto;
import threewks.framework.usermanager.controller.dto.UserDto;
import threewks.framework.usermanager.controller.dto.UserInviteRequestDto;
import threewks.framework.usermanager.model.AppUser;
import threewks.framework.usermanager.service.AppUserService;
import threewks.framework.usermanager.service.UserInviteService;

import static com.threewks.thundr.http.StatusCode.Created;
import static com.threewks.thundr.http.StatusCode.NoContent;
import static threewks.framework.usermanager.controller.dto.transformer.Transformers.TO_USER_DTO;

public class UserController {

    private final AppUserService userService;
    private final UserInviteService userInviteService;

    public UserController(AppUserService userService, UserInviteService userInviteService) {
        this.userService = userService;
        this.userInviteService = userInviteService;
    }

    public JsonView login(Session session, String username, String password) {
        AppUser user = userService.login(session, username, password);
        if (user == null) {
            throw new ForbiddenException("Invalid email or password");
        }
        return new JsonView(TO_USER_DTO.apply(user));
    }

    @Authenticated
    @Authorised(any = Roles.Admin)
    public JsonView listAll() {
        return new JsonView(Lists.transform(userService.listAll(), TO_USER_DTO));
    }

    @Authenticated
    @Authorised(any = Roles.Admin)
    public JsonView search(String email) {
        return new JsonView(Lists.transform(userService.search(email), TO_USER_DTO));
    }

    @Authenticated
    public JsonView me(AppUser user) {
        return new JsonView(TO_USER_DTO.apply(user));
    }

    @Authenticated
    @Authorised(any = Roles.Admin)
    public JsonView get(String username) {
        return new JsonView(TO_USER_DTO.apply(userService.get(username)));
    }

    @Authenticated
    public JsonView save(String username, UpdateUserRequestDto request) {
        UserDto dto = TO_USER_DTO.apply(userService.save(username, request));
        return new JsonView(dto);
    }

    @Authenticated
    @Authorised(any = Roles.Admin)
    public JsonView invite(UserInviteRequestDto requestDto) {
        userInviteService.invite(requestDto.getEmail(), requestDto.getRoles());
        return new JsonView(null).withStatusCode(NoContent);
    }

    public JsonView redeemInvite(Session session, String code, String name, String password) {
        AppUser user = userInviteService.redeem(code, name, password);
        user = userService.login(session, user);
        return new JsonView(TO_USER_DTO.apply(user)).withStatusCode(Created);
    }

    public JsonView logout(Session session) {
        if (session.isAuthenticated()) {
            userService.logout(session, (AppUser) session.getUser());
        }
        return new JsonView(null).withStatusCode(NoContent);
    }
}
