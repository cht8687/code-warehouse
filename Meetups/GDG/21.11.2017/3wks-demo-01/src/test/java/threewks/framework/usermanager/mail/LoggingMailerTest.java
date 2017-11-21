package threewks.framework.usermanager.mail;

import com.atomicleopard.expressive.Expressive;
import com.threewks.thundr.request.RequestContainer;
import com.threewks.thundr.view.ViewResolverRegistry;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import threewks.MockLogging;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

@RunWith(MockitoJUnitRunner.class)
public class LoggingMailerTest {

    @Rule
    public MockLogging mockLogging = new MockLogging();

    @Mock
    private ViewResolverRegistry viewResolverRegistry;
    @Mock
    private RequestContainer requestContainer;

    private LoggingMailer loggingMailer;

    @Before
    public void before() {
        loggingMailer = spy(new LoggingMailer(viewResolverRegistry, requestContainer));
    }

    @Test
    public void sendInternal_willLogAllInfo() {
        doReturn("The rain in Spain falls mainly on the plain.").when(loggingMailer).getBodyAsString(any(Object.class));
        mockLogging.reset();

        loggingMailer.sendInternal(
            email("from@email.com", "From Person"),
            email("replyTo@email.com", "ReplyTo Person"),
            emails("to1@email.com", "To Person 1", "to2@email.com", null),
            emails("cc1@email.com", "Cc Person 1", "cc2@email.com", null),
            emails("bcc1@email.com", "Bcc Person 1", "bcc2@email.com", null),
            "Email subject",
            new Object(),
            Arrays.asList(mock(com.threewks.thundr.mail.Attachment.class)));

        String output = mockLogging.getLoggedString();
        assertThat(output, containsString("from                : \"From Person\" <from@email.com>"));
        assertThat(output, containsString("replyTo             : \"ReplyTo Person\" <replyTo@email.com>"));
        assertThat(output, containsString("to                  : \"To Person 1\" <to1@email.com>, to2@email.com"));
        assertThat(output, containsString("cc                  : \"Cc Person 1\" <cc1@email.com>, cc2@email.com"));
        assertThat(output, containsString("bcc                 : \"Bcc Person 1\" <bcc1@email.com>, bcc2@email.com"));
        assertThat(output, containsString("subject             : Email subject"));
        assertThat(output, containsString("attachments         : 1"));
        assertThat(output, containsString("The rain in Spain falls mainly on the plain."));
    }


    @Test
    public void sendInternal_willLogMinimalInfo_whenMinimalProvided() {
        doReturn(null).when(loggingMailer).getBodyAsString(any(Object.class));
        mockLogging.reset();

        loggingMailer.sendInternal(
            email("from@email.com", null),
            null,
            null,
            null,
            null,
            null,
            new Object(),
            null);

        String output = mockLogging.getLoggedString();
        assertThat(output, containsString("from                : from@email.com"));

        assertThat(output, not(containsString("subject")));
        assertThat(output, not(containsString("replyTo")));
        assertThat(output, not(containsString("to       ")));
        assertThat(output, not(containsString("cc")));
        assertThat(output, not(containsString("bcc")));
        assertThat(output, not(containsString("attachments")));
        assertThat(output, not(containsString("null")));
    }

    @SuppressWarnings("ConfusingArgumentToVarargsMethod")
    private Map<String, String> emails(String... emails) {
        Map<String, String> map = Expressive.map(emails);
        // Make map consistent ordering for ease of assertions
        return new TreeMap<>(map);
    }

    private Map.Entry<String, String> email(String email, String alias) {
        return Expressive.<String, String>map(email, alias).entrySet().iterator().next();
    }

}
