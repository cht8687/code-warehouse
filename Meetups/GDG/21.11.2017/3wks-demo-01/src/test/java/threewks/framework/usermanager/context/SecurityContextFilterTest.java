package threewks.framework.usermanager.context;

import com.threewks.thundr.request.Request;
import com.threewks.thundr.request.Response;
import com.threewks.thundr.session.Session;
import com.threewks.thundr.user.User;
import com.threewks.thundr.view.View;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import threewks.TestData;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SecurityContextFilterTest {

    @Mock
    private SessionHelper sessionHelper;

    @InjectMocks
    private SecurityContextFilter securityContextFilter;

    @Mock
    private Request req;
    @Mock
    private Response resp;
    @Mock
    private View view;
    @Mock
    private Session session;

    private User user;

    @Before
    public void before() {
        user = TestData.user();
    }

    @After
    // Put it back as we found it!
    public void afterTest() {
        SecurityContextHolder.clear();
    }

    @Test
    public void before_willAddUser_whenRequestDataExists() {
        when(sessionHelper.getCurrentSession(req, resp)).thenReturn(session);
        when(session.getUser()).thenReturn(user);

        Object result = securityContextFilter.before(req, resp);

        assertThat(result, nullValue());
        assertThat(SecurityContextHolder.get().getUser(), is(user));
    }

    @Test
    public void before_willNotSetContext_whenSessionDoesNotHaveUser() {
        when(sessionHelper.getCurrentSession(req, resp)).thenReturn(session);
        when(session.getUser()).thenReturn(null);

        Object result = securityContextFilter.before(req, resp);

        assertThat(result, nullValue());
        assertThat(SecurityContextHolder.get().getUser(), nullValue());
    }

    @Test
    public void before_willNotSetContext_whenSessionIsNull() {
        when(sessionHelper.getCurrentSession(req, resp)).thenReturn(null);

        Object result = securityContextFilter.before(req, resp);

        assertThat(result, nullValue());
        assertThat(SecurityContextHolder.get().getUser(), nullValue());
    }

    @Test
    public void after_willClearSecurityContext() {
        SecurityContextHolder.get().setUser(user);

        Object result = securityContextFilter.after(view, req, resp);

        assertThat(result, nullValue());
        assertThat(SecurityContextHolder.get().getUser(), nullValue());
    }


}
