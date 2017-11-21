package threewks.framework.filter;

import com.threewks.thundr.http.StatusCode;
import com.threewks.thundr.http.exception.HttpStatusException;
import com.threewks.thundr.http.exception.NotFoundException;
import com.threewks.thundr.request.Request;
import com.threewks.thundr.request.Response;
import com.threewks.thundr.view.json.JsonView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

@SuppressWarnings("ThrowableNotThrown")
@RunWith(MockitoJUnitRunner.class)
public class ExceptionMappingFilterTest {

    @Mock
    private Request request;
    @Mock
    private Response response;

    private ExceptionMappingFilter interceptor;

    @Before
    public void before() {
        interceptor = new ExceptionMappingFilter();
    }

    @Test
    public void exception_WillMapJsonViewUsingFirstMatch_WhenClassIsInMappings() {
        interceptor = interceptor
            .withMapping(IllegalArgumentException.class, StatusCode.UnprocessableEntity)
            .withMapping(Exception.class, StatusCode.InternalServerError);

        final IllegalArgumentException exception = new IllegalArgumentException("blaaaa");

        final JsonView result = interceptor.exception(exception, request, response);

        assertThat(result.getStatusCode(), is(StatusCode.UnprocessableEntity));
        final ExceptionMappingFilter.Error error = (ExceptionMappingFilter.Error) result.getOutput();
        assertThat(error.getType(), is("IllegalArgumentException"));
        assertThat(error.getMessage(), is(exception.getMessage()));
    }

    @Test
    public void exception_WillMatch_WhenExceptionIsSubclassOfMapping() {
        interceptor = interceptor
            .withMapping(RuntimeException.class, StatusCode.UnprocessableEntity);

        final IllegalArgumentException exception = new IllegalArgumentException("blaaaa");

        final JsonView result = interceptor.exception(exception, request, response);

        assertThat(result.getStatusCode(), is(StatusCode.UnprocessableEntity));
        final ExceptionMappingFilter.Error error = (ExceptionMappingFilter.Error) result.getOutput();
        assertThat(error.getType(), is("IllegalArgumentException"));
        assertThat(error.getMessage(), is(exception.getMessage()));
    }


    @Test
    public void exception_WillHandleHttpStatusException_WhenAssignableFromException() {
        HttpStatusException exception = new NotFoundException("exception message");

        final JsonView result = interceptor.exception(exception, request, response);

        assertThat(result.getStatusCode(), is(StatusCode.NotFound));
        final ExceptionMappingFilter.Error error = (ExceptionMappingFilter.Error) result.getOutput();
        assertThat(error.getType(), is("NotFoundException"));
        assertThat(error.getMessage(), is(exception.getMessage()));
    }

    @Test
    public void exception_WillReturnNull_WhenClassNotMapped() {
        interceptor = interceptor
            .withMapping(IllegalArgumentException.class, StatusCode.UnprocessableEntity);

        final Object result = interceptor.exception(new IllegalStateException("just testing"), request, response);

        assertThat(result, nullValue());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void exception_WillReturnNull_WhenExceptionIsInExclusions() {
        interceptor = interceptor
            .withMapping(IllegalArgumentException.class, StatusCode.UnprocessableEntity)
            .withExclusions(Exception.class);

        final Object result = interceptor.exception(new IllegalArgumentException("just testing"), request, response);

        assertThat(result, nullValue());
    }

}
