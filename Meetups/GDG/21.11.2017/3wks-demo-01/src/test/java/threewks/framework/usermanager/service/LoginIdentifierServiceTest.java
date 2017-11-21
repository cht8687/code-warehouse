package threewks.framework.usermanager.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import threewks.BaseTest;
import threewks.framework.usermanager.model.AppUser;
import threewks.framework.usermanager.model.LoginIdentifier;
import threewks.framework.usermanager.repository.LoginIdentifierRepository;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LoginIdentifierServiceTest extends BaseTest {

    private static final String LOGIN_IDENTIFIER = "foo@example.org";

    private LoginIdentifierService service;

    @Mock
    private LoginIdentifierRepository loginIdentifierRepository;

    private LoginIdentifier loginIdentifier;

    @Before
    public void setUp() throws Exception {
        loginIdentifier = new LoginIdentifier(new AppUser("foo"));

        service = new LoginIdentifierService(loginIdentifierRepository);
    }

    @Test
    public void get_willReturnNull_whenLoginIdentifierIsNull() throws Exception {
        assertThat(service.get(null), is(nullValue()));
    }

    @Test
    public void get_willLowerCaseLoginIdentifier_whenLoginIdentifierIsMixedCase() throws Exception {
        String loginIdentifier = "Foo.Bar@example.org";

        service.get(loginIdentifier);

        verify(loginIdentifierRepository).get(eq(loginIdentifier.toLowerCase()));
    }

    @Test
    public void checkAvailability_willNotThrowException_whenLoginIdentifierIsAvailable() throws Exception {
        service.checkAvailability(LOGIN_IDENTIFIER);
    }

    @Test
    public void checkAvailability_willThrowException_whenLoginIdentifierIsNull() throws Exception {
        thrown.expect(IllegalArgumentException.class);

        service.checkAvailability(null);
    }

    @Test
    public void checkAvailability_willThrowException_whenLoginIdentifierIsBlank() throws Exception {
        thrown.expect(IllegalArgumentException.class);

        service.checkAvailability(" ");
    }

    @Test
    public void checkAvailability_willThrowException_whenLoginIdentifierExists() throws Exception {
        thrown.expect(LoginIdentifierUnavailableException.class);

        when(loginIdentifierRepository.get(LOGIN_IDENTIFIER)).thenReturn(loginIdentifier);

        service.checkAvailability(LOGIN_IDENTIFIER);
    }

    @Test
    public void checkAvailability_willThrowException_whenLoginIdentifierExistsIgnoringCase() throws Exception {
        thrown.expect(LoginIdentifierUnavailableException.class);

        when(loginIdentifierRepository.get(LOGIN_IDENTIFIER)).thenReturn(loginIdentifier);

        service.checkAvailability(LOGIN_IDENTIFIER.toUpperCase());
    }
}
