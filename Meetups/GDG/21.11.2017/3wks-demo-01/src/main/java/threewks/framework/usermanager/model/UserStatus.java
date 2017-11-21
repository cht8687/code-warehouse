package threewks.framework.usermanager.model;

import threewks.framework.ref.ReferenceData;

public enum UserStatus implements ReferenceData {
    INVITED("Invited"),
    ACTIVE("Active"),
    INACTIVE("Inactive");

    private final String description;

    UserStatus(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
