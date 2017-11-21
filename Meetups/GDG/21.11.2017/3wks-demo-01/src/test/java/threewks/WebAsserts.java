package threewks;

import com.threewks.thundr.http.StatusCode;
import com.threewks.thundr.view.View;
import com.threewks.thundr.view.json.JsonView;

import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class WebAsserts {

    public static void assertNoContent(View view) {
        assertThat(view, instanceOf(JsonView.class));
        JsonView jsonView = (JsonView) view;
        assertThat(jsonView.getStatusCode(), is(StatusCode.NoContent));
        assertThat(jsonView.getOutput(), instanceOf(Map.class));
        Map<?, ?> output = (Map) jsonView.getOutput();
        assertThat(output.isEmpty(), is(true));
    }

    public static void assertNotFound(View view) {
        assertThat(view, instanceOf(JsonView.class));
        JsonView jsonView = (JsonView) view;
        assertThat(jsonView.getContentType(), is("application/json"));
        assertThat(jsonView.getStatusCode(), is(StatusCode.NotFound));
        assertThat(jsonView.getOutput(), instanceOf(Map.class));
        Map<?, ?> output = (Map) jsonView.getOutput();
        assertThat(output.isEmpty(), is(true));
    }

    public static void assertOk(View view, Object expectedResult) {
        assertOk((JsonView) view, expectedResult);
    }

    public static void assertOk(JsonView jsonView, Object expectedResult) {
        assertJsonView(jsonView, StatusCode.OK, expectedResult);
    }

    public static void assertJsonView(JsonView jsonView, StatusCode expectedStatusCode, Object expectedResult) {
        assertThat(jsonView.getStatusCode(), is(expectedStatusCode));
        assertThat(jsonView.getOutput(), equalTo(expectedResult));
    }


}
