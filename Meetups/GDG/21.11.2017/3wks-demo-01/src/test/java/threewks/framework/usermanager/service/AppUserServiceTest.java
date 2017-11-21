package threewks.framework.usermanager.service;

import com.google.common.collect.Sets;
import com.threewks.thundr.http.exception.ForbiddenException;
import com.threewks.thundr.session.Session;
import com.threewks.thundr.user.Roles;
import com.threewks.thundr.user.authentication.Authentication;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import threewks.BaseTest;
import threewks.TestData;
import threewks.framework.usermanager.context.SecurityContextHolder;
import threewks.framework.usermanager.controller.dto.UpdateUserRequestDto;
import threewks.framework.usermanager.model.AppUser;
import threewks.framework.usermanager.model.LoginIdentifier;
import threewks.framework.usermanager.model.UserStatus;
import threewks.framework.usermanager.repository.AppUserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static java.util.UUID.randomUUID;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static threewks.MockHelpers.returnFirstArgument;
import static threewks.TestData.user;

public class AppUserServiceTest extends BaseTest {

    private static final String EMAIL = "foo@example.org";
    private static final String PASSWORD = "password";

    private AppUserService userService;

    @Mock
    private Session session;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private AppUserRepository<AppUser> userRepository;

    @Mock
    private com.threewks.thundr.user.UserService<AppUser, Session> thundrUserService;

    @Mock
    private LoginIdentifierService loginIdentifierService;

    private AppUser user;

    @Before
    public void setUp() throws Exception {
        user = user(EMAIL);
        LoginIdentifier loginIdentifier = spy(new LoginIdentifier(user));

        when(loginIdentifier.getUser()).thenReturn(user);
        when(loginIdentifierService.get(EMAIL)).thenReturn(loginIdentifier);
        doThrow(LoginIdentifierUnavailableException.class).when(loginIdentifierService).checkAvailability(EMAIL);

        when(thundrUserService.login(any(Session.class), any(Authentication.class), eq(PASSWORD))).thenReturn(user);
        returnFirstArgument(thundrUserService.put(any(AppUser.class), any(Authentication.class)));

        returnFirstArgument(userRepository.put(any(AppUser.class)));
        when(thundrUserService.get(user.getUsername())).thenReturn(user);

        userService = new AppUserService(userRepository, thundrUserService, loginIdentifierService);
    }

    @Test
    public void activate() throws Exception {
        String email = "bar@example.org";
        AppUser user = userService.activate(user(email), "password");

        assertThat(user, notNullValue());
        assertThat(user.getEmail(), is(email));
        assertThat(user.isActive(), is(true));
        assertThat(user.getStatus(), is(UserStatus.ACTIVE));
    }

    @Test
    public void activate_willFail_whenUserIsNull() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Cannot activate. User is required");

