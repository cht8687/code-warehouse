package threewks.service.bootstrap;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.VoidWork;
import com.threewks.thundr.configuration.Environment;
import com.threewks.thundr.logger.Logger;
import com.threewks.thundr.user.Roles;
import org.apache.commons.lang3.StringUtils;
import threewks.framework.usermanager.model.AppUser;
import threewks.framework.usermanager.model.UserStatus;
import threewks.framework.usermanager.service.AppUserService;
import threewks.util.Assert;

import static java.util.UUID.randomUUID;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

public class BootstrapService {
    private static final String MARKER = StringUtils.repeat('#', 20);

    private final AppUserService userService;
    private final String superAdminEmail;

    public BootstrapService(AppUserService userService, String superAdminEmail) {
        this.userService = userService;
        this.superAdminEmail = superAdminEmail;
    }

    public void bootstrapLocal() {
        Assert.isTrue(Environment.is(Environment.DEV), "This can only be run in a local dev environment");
        logStart();

        ObjectifyService.run(new VoidWork() {
            @Override
            public void vrun() {
                // Your bootstrap methods go here. Tips: do a check and only bootstrap if data not there; and add logging so you know what happened.
                createSuperUser();
            }
        });

        logEnd();
    }

    public void createSuperUser() {
        if (userService.get(superAdminEmail) == null) {
            AppUser user = new AppUser(randomUUID().toString());
            user.setStatus(UserStatus.ACTIVE);
            user.setEmail(superAdminEmail);
            user.setRoles(new Roles(Roles.Super, Roles.Admin));

            String password = Environment.is(Environment.DEV)
                ? "password" : randomAlphanumeric(12);

            userService.register(user, password);

            Logger.info("Created super admin user %s with password %s.", user.getEmail(), password);
        }
    }

    private void logStart() {
        Logger.info("%1$s    LOCAL BOOTSTRAP START   %1$s\n", MARKER);
    }

    private void logEnd() {
        Logger.info("%1$s    LOCAL BOOTSTRAP END     %1$s\n", MARKER);
    }

}
