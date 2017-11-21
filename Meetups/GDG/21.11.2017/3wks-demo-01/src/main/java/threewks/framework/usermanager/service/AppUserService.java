package threewks.framework.usermanager.service;

import com.googlecode.objectify.Work;
import com.threewks.thundr.http.exception.ForbiddenException;
import com.threewks.thundr.session.Session;
import com.threewks.thundr.user.Roles;
import com.threewks.thundr.user.UserService;
import com.threewks.thundr.user.authentication.Authentication;
import com.threewks.thundr.user.gae.authentication.PasswordAuthentication;
import org.apache.commons.lang3.StringUtils;
import threewks.framework.usermanager.context.SecurityContextHolder;
import threewks.framework.usermanager.controller.dto.UpdateUserRequestDto;
import threewks.framework.usermanager.model.AppUser;
import threewks.framework.usermanager.model.LoginIdentifier;
import threewks.framework.usermanager.model.UserStatus;
import threewks.framework.usermanager.repository.AppUserRepository;
import threewks.util.Assert;

import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static com.googlecode.objectify.ObjectifyService.ofy;
import static java.util.UUID.randomUUID;
import static threewks.util.RandomUtil.secureRandomAlphanumeric;

public class AppUserService {
    private final UserService<AppUser, Session> thundrUserService;
    private final AppUserRepository<AppUser> userRepository;
    private final LoginIdentifierService loginIdentifierService;

    public AppUserService(AppUserRepository<AppUser> userRepository,
                          UserService<AppUser, Session> thundrUserService,
                          LoginIdentifierService loginIdentifierService) {
        this.thundrUserService = thundrUserService;
        this.userRepository = userRepository;
        this.loginIdentifierService = loginIdentifierService;
    }

    public AppUser activate(final AppUser user, final String password) {
        Assert.notNull(user, "Cannot activate. User is required");
        Assert.notBlank(password, "Cannot activate. Password is required");

        user.setStatus(UserStatus.ACTIVE);

        Authentication authentication = new PasswordAuthentication(user.getUsername(), password);
        return thundrUserService.put(user, authentication);
    }

    /**
     * Create a placeholder user that will be activated at a later point. They intentionally are lacking roles, name etc.
     * These details will be completed at the point of activation.
     */
    public AppUser registerInvited(String email) {
        AppUser user = new AppUser(randomUUID().toString());
        user.setEmail(email);
        user.setStatus(UserStatus.INVITED);
        return this.register(user, secureRandomAlphanumeric(12));
    }

    public AppUser register(final AppUser user, final String password) {
        Assert.notNull(user, "User is required");
        Assert.notBlank(user.getEmail(), "Email is required");
        Assert.isEmail(user.getEmail(), "Must provide a valid email");
        Assert.notBlank(password, "Password is required");

        user.setEmail(user.getEmail().toLowerCase());  // ensure email is lowercase

        return ofy().transact(() -> {
            loginIdentifierService.checkAvailability(user.getEmail());

            LoginIdentifier loginIdentifier = new LoginIdentifier(user);
            loginIdentifierService.put(loginIdentifier);

            Authentication authentication = new PasswordAuthentication(user.getUsername(), password);
            return thundrUserService.put(user, authentication);
        });
    }

    public AppUser login(Session session, String loginIdentifier, String password) {
        Assert.notBlank(loginIdentifier, "Email is required");
        LoginIdentifier mapping = loginIdentifierService.get(loginIdentifier);
        if (mapping == null || mapping.getUser().isNotActive()) {
            return null;
        }

        String username = mapping.getUser().getUsername();
        Authentication authentication = new PasswordAuthentication(username, password);
        return thundrUserService.login(session, authentication, password);
    }

    public AppUser login(Session session, AppUser user) {
        return thundrUserService.login(session, user);
    }

    public void logout(Session session, AppUser user) {
        thundrUserService.logout(session, user);
    }

    public AppUser get(String loginIdentifierOrUsername) {
        LoginIdentifier mapping = loginIdentifierService.get(loginIdentifierOrUsername);
        AppUser user = mapping == null ? null : mapping.getUser();
        if (user == null) {
            user = thundrUserService.get(loginIdentifierOrUsername);
        }
        return user;
    }

    /**
     * List all users, including those who are not active. Intended for admin/system users.
     *
     * @return All users, regardless of status.
     */
    public List<AppUser> listAll() {
        return userRepository.listAll();
    }

    public List<AppUser> search(String email) {
        return userRepository.searchByEmail(email);
    }

    /**
     * List all active users. Default implementation available to unprivileged users.
     *
     * @return All active users
     */
    public List<AppUser> list() {
        return userRepository.list();
    }

    public AppUser save(final String username, final UpdateUserRequestDto request) {
        Assert.notNull(request, "AppUser update request must not be null");

        checkIsSelfOrAdmin(username);

        return ofy().transact(() -> {
            AppUser user = thundrUserService.get(username);
            Assert.entityExists(user, AppUser.class, username);

            String normalisedEmail = request.getEmail() != null ? request.getEmail().toLowerCase() : null;
            if (normalisedEmail != null && !StringUtils.equals(user.getEmail(), normalisedEmail)) {
                Assert.isEmail(request.getEmail(), "Must provide a valid email");

                user.setEmail(normalisedEmail);

                LoginIdentifier loginIdentifier = loginIdentifierService.get(user.getEmail());
                loginIdentifierService.delete(loginIdentifier);
                loginIdentifierService.put(new LoginIdentifier(user));
            }

            Set<String> roles = request.getRoles();
            user.setRoles(new Roles(roles.toArray(new String[roles.size()])));
            user.setName(request.getName());

            return userRepository.put(user);
        });
    }

    private static void checkIsSelfOrAdmin(String username) {
        AppUser loggedInUser = SecurityContextHolder.get().getUser();
        if (loggedInUser.getRoles().hasAnyRole(Roles.Super, Roles.Admin)) {
            return;
        }
        if (StringUtils.equals(loggedInUser.getUsername(), username)) {
            return;
        }
        throw new ForbiddenException("Not permitted");
    }
}
