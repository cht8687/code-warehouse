package threewks.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.Collection;

/**
 * <p>
 * Stolen idea and some code from Spring. Util to assert conditions and throw {@link IllegalArgumentException} in most cases if condition fails.
 * </p>
 * <p>
 * Tweaked and cut-down for our use ... add to it!
 * </p>
 */
public final class Assert {

    private Assert() {
        // Static use only
    }

    public static void isFalse(boolean expression, String messageFormat, Object... messageParams) {
        isTrue(!expression, messageFormat, messageParams);
    }

    public static void isTrue(boolean expression, String messageFormat, Object... messageParams) {
        if (!expression) {
            throwException(messageFormat, messageParams);
        }
    }

    /**
     * Extension of {@link #exists(Object, String, Object...)}, but specifically for entities, constructing a standard message.
     *
     * @param entity      Entity object to check not {@link null}
     * @param entityClass Class of the entity being checked
     * @param id          Id used to search for the entity
     */
    public static <T> void entityExists(T entity, Class<T> entityClass, Object id) {
        exists(entity, "No %s entity exists with ID: %s", entityClass.getSimpleName(), String.valueOf(id));
    }

    /**
     * Different to other assertions in that this throws a custom exception that can be customised in the framework to throw a 404 Not Found if desired
     * Will throw that exception if the supplied object is {@code null}.
     *
     * @param object        Object (usually an entity) to check.
     * @param messageFormat Custom message format
     * @param messageParams Custom message format params
     */
    public static void exists(Object object, String messageFormat, Object... messageParams) {
        if (object == null) {
            throw new DoesNotExistException(messageFormat, messageParams);
        }
    }

    public static void isNull(Object object, String messageFormat, Object... messageParams) {
        if (object != null) {
            throwException(messageFormat, messageParams);
        }
    }

    public static <T> T notNull(T object, String messageFormat, Object... messageParams) {
        if (object == null) {
            throwException(messageFormat, messageParams);
        }
        return object;
    }

    public static String notBlank(String object, String messageFormat, Object... messageParams) {
        if (StringUtils.isBlank(object)) {
            throwException(messageFormat, messageParams);
        }
        return object;
    }

    public static String notEmpty(String object, String messageFormat, Object... messageParams) {
        if (StringUtils.isEmpty(object)) {
            throwException(messageFormat, messageParams);
        }
        return object;
    }

    public static String isEmail(String string, String messageFormat, Object... messageParams) {
        if (!EMAIL_VALIDATOR.isValid(string)) {
            throwException(messageFormat, messageParams);
        }
        return string;
    }

    public static <T> Collection<T> notEmpty(Collection<T> object, String messageFormat, Object... messageParams) {
        if (object == null || object.isEmpty()) {
            throwException(messageFormat, messageParams);
        }
        return object;
    }

    public static <T> Collection<T> isEmpty(Collection<T> object, String messageFormat, Object... messageParams) {
        if (object != null && !object.isEmpty()) {
            throwException(messageFormat, messageParams);
        }
        return object;
    }

    private static final EmailValidator EMAIL_VALIDATOR = EmailValidator.getInstance();

    private static void throwException(String messageFormat, Object[] messageParams) {
        throw new IllegalArgumentException(String.format(messageFormat, messageParams));
    }

}
