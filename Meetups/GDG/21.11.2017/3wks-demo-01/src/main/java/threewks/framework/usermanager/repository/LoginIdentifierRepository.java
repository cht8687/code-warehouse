package threewks.framework.usermanager.repository;

import com.threewks.thundr.gae.objectify.repository.StringRepository;
import threewks.framework.usermanager.model.LoginIdentifier;

public class LoginIdentifierRepository extends StringRepository<LoginIdentifier> {
    public LoginIdentifierRepository() {
        super(LoginIdentifier.class, null);
    }
}
