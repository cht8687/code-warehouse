package threewks.framework.usermanager.service;

import threewks.framework.usermanager.model.LoginIdentifier;
import threewks.framework.usermanager.repository.LoginIdentifierRepository;
import threewks.util.Assert;

public class LoginIdentifierService {

    private final LoginIdentifierRepository loginIdentifierRepository;

    public LoginIdentifierService(LoginIdentifierRepository loginIdentifierRepository) {
        this.loginIdentifierRepository = loginIdentifierRepository;
    }

    public LoginIdentifier get(String loginIdentifier) {
        if (loginIdentifier == null) {
            return null;
        }
        return loginIdentifierRepository.get(loginIdentifier.toLowerCase());
    }

    public LoginIdentifier put(LoginIdentifier loginIdentifier) {
        return loginIdentifierRepository.put(loginIdentifier);
    }

    public void checkAvailability(String loginIdentifier) {
        Assert.notBlank(loginIdentifier, "Availability check failed. Login identifier is required");

        LoginIdentifier loginIdentifierUserMapping = loginIdentifierRepository.get(loginIdentifier.toLowerCase());
        if (loginIdentifierUserMapping != null) {
            throw new LoginIdentifierUnavailableException(loginIdentifier);
        }
    }

    public void delete(LoginIdentifier entity) {
        loginIdentifierRepository.delete(entity);
    }
}
