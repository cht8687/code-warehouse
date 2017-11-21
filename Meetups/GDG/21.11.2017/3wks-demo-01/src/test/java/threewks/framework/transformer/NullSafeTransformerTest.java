package threewks.framework.transformer;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class NullSafeTransformerTest {
    private StringTransformer transformer = new StringTransformer();

    @Test
    public void apply_WillReturnInput_WhenInputIsNotNull() throws Exception {
        assertThat(transformer.apply(123), is("123"));
    }

    @Test
    public void apply_WillReturnNull_WhenInputIsNull() throws Exception {
        assertThat(transformer.apply(null), nullValue());
    }

    private final class StringTransformer extends NullSafeTransformer<Integer, String> {

        @Override
        protected String transform(Integer input) {
            //Do something horrible null-unsafe...
            return input.toString();
        }
    }
}
