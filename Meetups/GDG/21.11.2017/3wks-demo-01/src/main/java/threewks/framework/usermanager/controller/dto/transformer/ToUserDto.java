package threewks.framework.usermanager.controller.dto.transformer;

import com.google.common.base.Function;
import threewks.framework.usermanager.controller.dto.UserDto;
import threewks.framework.usermanager.model.AppUser;

import javax.annotation.Nullable;

public class ToUserDto implements Function<AppUser, UserDto> {
    public static final ToUserDto INSTANCE = new ToUserDto();

    @Nullable
    @Override
    public UserDto apply(@Nullable AppUser input) {
        if (input == null) {
            return null;
        }

        UserDto output = new UserDto();
        output.setUsername(input.getUsername());
        output.setEmail(input.getEmail());
        output.setCreated(input.getCreated());
        output.setLastLogin(input.getLastLogin());
        output.setName(input.getName());
        output.setStatus(input.getStatus());
        output.setActive(input.isActive());
        output.setRoles(input.getRoles().getRoles());
        return output;
    }
}
