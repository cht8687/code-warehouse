package threewks.framework.usermanager.controller;

import com.threewks.thundr.http.StatusCode;
import com.threewks.thundr.session.Session;
import com.threewks.thundr.view.json.JsonView;
import com.threewks.thundr.view.redirect.RedirectView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import threewks.framework.usermanager.service.MagicLinkService;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class MagicLinkControllerTest {

    @Mock
    private MagicLinkService magicLinkService;

    @Mock
    private Session session;

    private MagicLinkController magicLinkController;

    @Before
    public void setUp() throws Exception {
        magicLinkController = new MagicLinkController(magicLinkService);
    }

    @Test
    public void requestMagicLink() throws Exception {
        JsonView view = magicLinkController.requestMagicLink("foo@example.org", null);

        assertThat(view, is(notNullValue()));
        assertThat(view.getStatusCode(), is(StatusCode.NoContent));
        assertThat(view.getOutput(), is(nullValue()));
    }

    @Test
    public void login() throws Exception {
        RedirectView view = magicLinkController.login(session, "dontyouknowitsmagic", null);

        assertThat(view, is(notNullValue()));
        assertThat(view.getRedirect(), is("/admin"));
    }

    @Test
    public void login_willReturnCustomRedirect_whenNextParameterSupplied() throws Exception {
        RedirectView view = magicLinkController.login(session, "dontyouknowitsmagic", "/thingy");

        assertThat(view, is(notNullValue()));
        assertThat(view.getRedirect(), is("/thingy"));
    }
}
