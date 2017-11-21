package threewks;

import org.junit.rules.ExternalResource;
import threewks.framework.usermanager.context.SecurityContextHolder;

public class SecurityContextReset extends ExternalResource {

    @Override
    protected void after() {
        super.after();
        SecurityContextHolder.clear();
    }

}