        userService.activate(null, null);
    }

    @Test
    public void activate_willFail_whenPasswordIsNull() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Cannot activate. Password is required");

        userService.activate(user("bar@example.org"), null);
    }

    @Test
    public void activate_willFail_whenPasswordIsBlank() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Cannot activate. Password is required");

        userService.activate(user("bar@example.org"), " ");
    }

    @Test
    public void registerInvited() throws Exception {
        String email = "bar@example.org";
        AppUser user = userService.registerInvited(email);

        assertThat(user, notNullValue());
        assertThat(user.getEmail(), is(email));
        assertThat(user.getStatus(), is(UserStatus.INVITED));
        assertThat(user.isActive(), is(false));
        assertThat(user.getRoles(), is(new Roles()));
    }

    @Test
    public void registerInvited_willFail_whenEmailIsNull() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Email is required");

        userService.registerInvited(null);
    }

    @Test
    public void registerInvited_willFail_whenEmailIsBlank() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Email is required");

        userService.registerInvited(" ");
    }

    @Test
    public void register() throws Exception {
        AppUser user = user("bar@example.org");

        AppUser registeredUser = userService.register(user, PASSWORD);

        assertThat(registeredUser, notNullValue());
    }

    @Test
    public void register_willThrowException_whenLoginIdentifierUnavailable() throws Exception {
        thrown.expect(LoginIdentifierUnavailableException.class);

        userService.register(user, PASSWORD);
    }

    @Test
    public void register_willThrowException_whenUserIsNull() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("User is required");

        userService.register(null, PASSWORD);
    }

    @Test
    public void register_willThrowException_whenEmailIsNull() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Email is required");

        userService.register(new AppUser(randomUUID().toString()), PASSWORD);
    }

    @Test
    public void register_willThrowException_whenEmailIsInvalid() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Must provide a valid email");

        AppUser user = new AppUser(randomUUID().toString());
        user.setEmail("foo@example");

        userService.register(user, PASSWORD);
    }

    @Test
    public void register_willLowerCaseEmail_whenEmailIsMixedCase() throws Exception {
        String email = "Foo.Bar@example.org";
        AppUser user = new AppUser(randomUUID().toString());
        user.setEmail(email);

        AppUser saved = userService.register(user, PASSWORD);

        assertThat(saved.getEmail(), is(email.toLowerCase()));
    }

    @Test
    public void register_willThrowException_whenPasswordIsNull() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Password is required");

        userService.register(user, null);
    }

    @Test
    public void login_willReturnUser_whenCredentialsMatch_andUserIsActive() throws Exception {
        user.setStatus(UserStatus.ACTIVE);

        AppUser result = userService.login(session, EMAIL, PASSWORD);

        assertThat(result, notNullValue());
    }

    @Test
    public void login_willNotReturnUser_whenUserIsNotActive() throws Exception {
        user.setStatus(UserStatus.INVITED);

        AppUser result = userService.login(session, EMAIL, PASSWORD);

        assertThat(result, nullValue());
    }

    @Test
    public void login_willReturnNull_whenUserDoesNotExist() throws Exception {
        AppUser user = userService.login(session, "bar@example.org", PASSWORD);

        assertThat(user, is(nullValue()));
    }

    @Test
    public void login_willReturnNull_whenPasswordInvalid() throws Exception {
        AppUser user = userService.login(session, EMAIL, "invalid");

        assertThat(user, is(nullValue()));
    }

    @Test
    public void login_willThrowException_whenLoginIdentifierIsNull() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Email is required");

        userService.login(session, null, "invalid");
    }

    @Test
    public void save_willSucceed_whenUpdatingSelf() throws Exception {
        SecurityContextHolder.get().setUser(user);
        String newEmail = "bar@example.org";
        Set<String> roles = Sets.newHashSet("bloop");
        String newName = "Foo Bar";

        UpdateUserRequestDto request = new UpdateUserRequestDto()
            .setEmail(newEmail)
            .setRoles(roles)
            .setName(newName);

        AppUser saved = userService.save(user.getUsername(), request);

        assertThat(saved, notNullValue());
        assertThat(saved.getUsername(), is(user.getUsername()));
        assertThat(saved.getEmail(), is(newEmail));
        assertThat(saved.getRoles(), is(new Roles(roles.toArray(new String[roles.size()]))));
        assertThat(saved.getName(), is(newName));
    }

    @Test
    public void save_willSucceed_whenUpdatingAnotherUserAndAdmin() throws Exception {
        String newName = "Foo Bar";

        UpdateUserRequestDto request = new UpdateUserRequestDto()
            .setName(newName);

        AppUser loggedInUser = user("bar@example.org");
        loggedInUser.setRoles(new Roles(Roles.Admin));
        SecurityContextHolder.get().setUser(loggedInUser);

        AppUser saved = userService.save(user.getUsername(), request);

        assertThat(saved, notNullValue());
        assertThat(saved.getUsername(), is(user.getUsername()));
        assertThat(saved.getName(), is(newName));
    }

    @Test
    public void save_willFail_whenUpdatingAnotherUserAndNotAdmin() throws Exception {
        thrown.expect(ForbiddenException.class);
        thrown.expectMessage("Not permitted");

        UpdateUserRequestDto request = new UpdateUserRequestDto().setName("Foo Bar");
        AppUser loggedInUser = user("bar@example.org");
        SecurityContextHolder.get().setUser(loggedInUser);

        userService.save(user.getUsername(), request);
    }

    @Test
    public void save_willFail_whenEmailIsInvalid() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Must provide a valid email");

        SecurityContextHolder.get().setUser(user);
        String newEmail = "foo@example";
        Set<String> roles = Sets.newHashSet("bloop");
        String newName = "Foo Bar";

        UpdateUserRequestDto request = new UpdateUserRequestDto()
            .setEmail(newEmail)
            .setRoles(roles)
            .setName(newName);

        userService.save(user.getUsername(), request);
    }

    @Test
    public void save_willLowerCaseEmail_whenEmailIsMixedCase() throws Exception {
        SecurityContextHolder.get().setUser(user);
        String newEmail = "Foo.Bar@example.org";
        Set<String> roles = Sets.newHashSet("bloop");
        String newName = "Foo Bar";

        UpdateUserRequestDto request = new UpdateUserRequestDto()
            .setEmail(newEmail)
            .setRoles(roles)
            .setName(newName);

        AppUser saved = userService.save(user.getUsername(), request);

        assertThat(saved.getEmail(), is(newEmail.toLowerCase()));
    }

    @Test
    public void list() {
        List<AppUser> users = Arrays.asList(TestData.user("user1@email.com"), TestData.user());
        when(userRepository.list()).thenReturn(users);

        List<AppUser> result = userService.list();

        assertThat(result, is(users));
    }

    @Test
    public void listAll() {
        List<AppUser> users = Arrays.asList(TestData.user("user1@email.com"), TestData.user());
        when(userRepository.listAll()).thenReturn(users);

        List<AppUser> result = userService.listAll();

        assertThat(result, is(users));
    }

    @Test
    public void search() throws Exception {
        List<AppUser> expected = new ArrayList<>();
        doReturn(expected).when(userRepository).searchByEmail("some-email");

        List<AppUser> result = userService.search("some-email");

        assertThat(result, is(expected));
    }
}
