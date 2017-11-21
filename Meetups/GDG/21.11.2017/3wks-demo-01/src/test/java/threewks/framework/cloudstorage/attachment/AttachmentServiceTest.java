package threewks.framework.cloudstorage.attachment;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import threewks.framework.cloudstorage.GoogleCloudStorageJsonApiClient;
import threewks.framework.shared.util.PropertyHelper;

import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static threewks.util.RandomUtil.uuid;

@RunWith(MockitoJUnitRunner.class)
public class AttachmentServiceTest {
    private static final String DEFAULT_BUCKET = "gcsDefaultBucket";
    private static final String HOST = "www.example.org";
    private static final int UUID_LENGTH = uuid().length();

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Mock
    private GoogleCloudStorageJsonApiClient cloudStorage;
    @Mock
    private PropertyHelper propertyHelper;

    private AttachmentService attachmentService;

    private Set<String> permittedAttachmentFolders;

    @Before
    public void setUp() throws Exception {
        permittedAttachmentFolders = Sets.newHashSet(AttachmentService.DEFAULT_ATTACHMENTS_FOLDER);
        when(propertyHelper.getPropertySet("gcsAttachmentFolders", AttachmentService.DEFAULT_ATTACHMENTS_FOLDER)).thenReturn(permittedAttachmentFolders);
        attachmentService = new AttachmentService(cloudStorage, DEFAULT_BUCKET, HOST, propertyHelper);
    }

    @Test
    public void getUploadUrl() throws Exception {
        permittedAttachmentFolders.add("some-folder");
        when(cloudStorage.initiateResumableUpload(eq(DEFAULT_BUCKET), any(String.class), eq("file-name.pdf"), eq("type"), eq("origin")))
            .thenReturn("https://some-cloud-address.com");

        String response = attachmentService.getUploadUrl("some-folder", "type", "file-name.pdf", "origin");

        assertThat(response, is("https://some-cloud-address.com"));
    }

    @Test
    public void getUploadUrl_willFail_whenAttachmentFolderNotAllowed() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Folder not permitted for GCS attachments: some-folder");

        String response = attachmentService.getUploadUrl("some-folder", "type", "file-name.pdf", "origin");

        assertThat(response, is("https://some-cloud-address.com"));
    }

    @Test
    public void getUploadUrl_willUseDefaultFolder_whenNoFolderSpecified() throws Exception {
        when(cloudStorage.initiateResumableUpload(eq(DEFAULT_BUCKET), any(String.class), eq("file-name.pdf"), eq("type"), eq("origin")))
            .thenReturn("https://some-cloud-address.com");

        String response = attachmentService.getUploadUrl("type", "file-name.pdf","origin");

        assertThat(response, is("https://some-cloud-address.com"));
    }

    @Test
    public void getDownloadUrl() {
        when(cloudStorage.generateSignedUrl(DEFAULT_BUCKET, "test", AttachmentService.DEFAULT_LINK_EXPIRY_DURATION))
            .thenReturn("http://some-link.com");

        assertThat(attachmentService.getDownloadUrl("test"), is("http://some-link.com"));
    }

    @Test
    public void getDownloadUrl_willEscapeFilenamePortion_whenFilenamePortionContainsSpecialChars() {
        when(cloudStorage.generateSignedUrl(DEFAULT_BUCKET, "a/b/c/TEST%202017-07-27%20at%202.00.37.pm.png", AttachmentService.DEFAULT_LINK_EXPIRY_DURATION))
            .thenReturn("http://some-link.com");

        assertThat(attachmentService.getDownloadUrl("a/b/c/TEST 2017-07-27 at 2.00.37.pm.png"), is("http://some-link.com"));
    }

    @Test
    public void buildBasePath() {

        String result = attachmentService.buildBasePath("my-folder");

        assertThat(result, startsWith("my-folder/"));
        assertThat(result.length(), is("my-folder/".length() + UUID_LENGTH));
    }

}
