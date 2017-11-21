package threewks;

import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.junit.rules.ExternalResource;

/**
 * Forces Joda Time to use a fixed value for calls to {@link DateTime#now()}.
 * <p>
 * This allows you to test timestamps without having to know the system time value when the property was set, by always asserting against {@link DateTime#now()}.
 */
public class JodaFixedNow extends ExternalResource {

    @Override
    protected void before() throws Throwable {
        super.before();
        DateTimeUtils.setCurrentMillisFixed(DateTime.now().getMillis());
    }

    @Override
    protected void after() {
        super.after();
        DateTimeUtils.setCurrentMillisSystem();
    }
}
