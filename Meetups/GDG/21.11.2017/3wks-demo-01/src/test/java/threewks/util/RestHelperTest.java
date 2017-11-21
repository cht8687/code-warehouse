package threewks.util;

import com.threewks.thundr.http.StatusCode;
import com.threewks.thundr.view.json.JsonView;
import org.junit.Test;

import java.util.Map;

import static com.atomicleopard.expressive.Expressive.map;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class RestHelperTest {

    @Test
    public void response_WillReturnJsonViewWithObject_WhenObjectNotNull() {
        Map<Object, Object> object = map("key", "value");

        JsonView response = RestHelper.response(object);

        assertThat(response.getStatusCode(), is(StatusCode.OK));
        assertThat(response.getOutput(), is((Object) object));
    }

    @Test
    public void response_WillReturnNotFoundWithEmptyMap_WhenObjectIsNull() {
        JsonView response = RestHelper.response(null);

        assertThat(response.getStatusCode(), is(StatusCode.NotFound));
        assertThat(response.getOutput(), is((Object) RestHelper.EMPTY_MAP));
    }

    @Test
    public void noContent_WillReturnNoContentStatus_WithEmptyMap() {
        JsonView response = RestHelper.noContent();

        assertThat(response.getStatusCode(), is(StatusCode.NoContent));
        assertThat(response.getOutput(), is((Object) RestHelper.EMPTY_MAP));
    }

}