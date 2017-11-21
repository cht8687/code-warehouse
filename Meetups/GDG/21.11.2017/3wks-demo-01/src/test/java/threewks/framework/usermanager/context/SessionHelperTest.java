package threewks.framework.usermanager.context;

import com.threewks.thundr.request.Request;
import com.threewks.thundr.request.Response;
import com.threewks.thundr.session.Session;
import com.threewks.thundr.session.SessionService;
import com.threewks.thundr.session.SessionStore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SessionHelperTest {
    @Mock
    private SessionService sessionService;

    @InjectMocks
    private SessionHelper sessionHelper;

    @Mock
    private Session session;
    @Mock
    private Request req;
    @Mock
    private Response resp;

    @Test
    public void getCurrentSession() {
        when(sessionService.getCurrentSession(any(SessionStore.class))).thenReturn(session);

        Session result = sessionHelper.getCurrentSession(req, resp);

        assertThat(result, is(session));
    }

}
