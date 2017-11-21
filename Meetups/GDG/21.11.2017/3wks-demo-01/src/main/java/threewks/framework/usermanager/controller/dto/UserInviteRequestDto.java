package threewks.framework.usermanager.controller.dto;

import com.threewks.thundr.user.Roles;
import threewks.framework.shared.dto.BaseDto;

import java.util.ArrayList;
import java.util.List;

public class UserInviteRequestDto extends BaseDto {
    private String email;
    private List<String> roles = new ArrayList<>();

    public String getEmail() {
        return email;
    }

    public Roles getRoles() {
        return new Roles(roles.toArray(new String[roles.size()]));
    }
}
