package threewks.framework.usermanager.model;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Subclass;
import com.threewks.thundr.user.Organisation;
import com.threewks.thundr.user.Roles;
import com.threewks.thundr.user.gae.AccountGae;
import com.threewks.thundr.user.gae.UserGae;

import java.util.Map;

/**
 * Fields in this class can be indexed, but {@link com.threewks.thundr.search.SearchIndex} is only supported on fields
 * that are defined directly on {@link UserGae}.
 */
@Cache
@Subclass
public class AppUser extends UserGae {

    public static class Fields extends UserGae.Fields {
        public static final String Status = "status";
    }

    private String name;

    @Index
    private UserStatus status;

    private AppUser() {
        // For frameworks: Objectify, GSON
    }

    public AppUser(String username) {
        this(username, null);
    }

    public AppUser(String username, Organisation organisation) {
        super(username, organisation);
        status = UserStatus.INACTIVE;
    }

    public String getName() {
        return name;
    }

    public AppUser setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isActive() {
        return status == UserStatus.ACTIVE;
    }

    public boolean isNotActive() {
        return !isActive();
    }

    public UserStatus getStatus() {
        return status;
    }

    public AppUser setStatus(UserStatus status) {
        this.status = status;
        return this;
    }

    @Override
    public AppUser withOrganisation(Organisation organisation) {
        super.withOrganisation(organisation);
        return this;
    }

    @Override
    public AppUser withUsername(String username) {
        super.withUsername(username);
        return this;
    }

    @Override
    public AppUser withEmail(String email) {
        super.withEmail(email);
        return this;
    }

    @Override
    public AppUser withProperty(String key, String value) {
        super.withProperty(key, value);
        return this;
    }

    @Override
    public AppUser withRoles(Roles roles) {
        super.withRoles(roles);
        return this;
    }

    @Override
    public AppUser withRoles(AccountGae accountGae, Roles roles) {
        super.withRoles(accountGae, roles);
        return this;
    }

    /**
     *
     * @deprecated - Use direct properties on the class instead.
     */
    @Deprecated
    @Override
    public Map<String, String> getProperties() {
        return super.getProperties();
    }

    /**
     *
     * @deprecated - Use direct properties on the class instead.
     */
    @Deprecated
    @Override
    public String getProperty(String property) {
        return super.getProperty(property);
    }

    /**
     *
     * @deprecated - Use direct properties on the class instead.
     */
    @Deprecated
    @Override
    public void setProperty(String key, String value) {
        super.setProperty(key, value);
    }
}
