package threewks.framework.ref;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ReferenceDataDtoTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void init_willNotAddMapProperty_whenNoValueSupplied() {
        ReferenceDataDto dto = new ReferenceDataDto("id", "description", 1, "keyWithoutVal");

        assertThat(dto.getProps().entrySet(), hasSize(0));
    }

    @Test
    public void getProp_willCast() {
        ReferenceDataDto dto = new ReferenceDataDto("id", "description", 1, "newProp", 99);
        int result = dto.getProp("newProp");

        assertThat(result, is(99));
    }


    @SuppressWarnings("unused")
    @Test
    public void getProp_willError_whenInvalidCast() {
        thrown.expect(ClassCastException.class);

        ReferenceDataDto dto = new ReferenceDataDto("id", "description", 1, "newProp", 99);

        String result = dto.getProp("newProp");
    }

}
