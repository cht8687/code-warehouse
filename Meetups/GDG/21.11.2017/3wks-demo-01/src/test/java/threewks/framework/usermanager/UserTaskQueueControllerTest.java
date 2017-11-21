package threewks.framework.usermanager;

import com.threewks.thundr.view.string.StringView;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import threewks.framework.usermanager.controller.UserTaskQueueController;
import threewks.framework.usermanager.service.MagicLinkService;
import threewks.framework.usermanager.service.UserInviteService;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserTaskQueueControllerTest {

    @Mock
    private UserInviteService userInviteService;

    @Mock
    private MagicLinkService magicLinkService;

    @InjectMocks
    private UserTaskQueueController controller;

    @Test
    public void sendUserInviteEmail() throws Exception {
        String email = "foo@example.org";
        String code = "abc123";
        StringView view = controller.sendUserInviteEmail(email, code);

        assertThat(view.content(), is("OK"));

        verify(userInviteService).sendInviteEmail(email, code);
    }

    @Test
    public void sendMagicLinkEmail() throws Exception {
        String email = "foo@example.org";
        StringView view = controller.sendMagicLinkEmail(email, null);

        assertThat(view.content(), is("OK"));

        verify(magicLinkService).sendMagicLinkEmail(email, null);
    }
}
