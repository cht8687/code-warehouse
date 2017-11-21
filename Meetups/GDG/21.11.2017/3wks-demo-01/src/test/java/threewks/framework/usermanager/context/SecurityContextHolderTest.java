package threewks.framework.usermanager.context;


import org.junit.Test;

import static org.junit.Assert.assertThat;
import static threewks.Matchers.hasHiddenConstructor;

public class SecurityContextHolderTest {

    @Test
    public void staticUseOnly() {
        assertThat(SecurityContextHolder.class, hasHiddenConstructor());
    }

}
