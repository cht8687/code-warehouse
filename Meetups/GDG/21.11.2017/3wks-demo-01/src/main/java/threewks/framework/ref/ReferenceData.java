package threewks.framework.ref;

import java.util.function.Function;

/**
 * Interface to allow an enum to be marked as reference data with simple name and description for UI.
 */
public interface ReferenceData {

    /**
     * Will already be implemented by enum. Just returns the enum name.
     */
    String name();

    /**
     * Will already be implemented by enum. Used to help sorting in the client without having to reference the complete set. Do not use for business logic!
     */
    int ordinal();

    /**
     * Description to use for business/ui.
     */
    String getDescription();

    /**
     * Transform the enum to a map which can be used in a JSON representation of the data.
     */
    Function<ReferenceData, ReferenceDataDto> TO_DTO_TRANSFORMER = referenceData -> referenceData == null ? null : new ReferenceDataDto(referenceData.name(), referenceData.getDescription(), referenceData.ordinal());
}
