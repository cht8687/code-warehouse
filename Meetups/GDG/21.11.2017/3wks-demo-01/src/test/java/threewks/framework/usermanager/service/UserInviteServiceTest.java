package threewks.framework.usermanager.service;

import com.atomicleopard.expressive.EList;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.threewks.thundr.mail.MailBuilder;
import com.threewks.thundr.route.HttpMethod;
import com.threewks.thundr.route.Route;
import com.threewks.thundr.route.Router;
import com.threewks.thundr.test.mock.mailer.MockMailer;
import com.threewks.thundr.user.Roles;
import com.threewks.thundr.user.User;
import com.threewks.thundr.view.handlebars.HandlebarsView;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import threewks.BaseTest;
import threewks.MockHelpers;
import threewks.framework.usermanager.context.SecurityContextHolder;
import threewks.framework.usermanager.model.AppUser;
import threewks.framework.usermanager.model.LoginIdentifier;
import threewks.framework.usermanager.model.UserInviteLink;
import threewks.framework.usermanager.model.UserStatus;
import threewks.framework.usermanager.repository.UserInviteLinkRepository;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static threewks.TestData.user;

public class UserInviteServiceTest extends BaseTest {

    private static final String HOST = "localhost";
    private static final String SENDER_EMAIL = "sender@example.org";

    @Mock
    private UserInviteLinkRepository userInviteLinkRepository;

    @Mock
    private LoginIdentifierService loginIdentifierService;

    @Mock
    private AppUserService userService;

    @Mock
    private Router router;

    @Mock
    private Queue queue;

    @Captor
    private ArgumentCaptor<UserInviteLink> userInviteLinkArg;

    @Captor
    private ArgumentCaptor<TaskOptions> taskOptionsArg;

    private MockMailer mailer;
    private Route inviteUserRoute;
    private UserInviteService userInviteService;

    @Before
    public void setUp() throws Exception {
        inviteUserRoute = new Route(HttpMethod.POST, "/task/send-invite", "task.notify.inviteUser");
        when(router.getNamedRoute(inviteUserRoute.getName())).thenReturn(inviteUserRoute);

        MockHelpers.returnFirstArgument(userService.activate(any(AppUser.class), anyString()));
        MockHelpers.returnFirstArgument(userService.register(any(AppUser.class), anyString()));

        when(userService.registerInvited(anyString())).thenAnswer((Answer<User>) invocation -> user((String) invocation.getArguments()[0]));

        mailer = new MockMailer();

        userInviteService = new UserInviteService(
            userInviteLinkRepository,
            loginIdentifierService,
            userService,
            router,
            queue,
            mailer,
            HOST,
            SENDER_EMAIL);
    }

    @Test
    public void invite_willPlaceTaskOnQueue() throws Exception {
        AppUser issuer = user("foo@example.org");
        String inviteeEmail = "bar@example.org";
        Roles inviteeRoles = new Roles(Roles.User);
        SecurityContextHolder.get().setUser(issuer);

        userInviteService.invite(inviteeEmail, inviteeRoles);


        verify(userInviteLinkRepository).put(userInviteLinkArg.capture());
        UserInviteLink link = userInviteLinkArg.getValue();
        assertThat(link.getEmail(), is(inviteeEmail));
        assertThat(link.getRoles(), is(inviteeRoles));
        assertThat(link.getHashedCode(), is(notNullValue()));

        verify(queue).add(any(Transaction.class), taskOptionsArg.capture());

        TaskOptions taskOptions = taskOptionsArg.getValue();
        assertThat(taskOptions.getUrl(), is(inviteUserRoute.getRoute()));

        Map<String, List<String>> params = taskOptions.getStringParams();
        assertThat(params.get("email"), is(Collections.singletonList(inviteeEmail)));

        List<String> codes = params.get("code");
        assertThat(codes, hasSize(1));
        assertThat(UserInviteLink.hash(codes.get(0)), is(link.getHashedCode()));
    }

    @Test
    public void invite_willRegisterInvitedUser_whenUserDoesNotExist() throws Exception {
        AppUser issuer = user("foo@example.org");
        String inviteeEmail = "bar@example.org";
        Roles inviteeRoles = new Roles(Roles.User);
        SecurityContextHolder.get().setUser(issuer);

        AppUser invitee = userInviteService.invite(inviteeEmail, inviteeRoles);

        assertThat(invitee, is(notNullValue()));
        assertThat(invitee.getEmail(), is(inviteeEmail));
    }

