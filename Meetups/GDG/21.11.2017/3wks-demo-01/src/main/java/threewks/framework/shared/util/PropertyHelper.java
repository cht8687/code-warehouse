package threewks.framework.shared.util;

import com.threewks.thundr.injection.InjectionContext;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class PropertyHelper {

    private final InjectionContext injectionContext;

    public PropertyHelper(InjectionContext injectionContext) {
        this.injectionContext = injectionContext;
    }

    public String getProperty(String key, String defaultValue) {
        return ObjectUtils.firstNonNull(getNullableProperty(key), defaultValue);
    }

    public List<String> getPropertyList(String key, String... defaultValues) {
        String rawValue = getNullableProperty(key);
        return rawValue == null ? toCollection(defaultValues, new ArrayList<>()) : toStringList(rawValue);
    }

    public Set<String> getPropertySet(String key, String... defaultValues) {
        String rawValue = getNullableProperty(key);
        return rawValue == null ? toCollection(defaultValues, new LinkedHashSet<>()) : toStringSet(rawValue);
    }

    private String getNullableProperty(String key) {
        return injectionContext.get(String.class, key);
    }

    /**
     * Convert comma-separated values into a {@link List < String >}, removing spaces between commas. Will
     * always return a value that is not {@code null}.
     *
     * @param source String that is comma-separated, empty, or {@null}.
     * @return elements in a {@link List < String >} or else an empty {@link List < String >}.
     */
    public static List<String> toStringList(String source) {
        return toCollection(source, new ArrayList<>());
    }

    /**
     * Convert comma-separated values into a {@link Set < String >}, removing spaces between commas. Will
     * always return a value that is not {@code null}.
     *
     * @param source String that is comma-separated, empty, or {@null}.
     * @return elements in a {@link Set < String >} or else an empty {@link Set < String >}.
     */
    public static Set<String> toStringSet(String source) {
        return toCollection(source, new LinkedHashSet<>());
    }

    private static <T extends Collection<String>> T toCollection(String source, T collectionInstance) {
        String trimmed = StringUtils.trimToNull(source);
        if (trimmed == null) {
            return collectionInstance;
        }
        String[] values = trimmed.split("\\s*,\\s*");

        return toCollection(values, collectionInstance);
    }

    private static <T extends Collection<String>> T toCollection(String[] values, T collectionInstance) {
        for (String value : values) {
            if (StringUtils.isNotEmpty(value)) {
                collectionInstance.add(value);
            }
        }

        return collectionInstance;
    }

}
