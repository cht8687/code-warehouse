package threewks;

import com.threewks.thundr.user.Roles;
import threewks.framework.usermanager.context.SecurityContextHolder;
import threewks.framework.usermanager.model.AppUser;

/**
 * Runs tests with either a default user in the context, or the specified one using the {@link #RunWithUser(AppUser)} constructor.
 * <p>
 * Use {@link #getUser()} if you need a reference to the user.
 */
public class RunWithUser extends SecurityContextReset {

    public static final String USERNAME = "test-user";

    private AppUser user = new AppUser("test-user");

    public RunWithUser() {
    }

    /**
     * Run tests with this user in the security context.
     *
     * @param user
     */
    public RunWithUser(AppUser user) {
        this.user = user;
    }

    /**
     * Run as a user with the given roles.
     *
     * @param roles
     */
    public RunWithUser(String... roles) {
        this.user = new AppUser(USERNAME)
            .withRoles(new Roles(roles));
    }

    public AppUser getUser() {
        return user;
    }

    @Override
    protected void before() throws Throwable {
        super.before();
        SecurityContextHolder.get().setUser(user);
    }
}
