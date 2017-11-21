package threewks;

import org.junit.rules.ExternalResource;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

import static java.util.logging.Logger.getLogger;

public class MockLogging extends ExternalResource {
    private final Logger log;
    private OutputStream logCapturingStream;
    private StreamHandler customLogHandler;

    public MockLogging() {
        this("thundr");
    }

    public MockLogging(String loggerName) {
        log = getLogger(loggerName);
    }

    @Override
    protected void before() {
        logCapturingStream = new ByteArrayOutputStream();
        customLogHandler = new StreamHandler(logCapturingStream, new SimpleFormatter());
        log.addHandler(customLogHandler);
    }

    @Override
    protected void after() {
        log.removeHandler(customLogHandler);
    }

    /**
     * Clear out any existing logs and start again.
     */
    public void reset() {
        after();
        before();
    }

    public String getLoggedString() {
        customLogHandler.flush();
        return logCapturingStream.toString();
    }

}
