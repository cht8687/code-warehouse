package threewks.framework.cloudstorage.attachment;

import com.google.common.net.UrlEscapers;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import threewks.framework.cloudstorage.GoogleCloudStorageJsonApiClient;
import threewks.framework.shared.util.PropertyHelper;
import threewks.util.Assert;
import threewks.util.RandomUtil;

import java.util.Set;

public class AttachmentService {
    public static final String DEFAULT_ATTACHMENTS_FOLDER = "attachments";
    public static final long DEFAULT_LINK_EXPIRY_DURATION = 3600L;

    private final GoogleCloudStorageJsonApiClient cloudStorage;
    private final Set<String> permittedAttachmentPaths;
    private final String gcsDefaultBucket;
    private final String host;

    public AttachmentService(GoogleCloudStorageJsonApiClient cloudStorage, String gcsDefaultBucket, String host, PropertyHelper propertyHelper) {
        this.permittedAttachmentPaths = propertyHelper.getPropertySet("gcsAttachmentFolders", DEFAULT_ATTACHMENTS_FOLDER);
        this.cloudStorage = cloudStorage;
        this.gcsDefaultBucket = gcsDefaultBucket;
        this.host = host;
    }

    /**
     * Generate a cloud storage upload url under the default {@value DEFAULT_ATTACHMENTS_FOLDER} folder.
     *
     * @param type   File MIME type.
     * @param origin Upload origin. (The system that the file will be uploaded from).
     * @return Upload url.
     */
    public String getUploadUrl(String type, String filename, String origin) {
        return getUploadUrl(DEFAULT_ATTACHMENTS_FOLDER, type, filename, origin);
    }

    /**
     * Generate a cloud storage upload url.
     *
     * @param folder Folder to store the attachment under.
     * @param type   File MIME type.
     * @param filename The file name to store the file as and ultimately download it as.
     * @param origin Upload origin. (The system that the file will be uploaded from).
     * @return Upload url.
     */
    public String getUploadUrl(String folder, String type, String filename, String origin) {
        Assert.notBlank(folder, "folder required");
        Assert.notBlank(filename, "filename required");
        String originHost = StringUtils.isNotBlank(origin) ? origin : host;
        Assert.isTrue(permittedAttachmentPaths.contains(folder), "Folder not permitted for GCS attachments: %s", folder);
        String base = buildBasePath(folder);
        return cloudStorage.initiateResumableUpload(gcsDefaultBucket, base, filename, type, originHost);
    }

    /**
     * Get a signed url to view an attachment. The url will last 24 hours. The file name portion of the id is url escaped internally.
     *
     * @param id The id of the attachment.
     * @return Url
     */
    public String getDownloadUrl(String id) {
        String filename = FilenameUtils.getName(id);
        String path = FilenameUtils.getFullPath(id);
        String escapedFullPath = FilenameUtils.concat(path, UrlEscapers.urlFragmentEscaper().escape(filename));
        return cloudStorage.generateSignedUrl(gcsDefaultBucket, escapedFullPath, DEFAULT_LINK_EXPIRY_DURATION);
    }

    /**
     * Build a unique base path. This is {@code protected} to allow any sub-classes to override strategy if they extend. By default it uses
     * the specified base path, with a UUID "sub-folder". The base path is unique so that we can preserve the filename that the user specified.
     *
     * @param folder
     * @return unique base path.
     */
    protected String buildBasePath(String folder) {
        return String.format("%s/%s", folder, RandomUtil.uuid());
    }

}
