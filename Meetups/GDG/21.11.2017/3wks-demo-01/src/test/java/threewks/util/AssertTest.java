package threewks.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class AssertTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void isTrue() {
        Assert.isTrue(true, "message");
    }

    @Test
    public void isTrue_willError_whenFalseExpression() {
        thrown.expect(IllegalArgumentException.class);
        Assert.isTrue(false, "message");
    }

    @Test
    public void isFalse() {
        Assert.isFalse(false, "message");
    }

    @Test
    public void isFalse_willError_whenTrueExpression() {
        thrown.expect(IllegalArgumentException.class);
        Assert.isFalse(true, "message");
    }

    @Test
    public void isNull_willNotError_whenNull() {
        Assert.isNull(null, "Bla");
    }

    @Test
    public void isNull_WilError_whenNonNull() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("exterminate!");

        Assert.isNull(new Object(), "exterminate!");
    }

    @Test
    public void notNull_willNotError_whenNotNull() {
        final String result = Assert.notNull("foo", "message");

        assertThat(result, is("foo"));
    }

    @Test
    public void notNull_willError_whenNull() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("message");

        Assert.notNull(null, "message");
    }

    @Test
    public void notBlank_willError_whenBlank() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("message");

        Assert.notBlank("   ", "message");
    }

    @Test
    public void notBlank_willNotError_whenNotBlank() {
        final String result = Assert.notBlank("foo", "message");

        assertThat(result, is("foo"));
    }

    @Test
    public void notEmpty_willError_whenEmptyString() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("message");

        Assert.notEmpty("", "message");
    }

    @Test
    public void notEmpty_willNotError_whenNotEmptyString() {
        final String result = Assert.notEmpty("foo", "message");

        assertThat(result, is("foo"));
    }

    @Test
    public void notEmpty_willError_whenEmptyCollection() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("message");

        Assert.notEmpty(new ArrayList<>(), "message");
    }

    @Test
    public void notEmpty_willError_whenNullCollection() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("message");

        Assert.notEmpty((Collection<Object>) null, "message");
    }

    @Test
    public void notEmpty_willNotError_whenCollectionNotEmpty() {
        Assert.notEmpty(Arrays.asList(1, 2), "message");
    }

    @Test
    public void isEmpty_willNotError_whenEmptyCollection() {
        Assert.isEmpty(new ArrayList<>(), "message");
    }

    @Test
    public void isEmpty_willNotError_whenNullCollection() {
        Assert.isEmpty(null, "message");
    }

    @Test
    public void isEmpty_willError_whenCollectionIsEmpty() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("message");

        Assert.isEmpty(Arrays.asList(1, 2), "message");
    }

    @Test
    public void entityExists_willNotError_whenObjectNotNull() {
        Assert.entityExists("blah", String.class, 123);
    }

    @Test
    public void entityExists_willError_whenObjectNull() {
        thrown.expect(DoesNotExistException.class);
        thrown.expectMessage("No String entity exists with ID: 123");

        Assert.entityExists(null, String.class, 123);
    }

    @Test
    public void exists_willNotError_whenObjectNotNull() {
        Assert.exists("blah", "message");
    }

    @Test
    public void exists_willError_whenObjectNull() {
        thrown.expect(DoesNotExistException.class);
        thrown.expectMessage("message");

        Assert.exists(null, "message");
    }

    @Test
    public void isEmail() throws Exception {
        List<String> emails = Arrays.asList(
            "foo@example.org",
            "foo+1@example.org",
            "foo.bar@example.org",
            "foo_bar@example.org",
            "foo123@example.org"
        );

        for (String email : emails) {
            Assert.isEmail(email, "Invalid email address");
        }
    }

    @Test
    public void isEmail_willError_whenStringIsNull() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invalid email address");

        Assert.isEmail(null, "Invalid email address");
    }

    @Test
    public void isEmail_willError_whenStringIsBlank() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invalid email address");

        Assert.isEmail(" ", "Invalid email address");
    }


    @Test
    public void isEmail_willError_whenNotEmail() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invalid email address");

        Assert.isEmail("foo123", "Invalid email address");
    }

    @Test
    public void isEmail_willError_whenEmailMissingTld() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invalid email address");

        Assert.isEmail("foo@local", "Invalid email address");
    }

    @Test
    public void isEmail_willError_whenEmailIsFriendly() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invalid email address");

        Assert.isEmail("Foo Bar <foo@example.org>", "Invalid email address");
    }
}