    @Test
    public void invite_willSucceed_whenInactiveUserAlreadyRegistered() throws Exception {
        String inviteeEmail = "bar@example.org";

        AppUser user = user(inviteeEmail);
        user.setStatus(UserStatus.INVITED);

        LoginIdentifier loginIdentifier = spy(new LoginIdentifier(user));
        when(loginIdentifier.getUser()).thenReturn(user);

        when(loginIdentifierService.get(inviteeEmail)).thenReturn(loginIdentifier);

        AppUser issuer = user("foo@example.org");
        Roles inviteeRoles = new Roles(Roles.User);
        SecurityContextHolder.get().setUser(issuer);


        AppUser invitee = userInviteService.invite(inviteeEmail, inviteeRoles);


        assertThat(invitee, is(notNullValue()));
        assertThat(invitee.getEmail(), is(inviteeEmail));
    }

    @Test
    public void invite_willFail_whenUserAlreadyActive() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Cannot issue invite. User account already activated.");

        String inviteeEmail = "bar@example.org";

        AppUser user = user(inviteeEmail);
        user.setStatus(UserStatus.ACTIVE);
        LoginIdentifier loginIdentifier = spy(new LoginIdentifier(user));
        when(loginIdentifier.getUser()).thenReturn(user);

        when(loginIdentifierService.get(inviteeEmail)).thenReturn(loginIdentifier);

        AppUser issuer = user("foo@example.org");
        Roles inviteeRoles = new Roles(Roles.User);
        SecurityContextHolder.get().setUser(issuer);

