package threewks.framework.cloudstorage.attachment;

public class NewAttachmentDto {
    private String filename;
    private String gcsObjectName;
    private String bucket;
    private String contentType;
    private String id;
    private String name;

    /**
     * Size of the attachment in bytes.
     */
    private long size;

    public String getFilename() {
        return filename;
    }

    public NewAttachmentDto setFilename(String filename) {
        this.filename = filename;
        return this;
    }

    public String getGcsObjectName() {
        return gcsObjectName;
    }

    public NewAttachmentDto setGcsObjectName(String gcsObjectName) {
        this.gcsObjectName = gcsObjectName;
        return this;
    }

    public String getBucket() {
        return bucket;
    }

    public NewAttachmentDto setBucket(String bucket) {
        this.bucket = bucket;
        return this;
    }

    public String getContentType() {
        return contentType;
    }

    public NewAttachmentDto setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public String getId() {
        return id;
    }

    public NewAttachmentDto setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public NewAttachmentDto setName(String name) {
        this.name = name;
        return this;
    }

    public long getSize() {
        return size;
    }

    public NewAttachmentDto setSize(long size) {
        this.size = size;
        return this;
    }
}
