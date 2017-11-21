package threewks.framework.cloudstorage;

import com.threewks.thundr.exception.BaseException;

public class GoogleCloudStorageException extends BaseException {
    public GoogleCloudStorageException(Throwable cause, String format, Object... formatArgs) {
        super(cause, format, formatArgs);
    }
}
