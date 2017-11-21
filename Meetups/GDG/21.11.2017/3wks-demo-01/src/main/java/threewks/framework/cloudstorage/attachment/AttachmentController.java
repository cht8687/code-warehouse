package threewks.framework.cloudstorage.attachment;

import com.threewks.thundr.view.json.JsonView;
import com.threewks.thundr.view.redirect.RedirectView;

import static threewks.util.RestHelper.response;

public class AttachmentController {
    private final AttachmentService attachmentService;

    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    public JsonView generateAttachmentUploadUrl(StorageUrlRequestDto body, String origin) {
        return response(
            attachmentService.getUploadUrl(body.getType(), body.getName(), origin)
        );
    }

    public JsonView generateAttachmentUploadUrlForFolder(StorageUrlRequestDto body, String folder, String origin) {
        return response(
            attachmentService.getUploadUrl(folder, body.getType(), body.getName(), origin)
        );
    }

    public RedirectView get(String id) {
        return new RedirectView(
            attachmentService.getDownloadUrl(id)
        );
    }

}
