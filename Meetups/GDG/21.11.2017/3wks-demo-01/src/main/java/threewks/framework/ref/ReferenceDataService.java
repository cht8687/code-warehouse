package threewks.framework.ref;

import threewks.util.Assert;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ReferenceDataService {

    private final Map<String, List<ReferenceDataDto>> referenceData = new LinkedHashMap<>();
    private final Map<Class<? extends ReferenceData>, Function<? extends ReferenceData, ReferenceDataDto>> customTransformers = new LinkedHashMap<>();

    /**
     * <p>
     * Add a custom transformer for a specific subclass of {@link ReferenceData}. Custom transformers can add properties to {@link ReferenceDataDto#props}.
     * Custom transformers will be matched in sequence, finding the first one that is assignable to the enum class. This means that you should define the
     * more specific ones first.
     * </p>
     * <p>
     * <p>
     * <strong>This must be called before {@link #registerClasses(Class[])}</strong>, otherwise it will throw an {@link IllegalStateException}.
     * </p>
     *
     * @return this object
     */
    public <R extends ReferenceData> ReferenceDataService withCustomTransformer(Class<R> subClass, Function<R, ReferenceDataDto> transformer) {
        if (!referenceData.isEmpty()) {
            throw new IllegalStateException("Custom transformers must be added before classes are registered");
        }
        customTransformers.put(subClass, transformer);
        return this;
    }

    /**
     * Register the enum classes that implement {@link ReferenceData}, to be included in reference data.
     *
     * @return this object
     */
    @SafeVarargs
    public final ReferenceDataService registerClasses(Class<? extends ReferenceData>... referenceDataClasses) {
        for (Class<? extends ReferenceData> referenceDataClass : referenceDataClasses) {
            Assert.isTrue(Enum.class.isAssignableFrom(referenceDataClass), "ReferenceData implementation must be an enum. Offending class: %s", referenceDataClass.getName());

            List<ReferenceDataDto> entries = transform(referenceDataClass);
            referenceData.put(referenceDataClass.getSimpleName(), entries);
        }
        return this;
    }

    @SuppressWarnings("unchecked")
    private <T extends ReferenceData> Function<T, ReferenceDataDto> getTransformer(Class<T> referenceDataClass) {
        Optional transformer = customTransformers
            .entrySet()
            .stream()
            .filter(entry -> entry.getKey().isAssignableFrom(referenceDataClass))
            .map(Map.Entry::getValue)
            .findFirst();
        return (Function<T, ReferenceDataDto>) transformer.orElse(ReferenceData.TO_DTO_TRANSFORMER);
    }

    @SuppressWarnings("unchecked")
    private <T extends ReferenceData> List<ReferenceDataDto> transform(Class<T> referenceDataClass) {
        return getEnumConstants(referenceDataClass)
            .stream()
            .map(getTransformer(referenceDataClass))
            .collect(Collectors.toList());
    }

    private <T extends ReferenceData> List<T> getEnumConstants(Class<T> referenceDataClass) {
        return Arrays.asList(referenceDataClass.getEnumConstants());
    }

    public Map<String, List<ReferenceDataDto>> getReferenceData() {
        return referenceData;
    }
}
