package threewks.framework.usermanager.service;

import com.threewks.thundr.exception.BaseException;

public class LoginIdentifierUnavailableException extends BaseException {
    public LoginIdentifierUnavailableException(String loginIdentifier) {
        super("Login ID %s is unavailable", loginIdentifier);
    }
}
