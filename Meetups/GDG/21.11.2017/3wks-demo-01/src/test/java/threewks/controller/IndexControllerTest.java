package threewks.controller;

import com.threewks.thundr.http.exception.NotFoundException;
import com.threewks.thundr.request.Request;
import com.threewks.thundr.view.handlebars.HandlebarsView;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.net.URI;
import java.util.Map;

import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@SuppressWarnings("unchecked")
@RunWith(MockitoJUnitRunner.class)
public class IndexControllerTest {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    private IndexController indexController;

    @Mock
    private Request request;

    @Before
    public void setUp() throws Exception {
        indexController = new IndexController("Test App");
    }

    @Test
    public void index() throws Exception {
        URI uri = new URI("http", "localhost", "/foo");
        when(request.getRequestUri()).thenReturn(uri);

        HandlebarsView result = indexController.index(request);

        assertThat(result.getContentType(), is("text/html"));
        assertThat(result.getView(), is("/WEB-INF/hbs/index.html"));
        assertThat(result.getModel(), hasKey("meta"));

        Map<String, Object> meta = (Map<String, Object>) result.getModel().get("meta");
        assertThat(meta, hasEntry("url", (Object) uri));
        assertThat(meta, hasEntry("title", (Object) "Test App"));
        assertThat(meta, hasKey("description"));
        assertThat(meta, hasKey("image"));
    }

    @Test
    public void notFound_willThrowException() {
        thrown.expect(NotFoundException.class);
        thrown.expectMessage("Not found");

        indexController.notFound();
    }

}
