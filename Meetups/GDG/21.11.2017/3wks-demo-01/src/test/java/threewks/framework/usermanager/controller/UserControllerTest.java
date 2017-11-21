package threewks.framework.usermanager.controller;

import com.threewks.thundr.http.StatusCode;
import com.threewks.thundr.user.controller.Authenticated;
import com.threewks.thundr.user.controller.Authorised;
import com.threewks.thundr.view.json.JsonView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import threewks.framework.usermanager.controller.dto.UserDto;
import threewks.framework.usermanager.model.AppUser;
import threewks.framework.usermanager.service.AppUserService;
import threewks.framework.usermanager.service.UserInviteService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static threewks.Matchers.hasMethodAnnotation;
import static threewks.TestData.user;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @Mock
    private AppUserService userService;

    @Mock
    private UserInviteService userInviteService;

    private UserController userController;

    @Before
    public void setUp() throws Exception {
        userController = new UserController(userService, userInviteService);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void list() throws Exception {
        assertThat(UserController.class, hasMethodAnnotation("listAll", Authenticated.class));
        assertThat(UserController.class, hasMethodAnnotation("listAll", Authorised.class));

        AppUser user1 = user("foo@example.org");
        AppUser user2 = user("bar@example.org");
        List<AppUser> users = Arrays.asList(user1, user2);
        when(userService.listAll()).thenReturn(users);

        JsonView view = userController.listAll();

        assertThat(view, is(notNullValue()));
        assertThat(view.getStatusCode(), is(StatusCode.OK));
        assertThat(view.getOutput(), is(instanceOf(List.class)));

        List<UserDto> output = (List<UserDto>) view.getOutput();
        assertThat(output, hasSize(output.size()));
        assertThat(output.get(0).getEmail(), is(user1.getEmail()));
        assertThat(output.get(1).getEmail(), is(user2.getEmail()));
    }

    @Test
    public void me() throws Exception {
        assertThat(UserController.class, hasMethodAnnotation("me", Authenticated.class));

        JsonView view = userController.me(user("foo@example.org"));
        assertThat(view, is(notNullValue()));
    }
}
