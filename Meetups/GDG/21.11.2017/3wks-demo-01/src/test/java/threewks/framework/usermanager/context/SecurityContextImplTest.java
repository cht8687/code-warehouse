package threewks.framework.usermanager.context;

import com.threewks.thundr.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import threewks.TestData;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class SecurityContextImplTest {

    private User user;
    private SecurityContextImpl context;

    @Before
    public void before() {
        context = new SecurityContextImpl();
        user = TestData.user();
    }

    @Test
    public void isAuthenticated_isNotAuthenticated_WhenUserIsNull() {
        context.setUser(null);

        assertThat(context.isAuthenticated(), is(false));
        assertThat(context.isNotAuthenticated(), is(true));
    }

    @Test
    public void isAuthenticated_isNotAuthenticated_WhenUserNotNull() {
        context.setUser(user);

        assertThat(context.isAuthenticated(), is(true));
        assertThat(context.isNotAuthenticated(), is(false));
    }

}
