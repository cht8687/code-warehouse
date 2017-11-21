package threewks.framework.cloudstorage.attachment;

import org.joda.time.DateTime;
import threewks.framework.usermanager.controller.dto.UserDto;

public class AttachmentDto extends NewAttachmentDto {
    private UserDto uploadedBy;
    private DateTime created;

    public UserDto getUploadedBy() {
        return uploadedBy;
    }

    public AttachmentDto setUploadedBy(UserDto uploadedBy) {
        this.uploadedBy = uploadedBy;
        return this;
    }

    public DateTime getCreated() {
        return created;
    }

    public AttachmentDto setCreated(DateTime created) {
        this.created = created;
        return this;
    }

    @Override
    public AttachmentDto setFilename(String filename) {
        super.setFilename(filename);
        return this;
    }

    @Override
    public AttachmentDto setGcsObjectName(String gcsObjectName) {
        super.setGcsObjectName(gcsObjectName);
        return this;
    }

    @Override
    public AttachmentDto setBucket(String bucket) {
        super.setBucket(bucket);
        return this;
    }

    @Override
    public AttachmentDto setContentType(String contentType) {
        super.setContentType(contentType);
        return this;
    }

    @Override
    public AttachmentDto setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public AttachmentDto setName(String name) {
        super.setName(name);
        return this;
    }

    @Override
    public AttachmentDto setSize(long size) {
        super.setSize(size);
        return this;
    }
}
