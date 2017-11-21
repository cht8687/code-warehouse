package threewks;

import com.threewks.thundr.gae.SetupAppengine;
import com.threewks.thundr.gae.objectify.SetupObjectify;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static com.googlecode.objectify.ObjectifyService.ofy;


public class BaseTest {

    /**
     *  Don't complain about unused mocks/stubs by default because we stub out a lot of Ref instances to return the real values
     *  in common test data setup and the stub ref may not always be used.
     */
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule().silent();

    @Rule
    public SetupAppengine setupAppengine = new SetupAppengine();

    @Rule
    public SetupObjectify setupObjectify = new TestObjectify();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TimeZoneUTC timeZoneUTC = new TimeZoneUTC();

    @Rule
    public JodaFixedNow jodaFixedNow = new JodaFixedNow();

    @Rule
    public SecurityContextReset securityContextReset = new SecurityContextReset();

    protected void save(Object... entities) {
        for (Object entity : entities) {
            ofy().save().entity(entity).now();
        }
    }
}
