package threewks.framework.usermanager.service;

import com.threewks.thundr.exception.BaseException;

public class MagicLinkException extends BaseException {
    public MagicLinkException(String format, Object... formatArgs) {
        super(format, formatArgs);
    }
}
