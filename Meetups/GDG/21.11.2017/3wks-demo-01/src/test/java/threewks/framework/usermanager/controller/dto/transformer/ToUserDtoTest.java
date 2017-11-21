package threewks.framework.usermanager.controller.dto.transformer;

import com.threewks.thundr.user.Roles;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import threewks.framework.usermanager.controller.dto.UserDto;
import threewks.framework.usermanager.model.AppUser;
import threewks.framework.usermanager.model.UserStatus;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ToUserDtoTest {

    private ToUserDto transformer;

    @Before
    public void setUp() throws Exception {
        transformer = new ToUserDto();
    }

    @Test
    public void apply_willTransformUser() throws Exception {
        AppUser user = new AppUser("foo");
        user.setEmail("foo@example.org");
        user.setLastLogin(DateTime.now());
        user.setName("name");
        user.setStatus(UserStatus.INVITED);
        user.setRoles(new Roles(Roles.Admin));

        UserDto dto = transformer.apply(user);

        assertThat(dto, is(notNullValue()));
        assertThat(dto.getUsername(), is(user.getUsername()));
        assertThat(dto.getEmail(), is(user.getEmail()));
        assertThat(dto.getCreated(), is(user.getCreated()));
        assertThat(dto.getLastLogin(), is(user.getLastLogin()));
        assertThat(dto.getName(), is(user.getName()));
        assertThat(dto.getStatus(), is(user.getStatus()));
        assertThat(dto.isActive(), is(user.isActive()));

        assertThat(dto.getRoles(), contains(Roles.Admin));
    }

    @Test
    public void apply_willReturnNull_whenInputIsNull() throws Exception {
        UserDto dto = transformer.apply(null);

        assertThat(dto, is(nullValue()));
    }
}
