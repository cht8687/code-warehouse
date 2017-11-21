package threewks.framework.ref;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@SuppressWarnings("ConstantConditions")
@RunWith(MockitoJUnitRunner.class)
public class ReferenceDataWithCodeTest {

    @Test
    public void transformer_willTransformToDto() {
        ReferenceDataDto result = ReferenceDataWithCode.TO_DTO_TRANSFORMER.apply(TestReferenceDataWithCode.VAL1);

        assertThat(result.getId(), is("VAL1"));
        assertThat(result.getDescription(), is("description"));
        assertThat(result.getOrdinal(), is(0));
        assertThat(result.getProps().get("code"), is((Object) "code"));
    }

    private enum TestReferenceDataWithCode implements ReferenceDataWithCode {
        VAL1("description", "code");

        private final String description;
        private final String code;

        TestReferenceDataWithCode(String description, String code) {
            this.description = description;
            this.code = code;
        }

        @Override
        public String getDescription() {
            return description;
        }

        @Override
        public String getCode() {
            return code;
        }
    }
}
