package threewks;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import java.math.BigDecimal;

/**
 * Some convenience matchers for {@link BigDecimal}.
 */
public class BigDecimalMatchers {

    public static Matcher<BigDecimal> lessThan(final BigDecimal bigDecimal) {
        return new BaseMatcher<BigDecimal>() {
            public boolean matches(Object o) {
                BigDecimal actual = (BigDecimal) o;
                return actual != null && actual.compareTo(bigDecimal) < 0;
            }

            public void describeTo(Description description) {
                description.appendText("value less than " + bigDecimal);
            }
        };
    }

    public static Matcher<BigDecimal> bigDecimalValue(String value) {
        return bigDecimalValue(new BigDecimal(value));
    }

    public static Matcher<BigDecimal> bigDecimalValue(BigDecimal expected) {
        return Matchers.closeTo(expected, BigDecimal.ZERO);
    }

}
