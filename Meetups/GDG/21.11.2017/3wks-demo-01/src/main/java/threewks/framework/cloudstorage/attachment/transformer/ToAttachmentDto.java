package threewks.framework.cloudstorage.attachment.transformer;

import threewks.framework.cloudstorage.attachment.AttachmentDto;
import threewks.framework.cloudstorage.attachment.model.Attachment;
import threewks.framework.transformer.NullSafeTransformer;
import threewks.framework.usermanager.controller.dto.transformer.ToUserDto;

public class ToAttachmentDto extends NullSafeTransformer<Attachment, AttachmentDto> {
    @Override
    public AttachmentDto transform(Attachment input) {
        return new AttachmentDto()
            .setBucket(input.getBucket())
            .setContentType(input.getContentType())
            .setFilename(input.getFilename())
            .setGcsObjectName(input.getGcsObjectName())
            .setId(input.getId())
            .setName(input.getName())
            .setSize(input.getSize())
            .setCreated(input.getCreated())
            .setUploadedBy(ToUserDto.INSTANCE.apply(input.getUploadedBy()));
    }
}
