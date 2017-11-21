package threewks.framework.usermanager.context;

import com.threewks.thundr.user.User;

public class SecurityContextImpl implements SecurityContext {
    private User user;

    @Override
    @SuppressWarnings("unchecked")
    public <U extends User> U getUser() {
        return (U) user;
    }

    @Override
    public <U extends User> void setUser(U user) {
        this.user = user;
    }

    @Override
    public boolean isAuthenticated() {
        return user != null;
    }

    @Override
    public boolean isNotAuthenticated() {
        return !isAuthenticated();
    }

}
