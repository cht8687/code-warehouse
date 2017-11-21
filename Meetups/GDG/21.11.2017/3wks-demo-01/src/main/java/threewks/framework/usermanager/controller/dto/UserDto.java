package threewks.framework.usermanager.controller.dto;

import org.joda.time.DateTime;
import threewks.framework.shared.dto.BaseDto;
import threewks.framework.usermanager.model.UserStatus;

import java.util.HashSet;
import java.util.Set;

public class UserDto extends BaseDto {
    private String name;
    private String username;
    private String email;
    private DateTime created;
    private DateTime lastLogin;
    private boolean active;
    private UserStatus status;
    private Set<String> roles = new HashSet<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public DateTime getCreated() {
        return created;
    }

    public void setCreated(DateTime created) {
        this.created = created;
    }

    public DateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(DateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = new HashSet<>(roles);
    }

    public String getName() {
        return name;
    }

    public UserDto setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public UserDto setActive(boolean active) {
        this.active = active;
        return this;
    }

    public UserStatus getStatus() {
        return status;
    }

    public UserDto setStatus(UserStatus status) {
        this.status = status;
        return this;
    }
}
