package threewks.framework.shared.util;

import com.threewks.thundr.injection.InjectionContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PropertyHelperTest {

    @InjectMocks
    private PropertyHelper propertyHelper;

    @Mock
    private InjectionContext injectionContext;

    @Test
    public void getProperty() {
        when(injectionContext.get(String.class, "property")).thenReturn("value");

        String result = propertyHelper.getProperty("property", "defaultValue");

        assertThat(result, is("value"));
    }

    @Test
    public void getProperty_willReturnDefautValue_whenPropertyDoesNotExist() {
        when(injectionContext.get(String.class, "property")).thenReturn(null);

        String result = propertyHelper.getProperty("property", "defaultValue");

        assertThat(result, is("defaultValue"));
    }

    @Test
    public void getPropertyList() {
        when(injectionContext.get(String.class, "property")).thenReturn("one,,two,");

        List<String> result = propertyHelper.getPropertyList("property", "default1", "default2");

        assertThat(result, contains("one", "two"));
    }

    @Test
    public void getPropertyList_willReturnDefaultValues_whenPropertyDoesNotExist() {
        when(injectionContext.get(String.class, "property")).thenReturn(null);

        List<String> result = propertyHelper.getPropertyList("property", "default1", "default2");

        assertThat(result, contains("default1", "default2"));
    }

    @Test
    public void getPropertySet() {
        when(injectionContext.get(String.class, "property")).thenReturn("one,,two,");

        Set<String> result = propertyHelper.getPropertySet("property", "default1", "default2");

        assertThat(result, contains("one", "two"));
    }

    @Test
    public void getPropertySet_willReturnDefaultValues_whenPropertyDoesNotExist() {
        when(injectionContext.get(String.class, "property")).thenReturn(null);

        Set<String> result = propertyHelper.getPropertySet("property", "default1", "default2");

        assertThat(result, contains("default1", "default2"));
    }

    @Test
    public void toStringSet_willIgnoreSpaces_andTrimEmptyElements() {
        String source = " one ,    two,three,    four   ,, one, two,";

        Set<String> result = PropertyHelper.toStringSet(source);

        assertThat(result, contains("one", "two", "three", "four"));
    }

    @Test
    public void toStringList_willIgnoreSpaces_andTrimEmptyElements() {
        String source = " one ,    two,three, ,   four   ,,";

        List<String> result = PropertyHelper.toStringList(source);

        assertThat(result, contains("one", "two", "three", "four"));
    }

    @Test
    public void toStringList_willReturnEmptyList_whenNull() {
        assertThat(PropertyHelper.toStringList(null), hasSize(0));
    }

    @Test
    public void toStringList_willReturnEmptyList_whenBlankString() {
        assertThat(PropertyHelper.toStringList("      "), hasSize(0));
    }

    @Test
    public void toStringList_willReturnEmptyList_whenJustCommas() {
        assertThat(PropertyHelper.toStringList(",,,  ,"), hasSize(0));
    }

}
