package threewks.framework.filter;

import com.threewks.thundr.http.StatusCode;
import com.threewks.thundr.http.exception.HttpStatusException;
import com.threewks.thundr.logger.Logger;
import com.threewks.thundr.request.Request;
import com.threewks.thundr.request.Response;
import com.threewks.thundr.route.controller.BaseFilter;
import com.threewks.thundr.view.json.JsonView;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Class for mapping exceptions of a certain type (including subclasses) to a response code. This
 * will find the first match by searching in chronological order what was configured. See test for
 * examples.
 */
public class ExceptionMappingFilter extends BaseFilter {

    private static final String DEFAULT_LOG_MESSAGE_FORMAT = "ExceptionMappingFilter caught %s. Returning %s - %s";

    private final Map<Class<? extends Exception>, ResponseOptions> exceptionResponseCodeMappings = new LinkedHashMap<>();
    private final Set<Class<? extends Exception>> exclusions = new HashSet<>();

    public ExceptionMappingFilter withMapping(Class<? extends Exception> exceptionClass, StatusCode statusCode, LogLevel logLevel) {
        exceptionResponseCodeMappings.put(exceptionClass, new ResponseOptions(statusCode, logLevel));
        return this;
    }

    public ExceptionMappingFilter withMapping(Class<? extends Exception> exceptionClass, StatusCode statusCode) {
        return withMapping(exceptionClass, statusCode, LogLevel.ERROR_WITH_MESSAGE);
    }

    @SafeVarargs
    public final ExceptionMappingFilter withExclusions(Class<? extends Exception>... exceptionClasses) {
        Collections.addAll(exclusions, exceptionClasses);
        return this;
    }

    @Override
    public <T> T exception(Exception e, Request req, Response resp) {
        final Class<? extends Exception> exceptionClass = e.getClass();
        if (isExcluded(exceptionClass)) {
            return null;
        }

        final ResponseOptions mappedResponse = getMappedStatusCode(exceptionClass, e);
        return mappedResponse == null
            ? null
            : (T) logAndGetView(e, mappedResponse);
    }

    private JsonView logAndGetView(Exception e, ResponseOptions mappedResponse) {
        logException(e, mappedResponse);

        return new JsonView(new Error(e)).withStatusCode(mappedResponse.statusCode);
    }

    private void logException(Exception e, ResponseOptions mappedResponse) {
        String className = e.getClass().getSimpleName();
        switch (mappedResponse.logLevel) {
            case ERROR_WITH_STACKTRACE:
                Logger.error("ExceptionMappingFilter caught %s. Returning %s - %n%s", className, mappedResponse, ExceptionUtils.getStackTrace(e));
                break;
            case ERROR_WITH_MESSAGE:
                Logger.error(DEFAULT_LOG_MESSAGE_FORMAT, className, mappedResponse.statusCode, e.getMessage());
                break;
            case WARN_WITH_MESSAGE:
                Logger.warn(DEFAULT_LOG_MESSAGE_FORMAT, className, mappedResponse.statusCode, e.getMessage());
                break;
            case INFO_WITH_MESSAGE:
                Logger.info(DEFAULT_LOG_MESSAGE_FORMAT, className, mappedResponse.statusCode, e.getMessage());
                break;
        }
    }

    private ResponseOptions getMappedStatusCode(Class<? extends Exception> exceptionClass, Exception e) {
        if (HttpStatusException.class.isAssignableFrom(exceptionClass)) {
            HttpStatusException httpStatusException = (HttpStatusException) e;
            return new ResponseOptions(httpStatusException.getStatus(), LogLevel.WARN_WITH_MESSAGE);
        }

        for (Map.Entry<Class<? extends Exception>, ResponseOptions> exceptionMapping : exceptionResponseCodeMappings.entrySet()) {
            if (exceptionMapping.getKey().isAssignableFrom(exceptionClass)) {
                return exceptionMapping.getValue();
            }
        }
        return null;
    }

    private boolean isExcluded(Class<? extends Exception> exceptionClass) {
        for (Class<? extends Exception> exclusion : exclusions) {
            if (exclusion.isAssignableFrom(exceptionClass)) {
                return true;
            }
        }
        return false;
    }


    public static class Error {
        private String type;
        private String message;

        private Error() {
        }

        public Error(Exception e) {
            this.type = e.getClass().getSimpleName();
            this.message = e.getMessage();
        }

        public String getType() {
            return type;
        }

        public String getMessage() {
            return message;
        }
    }

    public enum LogLevel {
        ERROR_WITH_STACKTRACE,
        ERROR_WITH_MESSAGE,
        WARN_WITH_MESSAGE,
        INFO_WITH_MESSAGE,
        NONE
    }

    private static class ResponseOptions {
        private final StatusCode statusCode;
        private final LogLevel logLevel;

        private ResponseOptions(StatusCode statusCode, LogLevel logLevel) {
            this.statusCode = statusCode;
            this.logLevel = logLevel;
        }
    }

}
