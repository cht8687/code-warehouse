package threewks.framework.usermanager.controller.dto;

import threewks.framework.shared.dto.BaseDto;

import java.util.HashSet;
import java.util.Set;

public class UpdateUserRequestDto extends BaseDto {
    private String name;
    private String email;
    private Set<String> roles = new HashSet<>();

    public String getEmail() {
        return email;
    }

    public UpdateUserRequestDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getName() {
        return name;
    }

    public UpdateUserRequestDto setName(String name) {
        this.name = name;
        return this;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public UpdateUserRequestDto setRoles(Set<String> roles) {
        this.roles = new HashSet<>(roles);
        return this;
    }

}
