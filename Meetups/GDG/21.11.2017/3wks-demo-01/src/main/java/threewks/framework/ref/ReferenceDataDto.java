package threewks.framework.ref;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Map;

import static com.atomicleopard.expressive.Expressive.map;

public class ReferenceDataDto {
    private final String id;
    private final String description;
    private final int ordinal;
    private final Map<String, Object> props;

    public ReferenceDataDto(String id, String description, int ordinal, Object... propMapParams) {
        this.id = id;
        this.description = description;
        this.ordinal = ordinal;
        this.props = map(propMapParams);
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public Map<String, Object> getProps() {
        return props;
    }

    @SuppressWarnings("unchecked")
    public <T> T getProp(String key) {
        return (T) props.get(key);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

}
