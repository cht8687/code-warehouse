package threewks.model;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.OnSave;
import com.threewks.thundr.gae.objectify.Refs;
import com.threewks.thundr.search.SearchIndex;
import com.threewks.thundr.search.gae.meta.IndexType;
import org.joda.time.DateTime;
import threewks.framework.usermanager.context.SecurityContextHolder;
import threewks.framework.usermanager.model.AppUser;

/**
 * Created and updated timestamps set. On creation updated and created are the same.
 * <p>
 * We deliberately do not expose setters. If you need to set values for tests, use
 * {@link com.threewks.thundr.test.TestSupport#setField(Object, String, Object)}.
 */
public abstract class BaseEntity {

    /**
     * A marker interface to allow us to specify when to pre-fetch user Ref entities.
     * See https://github.com/objectify/objectify/wiki/BasicOperations#load-groups for how to do this.
     */
    public interface PreLoadAuditRefs {
    }

    public static class Fields {
        public static final String updated = "updated";
        public static final String updatedBy = "updatedBy";
        public static final String created = "created";
        public static final String createdBy = "createdBy";
    }

    @Index
    @SearchIndex(as = IndexType.BigDecimal)
    private DateTime created;

    // Only pre-load when specifically asked, using the marker interface
    @Load(PreLoadAuditRefs.class)
    @Index
    @SearchIndex(as = IndexType.Identifier)
    private Ref<AppUser> createdBy;

    @Index
    @SearchIndex(as = IndexType.BigDecimal)
    private DateTime updated;

    // Only pre-load when specifically asked, using the marker interface
    @Load(PreLoadAuditRefs.class)
    @Index
    @SearchIndex(as = IndexType.Identifier)
    private Ref<AppUser> updatedBy;

    @Ignore
    private transient boolean skipSettingAuditableFields = false;

    /**
     * Handy for data migrations where you don't want to overwrite last updated.
     */
    public void skipSettingAuditableFields() {
        skipSettingAuditableFields = true;
    }

    public boolean isSkipSettingAuditableFields() {
        return skipSettingAuditableFields;
    }

    public DateTime getCreated() {
        return created;
    }

    public AppUser getCreatedBy() {
        return Refs.unref(createdBy);
    }

    public Key<AppUser> getCreatedByKey() {
        return toKey(createdBy);
    }

    public DateTime getUpdated() {
        return updated;
    }

    public AppUser getUpdatedBy() {
        return Refs.unref(updatedBy);
    }

    public Key<AppUser> getUpdatedByKey() {
        return toKey(updatedBy);
    }

    @OnSave
    private void setAuditableFieldsOnSave() {
        if (!skipSettingAuditableFields) {
            updated = DateTime.now();
            updatedBy = getCurrentUserRef();

            if (created == null) {
                created = updated;
                createdBy = updatedBy;
            }
        }
    }

    private Key<AppUser> toKey(Ref<AppUser> ref) {
        return ref == null ? null : ref.getKey();
    }

    protected Ref<AppUser> getCurrentUserRef() {
        AppUser user = SecurityContextHolder.get().getUser();
        return Refs.ref(user);
    }

}