        userInviteService.invite(inviteeEmail, inviteeRoles);
    }

    @Test
    public void invite_willDefaultRoleToUser_whenRolesIsNull() throws Exception {
        AppUser issuer = user("foo@example.org");
        String inviteeEmail = "bar@example.org";
        SecurityContextHolder.get().setUser(issuer);

        userInviteService.invite(inviteeEmail, null);

        verify(userInviteLinkRepository).put(userInviteLinkArg.capture());
        UserInviteLink link = userInviteLinkArg.getValue();
        assertThat(link.getRoles(), is(UserInviteService.DEFAULT_ROLES));
    }

    @Test
    public void invite_willThrowException_whenInviteeIssuerIsNull() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Cannot issue invite. Issuer must be provided.");

        userInviteService.invite(null, null);
    }

    @Test
    public void invite_willThrowException_whenInviteeInviteeEmailIsNull() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Cannot issue invite. Invitee email address must be provided.");

        AppUser issuer = user("foo@example.org");
        SecurityContextHolder.get().setUser(issuer);

        userInviteService.invite(null, null);
    }

    @Test
    public void invite_willThrowException_whenInviteeInviteeEmailIsBlank() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Cannot issue invite. Invitee email address must be provided.");

        AppUser issuer = user("foo@example.org");
        SecurityContextHolder.get().setUser(issuer);

        userInviteService.invite(" ", null);
    }

    @Test
    public void invite_willThrowException_whenInviteeInviteeEmailIsInvalid() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Cannot issue invite. Invitee email address must be a valid email address.");

        AppUser issuer = user("foo@example.org");
        SecurityContextHolder.get().setUser(issuer);

        userInviteService.invite("bar@", null);
    }

    @Test
    public void inviteIfRequired_willInvokeInvite_whenLoginIdentifierIsNull() throws Exception {
        AppUser issuer = user("foo@example.org");
        String inviteeEmail = "bar@example.org";
        SecurityContextHolder.get().setUser(issuer);
        UserInviteService userInviteService = spy(this.userInviteService);

        when(loginIdentifierService.get(inviteeEmail)).thenReturn(null);

        userInviteService.inviteIfRequired(inviteeEmail, Roles.User);

        verify(userInviteService).invite(inviteeEmail, new Roles(Roles.User));
    }

    @Test
    public void inviteIfRequired_willReturnUser_whenUserIsActive() throws Exception {
        AppUser issuer = user("foo@example.org");
        String inviteeEmail = "bar@example.org";
        AppUser user = user(inviteeEmail);
        user.setStatus(UserStatus.ACTIVE);
        LoginIdentifier loginIdentifier = spy(new LoginIdentifier(user));
        SecurityContextHolder.get().setUser(issuer);
        UserInviteService userInviteService = spy(this.userInviteService);

        when(loginIdentifierService.get(inviteeEmail)).thenReturn(loginIdentifier);
        when(loginIdentifier.getUser()).thenReturn(user);

        AppUser result = userInviteService.inviteIfRequired(inviteeEmail, Roles.User);

        verify(userInviteService, never()).invite(inviteeEmail, new Roles(Roles.User));
        assertThat(result, is(user));
    }

    @Test
    public void sendInviteEmail() throws Exception {
        String inviteeEmail = "email";
        String inviteCode = "code";
        userInviteService.sendInviteEmail(inviteeEmail, inviteCode);

        EList<MailBuilder> sent = mailer.getSent();
        MailBuilder email = sent.first();
        assertThat(email.from(), is(Collections.singletonMap(SENDER_EMAIL, "News Xtend").entrySet().iterator().next()));
        assertThat(email.to(), is(Collections.<String, String>singletonMap(inviteeEmail, null)));
        assertThat(email.subject(), is("Welcome to News Xtend - complete your account setup"));
        assertThat(email.body(), is(notNullValue()));
        assertThat(email.body(), is(instanceOf(HandlebarsView.class)));

        HandlebarsView body = email.body();
        assertThat(body.getView(), is("/WEB-INF/hbs/user-invite-email.hbs"));
        assertThat(body.getModel(), hasEntry("inviteLink", (Object) String.format("%s/register/%s", HOST, inviteCode)));
    }

    @Test
    public void redeem() throws Exception {
        String code = "code";
        String email = "bar@example.org";
        AppUser issuer = user("foo@example.org");
        Roles roles = new Roles(Roles.User);
        UserInviteLink inviteLink = new UserInviteLink(code, issuer, email, roles);

        String hashedCode = UserInviteLink.hash(code);
        when(userInviteLinkRepository.get(hashedCode)).thenReturn(inviteLink);

        AppUser user = userInviteService.redeem(code, "name", "password");

        assertThat(inviteLink.isRedeemed(), is(true));

        assertThat(user, is(notNullValue()));
        assertThat(user.getEmail(), is(email));
        assertThat(user.getName(), is("name"));
        assertThat(user.getRoles(), is(roles));
    }

    @Test
    public void redeem_willThrowError_whenCodeIsNull() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invite code is required");

        userInviteService.redeem(null, null, null);
    }

    @Test
    public void redeem_willThrowError_whenCodeIsBlank() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invite code is required");

        userInviteService.redeem(" ", null, null);
    }

    @Test
    public void redeem_willThrowError_whenNameIsNull() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Name is required");

        userInviteService.redeem("code", null, null);
    }

    @Test
    public void redeem_willThrowError_whenNameIsBlank() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Name is required");

        userInviteService.redeem("code", " ", null);
    }

    @Test
    public void redeem_willThrowError_whenPasswordIsNull() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Password is required");

        userInviteService.redeem("code", "name", null);
    }

    @Test
    public void redeem_willThrowError_whenPasswordIsBlank() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Password is required");

        userInviteService.redeem("code", "name", " ");
    }

    @Test
    public void redeem_willThrowError_whenPasswordIsShorterThanMinimum() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Password must be 8 characters or more in length");

        userInviteService.redeem("code", "name", "abc");
    }

    @Test
    public void redeem_willThrowError_whenUserAlreadyActivated() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Cannot issue invite. User account already activated.");

        String code = "code";
        String email = "bar@example.org";
        AppUser issuer = user("foo@example.org");
        Roles roles = new Roles(Roles.User);
        UserInviteLink inviteLink = new UserInviteLink(code, issuer, email, roles);
        when(userService.registerInvited(anyString())).thenAnswer((Answer<User>) invocation -> user((String) invocation.getArguments()[0])
            .setStatus(UserStatus.ACTIVE));

        String hashedCode = UserInviteLink.hash(code);
        when(userInviteLinkRepository.get(hashedCode)).thenReturn(inviteLink);

        userInviteService.redeem(code, "name", "password");
    }

    @Test
    public void redeem_willThrowError_whenCodeIsInvalid() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invalid invite code");

        String code = "invalid";

        String hashedCode = UserInviteLink.hash(code);
        when(userInviteLinkRepository.get(hashedCode)).thenReturn(null);

        userInviteService.redeem(code, "name", "password");
    }

    @Test
    public void redeem_willThrowError_whenCodeIsExpired() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invite code has expired. Please ask your administrator to issue you an new one.");

        String code = "invalid";
        String email = "bar@example.org";
        AppUser issuer = user("foo@example.org");
        Roles roles = new Roles(Roles.User);
        UserInviteLink inviteLink = new UserInviteLink(code, issuer, email, roles);

        DateTimeUtils.setCurrentMillisFixed(DateTime.now().plusDays(2).getMillis() + 1);

        String hashedCode = UserInviteLink.hash(code);
        when(userInviteLinkRepository.get(hashedCode)).thenReturn(inviteLink);

        userInviteService.redeem(code, "name", "password");
    }
}
