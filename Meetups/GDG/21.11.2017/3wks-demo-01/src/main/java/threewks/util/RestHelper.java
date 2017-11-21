package threewks.util;

import com.google.common.collect.ImmutableMap;
import com.threewks.thundr.http.ContentType;
import com.threewks.thundr.http.StatusCode;
import com.threewks.thundr.view.json.JsonView;

import java.util.Map;

public class RestHelper {
    static final Map<Object, Object> EMPTY_MAP = ImmutableMap.of();

    private RestHelper() {
    }

    public static JsonView response(Object object) {
        if (object != null) {
            return new JsonView(object);
        } else {
            return new JsonView(EMPTY_MAP)
                    .withContentType(ContentType.ApplicationJson)
                    .withStatusCode(StatusCode.NotFound);
        }
    }

    public static JsonView noContent() {
        return new JsonView(EMPTY_MAP)
                .withStatusCode(StatusCode.NoContent);
    }

}
