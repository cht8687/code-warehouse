package threewks.util;

import com.threewks.thundr.exception.BaseException;

/**
 * Throw when a requested object (usually entity) does not exist.
 */
public class DoesNotExistException extends BaseException {

    public DoesNotExistException(String format, Object... formatArgs) {
        super(format, formatArgs);
    }
}
