package threewks.framework.usermanager.context;


import com.threewks.thundr.user.User;

public interface SecurityContext {

    <U extends User> U getUser();

    <U extends User> void setUser(U user);

    /**
     * Convenience method that should return {@code true} if there is a non-null value for {@link #getUser()}.
     *
     * @return {@code true} if there is a user object present.
     */
    boolean isAuthenticated();

    /**
     * Inverse of {@link #isAuthenticated()}.
     */
    boolean isNotAuthenticated();

}
