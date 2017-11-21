package threewks.framework.usermanager.service;

import com.google.appengine.api.taskqueue.Queue;
import com.threewks.thundr.mail.Mailer;
import com.threewks.thundr.route.Router;
import com.threewks.thundr.session.Session;
import com.threewks.thundr.test.TestSupport;
import com.threewks.thundr.test.mock.mailer.MockMailer;
import com.threewks.thundr.user.gae.SessionGae;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import threewks.framework.usermanager.model.AppUser;
import threewks.framework.usermanager.model.MagicLink;
import threewks.framework.usermanager.repository.MagicLinkRepository;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static threewks.TestData.user;

@RunWith(MockitoJUnitRunner.class)
public class MagicLinkServiceTest {

    private static final String HOST = "http://hostname";
    private static final String SENDER = "sender@example.org";
    private static final String CODE = "ABC123";
    private static final String MAGIC_LINK_EXPIRY = "5";
    private static final Integer TWO_WEEKS_IN_MINUTES = 20160;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private MagicLink magicLink;

    @Mock
    private MagicLinkRepository magicLinkRepository;

    @Mock
    private AppUserService userService;

    @Mock
    private Queue queue;

    @Mock
    private Router router;

    private Session testSession = new SessionGae();
    private AppUser testUser;
    private Mailer mailer;

    private MagicLinkService test;

    @Before
    public void setUp() throws Exception {
        mailer = new MockMailer();

        test = new MagicLinkService(magicLinkRepository, userService, router, queue, mailer, HOST, SENDER, MAGIC_LINK_EXPIRY);
        testUser = user("foo@example.org");
        when(magicLinkRepository.get(isA(String.class))).thenReturn(magicLink);
        when(magicLink.hasExpired()).thenReturn(false);
        when(magicLink.getUser()).thenReturn(testUser);
        when(userService.login(testSession, testUser)).thenReturn(testUser);
    }

    @Test
    public void loginWithCode_willLogUserIn_whenMagicCodeIsFoundAndNotExpired() throws Exception {
        AppUser actualResult = test.loginWithCode(testSession, CODE);

        assertThat(actualResult, is(testUser));
        verify(userService).login(testSession, testUser);
    }

    @Test
    public void loginWithCode_willNotLogin_whenMagicCodeIsNotFound() throws Exception {
        when(magicLinkRepository.get(isA(String.class))).thenReturn(null);

        try {
            test.loginWithCode(testSession, CODE);
            fail("MagicLinkException expected");
        } catch (MagicLinkException e) {
            verify(userService, never()).login(isA(Session.class), isA(AppUser.class));
        }
    }

    @Test
    public void loginWithCode_willThrowException_whenMagicCodeIsNotFound() throws Exception {
        when(magicLinkRepository.get(isA(String.class))).thenReturn(null);

        expectedException.expect(MagicLinkException.class);
        expectedException.expectMessage(String.format("Unknown magic code: %s", CODE));

        test.loginWithCode(testSession, CODE);
    }

    @Test
    public void loginWithCode_willNotLogin_whenMagicCodeHasExpired() throws Exception {
        when(magicLink.hasExpired()).thenReturn(true);

        try {
            test.loginWithCode(testSession, CODE);
            fail("MagicLinkException expected");
        } catch (MagicLinkException e) {
            verify(userService, never()).login(isA(Session.class), isA(AppUser.class));
        }
    }

    @Test
    public void loginWithCode_willThrowException_whenMagicCodeHasExpired() throws Exception {
        when(magicLink.hasExpired()).thenReturn(true);

        expectedException.expect(MagicLinkException.class);
        expectedException.expectMessage(String.format("Magic code is no longer valid: %s", CODE));

        test.loginWithCode(testSession, CODE);
    }

    @Test
    public void constructor_willDefaultMagicLinkExpiryToTwoWeeks_whenNonNumericExpiryEntered() throws Exception {
        test = new MagicLinkService(magicLinkRepository, userService, router, queue, mailer, HOST, SENDER, "not a number");

        Integer actualResult = TestSupport.getField(test, "magicLinkExpiryInMinutes");
        assertThat(actualResult, is(TWO_WEEKS_IN_MINUTES));
    }

    @Test
    public void constructor_willDefaultMagicLinkExpiryToTwoWeeks_whenNullExpiryEntered() throws Exception {
        test = new MagicLinkService(magicLinkRepository, userService, router, queue, mailer, HOST, SENDER, null);

        Integer actualResult = TestSupport.getField(test, "magicLinkExpiryInMinutes");
        assertThat(actualResult, is(TWO_WEEKS_IN_MINUTES));
    }
}

