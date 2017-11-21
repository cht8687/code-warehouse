package threewks.framework.usermanager.repository;

import com.threewks.thundr.gae.objectify.repository.StringRepository;
import threewks.framework.usermanager.model.MagicLink;

public class MagicLinkRepository extends StringRepository<MagicLink> {

    public MagicLinkRepository() {
        super(MagicLink.class, null);
    }
}
