package threewks.framework.ref;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@SuppressWarnings("unchecked")
public class ReferenceDataServiceTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    private ReferenceDataService service;

    @Before
    public void before() {
        service = new ReferenceDataService();
    }

    @Test
    public void getReferenceData_willReturnEmptyMap_ByDefault() {
        Map<String, List<ReferenceDataDto>> result = service.getReferenceData();

        assertThat(result.isEmpty(), is(true));
    }

    @Test
    public void getReferenceData_willReturnJsonFriendlyMap_whenClassesRegistered() {
        service = new ReferenceDataService().registerClasses(TestEnum1.class, TestEnum2.class);

        Map<String, List<ReferenceDataDto>> result = service.getReferenceData();

        assertThat(result.entrySet(), hasSize(2));
        List<ReferenceDataDto> testEnum1 = result.get("TestEnum1");
        assertThat(testEnum1, contains(new ReferenceDataDto("ONE", "One", 0, "ONE"), new ReferenceDataDto("TWO", "Two", 1, "TWO")));
        assertThat(result.get("TestEnum2"), contains(new ReferenceDataDto("A", "A desc", 0), new ReferenceDataDto("B", "B desc", 1, "B")));
    }

    @Test
    public void getReferenceData_willReturnJsonFriendlyMapWithCode_whenMatchingCustomTransformerRegistered() {
        service = new ReferenceDataService()
            .withCustomTransformer(ReferenceDataWithCode.class, ReferenceDataWithCode.TO_DTO_TRANSFORMER)
            .registerClasses(TestEnum1WithCode.class, TestEnum2WithCode.class);

        Map<String, List<ReferenceDataDto>> result = service.getReferenceData();

        assertThat(result.entrySet(), hasSize(2));
        assertThat(result.get("TestEnum1WithCode"), contains(new ReferenceDataDto("ONE", "One", 0, "code", "one-code"), new ReferenceDataDto("TWO", "Two", 1, "code", "two-code")));
        assertThat(result.get("TestEnum2WithCode"), contains(new ReferenceDataDto("A", "A desc", 0, "code", "a-desc-code"), new ReferenceDataDto("B", "B desc", 1, "code", "b-desc-code")));
    }

    @Test
    public void getReferenceData_willIgnoreCustomTransformer_whenNotAssignableToEnumClass() {
        service = new ReferenceDataService()
            .withCustomTransformer(ReferenceDataWithCode.class, ReferenceDataWithCode.TO_DTO_TRANSFORMER)
            .registerClasses(TestEnum1.class);

        Map<String, List<ReferenceDataDto>> result = service.getReferenceData();

        assertThat(result.entrySet(), hasSize(1));
        List<ReferenceDataDto> testEnum1 = result.get("TestEnum1");
        assertThat(testEnum1, contains(new ReferenceDataDto("ONE", "One", 0, "ONE"), new ReferenceDataDto("TWO", "Two", 1, "TWO")));
    }


    @Test
    public void registerClasses_willFail_whenNotAnEnum() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("ReferenceData implementation must be an enum. Offending class: threewks.framework.ref.ReferenceDataServiceTest$NotAnEnum");

        service = new ReferenceDataService().registerClasses(NotAnEnum.class);

        Map<String, List<ReferenceDataDto>> result = service.getReferenceData();

        assertThat(result.isEmpty(), is(true));
    }

    @Test
    public void withCustomTransformer_willFail_whenCalledAfterRegisterClasses() {
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Custom transformers must be added before classes are registered");

        service = new ReferenceDataService()
            .registerClasses(TestEnum1.class)
            .withCustomTransformer(ReferenceDataWithCode.class, ReferenceDataWithCode.TO_DTO_TRANSFORMER);
    }

    public enum TestEnum1 implements ReferenceData {
        ONE("One"), TWO("Two");

        private final String description;

        TestEnum1(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum TestEnum1WithCode implements ReferenceDataWithCode {
        ONE("One", "one-code"), TWO("Two", "two-code");

        private final String description;
        private final String code;

        TestEnum1WithCode(String description, String code) {
            this.description = description;
            this.code = code;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public String getCode() {
            return code;
        }
    }

    public enum TestEnum2 implements ReferenceData {
        A("A desc"), B("B desc");

        private final String description;

        TestEnum2(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum TestEnum2WithCode implements ReferenceDataWithCode {
        A("A desc", "a-desc-code"), B("B desc", "b-desc-code");

        private final String description;
        private final String code;

        TestEnum2WithCode(String description, String code) {
            this.description = description;
            this.code = code;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public String getCode() {
            return code;
        }
    }

    public class NotAnEnum implements ReferenceData {
        @Override
        public String name() {
            return "A name";
        }

        @Override
        public int ordinal() {
            return 0;
        }

        @Override
        public String getDescription() {
            return "A description";
        }
    }


}
