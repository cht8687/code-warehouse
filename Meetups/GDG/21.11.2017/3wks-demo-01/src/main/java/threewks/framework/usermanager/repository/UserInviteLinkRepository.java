package threewks.framework.usermanager.repository;

import com.threewks.thundr.gae.objectify.repository.StringRepository;
import threewks.framework.usermanager.model.UserInviteLink;

public class UserInviteLinkRepository extends StringRepository<UserInviteLink> {
    public UserInviteLinkRepository() {
        super(UserInviteLink.class, null);
    }
}
