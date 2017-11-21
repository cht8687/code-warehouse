package threewks.framework.cloudstorage.attachment.transformer;

import threewks.framework.cloudstorage.attachment.NewAttachmentDto;
import threewks.framework.cloudstorage.attachment.model.Attachment;
import threewks.framework.transformer.NullSafeTransformer;
import threewks.framework.usermanager.context.SecurityContextHolder;
import threewks.framework.usermanager.model.AppUser;

public class ToAttachmentEntity extends NullSafeTransformer<NewAttachmentDto, Attachment> {
    @Override
    public Attachment transform(NewAttachmentDto input) {
        return new Attachment()
            .setBucket(input.getBucket())
            .setContentType(input.getContentType())
            .setFilename(input.getFilename())
            .setGcsObjectName(input.getGcsObjectName())
            .setId(input.getId())
            .setName(input.getName())
            .setSize(input.getSize())
            .setUploadedBy((AppUser) SecurityContextHolder.get().getUser());
    }
}
