package threewks.framework.cloudstorage.attachment.model;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Load;
import com.openpojo.business.BusinessIdentity;
import com.openpojo.business.annotation.BusinessKey;
import com.threewks.thundr.gae.objectify.Refs;
import org.joda.time.DateTime;
import threewks.framework.usermanager.model.AppUser;

import static org.joda.time.DateTime.now;

public class Attachment {
    @BusinessKey
    private String id;
    private String filename;
    private String gcsObjectName;
    private String bucket;
    private String contentType;
    private String name;

    @Load
    private Ref<AppUser> uploadedBy;

    /**
     * Size of the attachment in bytes.
     */
    private long size;
    private DateTime created = now();

    public String getId() {
        return id;
    }

    public Attachment setId(String id) {
        this.id = id;
        return this;
    }

    public String getFilename() {
        return filename;
    }

    public Attachment setFilename(String filename) {
        this.filename = filename;
        return this;
    }

    public String getGcsObjectName() {
        return gcsObjectName;
    }

    public Attachment setGcsObjectName(String gcsObjectName) {
        this.gcsObjectName = gcsObjectName;
        return this;
    }

    public String getBucket() {
        return bucket;
    }

    public Attachment setBucket(String bucket) {
        this.bucket = bucket;
        return this;
    }

    public String getContentType() {
        return contentType;
    }

    public Attachment setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public String getName() {
        return name;
    }

    public Attachment setName(String name) {
        this.name = name;
        return this;
    }

    public long getSize() {
        return size;
    }

    public Attachment setSize(long size) {
        this.size = size;
        return this;
    }

    public DateTime getCreated() {
        return created;
    }

    public Attachment setUploadedBy(AppUser uploadedBy) {
        this.uploadedBy = Refs.ref(uploadedBy);
        return this;
    }

    public AppUser getUploadedBy() {
        return Refs.unref(uploadedBy);
    }

    @Override
    public boolean equals(Object o) {
        return BusinessIdentity.areEqual(this, o);
    }

    @Override
    public int hashCode() {
        return BusinessIdentity.getHashCode(this);
    }

    @Override
    public String toString() {
        return BusinessIdentity.toString(this);
    }
}
