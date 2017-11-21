package threewks;

import org.joda.time.DateTimeZone;
import org.junit.rules.ExternalResource;

/**
 * Sets the system timezone to UTC so that we can ensure tests do not depend on the current timezone of their
 * local environment for any business logic. This helps us trap unexpected behaviour when we deploy to environments
 * with a different timezone (namely, Google environments deliberately setup as UTC to avoid daylight savings shifts).
 */
public class TimeZoneUTC extends ExternalResource {

    private DateTimeZone origDefault = DateTimeZone.getDefault();

    @Override
    protected void before() throws Throwable {
        super.before();
        DateTimeZone.setDefault(DateTimeZone.UTC);
    }

    @Override
    protected void after() {
        super.after();
        DateTimeZone.setDefault(origDefault);
    }
}
