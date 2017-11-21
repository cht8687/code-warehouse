package threewks.framework.cloudstorage.attachment;

import com.threewks.thundr.view.json.JsonView;
import com.threewks.thundr.view.redirect.RedirectView;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static threewks.WebAsserts.assertOk;

@RunWith(MockitoJUnitRunner.class)
public class AttachmentControllerTest {

    @Mock
    private AttachmentService attachmentService;

    @InjectMocks
    private AttachmentController attachmentController;

    @Test
    public void get() {
        when(attachmentService.getDownloadUrl("some-id")).thenReturn("http://some-url.com");

        RedirectView result = attachmentController.get("some-id");

        assertThat(result.getRedirect(), is("http://some-url.com"));
    }

    @Test
    public void generateAttachmentUploadUrl() {
        StorageUrlRequestDto request = new StorageUrlRequestDto();
        request.setName("name");
        request.setType("type");

        when(attachmentService.getUploadUrl("type", "name", "origin")).thenReturn("http://the-download-url");

        JsonView result = attachmentController.generateAttachmentUploadUrl(request, "origin");

        assertOk(result, "http://the-download-url");
    }

    @Test
    public void generateAttachmentUploadUrlForFolder() {
        StorageUrlRequestDto request = new StorageUrlRequestDto();
        request.setName("name");
        request.setType("type");

        when(attachmentService.getUploadUrl("some-folder", "type", "name", "origin")).thenReturn("http://the-download-url");

        JsonView result = attachmentController.generateAttachmentUploadUrlForFolder(request, "some-folder", "origin");

        assertOk(result, "http://the-download-url");
    }

}
