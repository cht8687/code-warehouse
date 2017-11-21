package threewks.framework.usermanager.model;

import org.junit.Before;
import org.junit.Test;
import threewks.TestData;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class AppUserTest {

    private AppUser user;

    @Before
    public void before() {
        user = TestData.user();
    }

    @Test
    public void isActive_willReturnTrue_whenActiveStatus() {
        AppUser user = this.user.setStatus(UserStatus.ACTIVE);
        assertThat(user.isActive(), is(true));
        assertThat(user.isNotActive(), is(false));
    }

    @Test
    public void isActive_willReturnFalse_whenNotActiveStatus() {
        assertThat(user.setStatus(UserStatus.INVITED).isActive(), is(false));
        assertThat(user.setStatus(UserStatus.INVITED).isNotActive(), is(true));

        assertThat(user.setStatus(UserStatus.INACTIVE).isActive(), is(false));
        assertThat(user.setStatus(UserStatus.INACTIVE).isNotActive(), is(true));
    }

}
