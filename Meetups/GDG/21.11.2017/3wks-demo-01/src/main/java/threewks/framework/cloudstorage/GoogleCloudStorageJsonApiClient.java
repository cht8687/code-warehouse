package threewks.framework.cloudstorage;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.appengine.api.appidentity.AppIdentityService;
import com.google.common.collect.ImmutableMap;
import com.threewks.thundr.configuration.Environment;
import com.threewks.thundr.http.URLEncoder;
import org.apache.geronimo.mail.util.Base64;
import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.io.IOException;
import java.security.Signature;

/**
 * Google Cloud Storage client that operates via the much fuller-featured JSON API. Requests occur
 * via the {@link HttpRequestFactory} which must be configured via your
 * <code>ApplicationModule</code>.
 *
 * @see <a href="https://cloud.google.com/storage/docs/json_api/">https://cloud.google.com/storage/docs/json_api/</a>
 */
public class GoogleCloudStorageJsonApiClient {
    private static final String BASE_GOOGLE_API_URL = "https://www.googleapis.com";
    private static final String BASE_GOOGLE_STORAGE_URL = "https://storage.googleapis.com";
    private static final String HTTP_METHOD = "GET";
    private static final long TEN_MINUTES = 10L;

    private final HttpRequestFactory httpRequestFactory;
    private final AppIdentityService appIdentityService;
    private final GoogleCredential gcsCredential;

    public GoogleCloudStorageJsonApiClient(HttpRequestFactory httpRequestFactory,
        AppIdentityService appIdentityService,
        GoogleCredential gcsCredential) {
        this.httpRequestFactory = httpRequestFactory;
        this.appIdentityService = appIdentityService;
        this.gcsCredential = gcsCredential;
    }

    /**
     * Initiate a resumable upload direct to the cloud storage API.
     *
     * @param bucket      the cloud storage bucket to upload to
     * @param name        the name of the resource that will be uploaded
     * @param contentType the resource's content/mime type
     * @return the upload URL
     * @see <a href="https://cloud.google.com/storage/docs/json_api/v1/how-tos/resumable-upload">Google Cloud Storage JSON API Overview</a>
     */
    public String initiateResumableUpload(String bucket, String name, String contentType) {
        return initiateResumableUpload(bucket, name, contentType, null);
    }

    public String initiateResumableUpload(String bucket, String folder, String filename, String contentType, String origin) {
        return initiateResumableUpload(
            bucket,
            String.format("%s/%s", folder, filename),
            contentType,
            origin);
    }

    /**
     * Initiate a resumable upload direct to the cloud storage API. Providing an origin will enable
     * CORS requests to the upload URL from the specified origin.
     *
     * @param bucket      the cloud storage bucket to upload to
     * @param name        the name of the resource that will be uploaded
     * @param contentType the resource's content/mime type
     * @param origin      the origin to allow for CORS requests
     * @return the upload URL
     * @see <a href="https://cloud.google.com/storage/docs/json_api/v1/how-tos/resumable-upload">Performing a Resumable Upload</a>
     */
    public String initiateResumableUpload(String bucket, String name, String contentType, String origin) {
        String uploadUrl = String.format("%s/upload/storage/v1/b/%s/o", BASE_GOOGLE_API_URL, bucket);

        GenericUrl url = new GenericUrl(uploadUrl);
        url.put("uploadType", "resumable");
        url.put("name", name);

        HttpHeaders headers = new HttpHeaders();
        headers.put("X-Upload-Content-Type", contentType);
        if (origin != null) {
            headers.put("Origin", origin);  // Add origin header for CORS support
        }

        HttpResponse response;
        try {
            response = httpRequestFactory
                .buildPostRequest(url, null)
                .setHeaders(headers)
                .execute();
        } catch (IOException e) {
            throw new GoogleCloudStorageException(e, "Cannot initiate upload: %s", e.getMessage());
        }

        return response.getHeaders().getLocation();
    }

    /**
     * Generate a signed URL which can be used to access a resource without a Google account. Links
     * expire after a set time period (default 10 minutes).
     *
     * @param bucket             the bucket where the resource is stored
     * @param name               the resource name
     * @param minutesTillExpires the number of minutes from now till link expires. Defaults to 10
     * @return a signed URL for accessing a resource
     * @see <a href="https://cloud.google.com/storage/docs/access-control/signed-urls#signing-gae">Signed URLs</a>
     */
    public String generateSignedUrl(String bucket, String name, Long minutesTillExpires) {
        String canonicalizedResource = String.format("/%s/%s", bucket, name);
        long expires = getExpiration(minutesTillExpires);
        String signature = signRequest(canonicalizedResource, expires);
        String googleAccessId = getGoogleAccessId();

        String queryString = URLEncoder.encodeQueryString(ImmutableMap.<String, Object>of(
            "GoogleAccessId", googleAccessId,
            "Expires", expires,
            "Signature", signature
        ));
        return String.format("%s%s%s",
            BASE_GOOGLE_STORAGE_URL,
            canonicalizedResource,
            queryString);
    }

    private String getGoogleAccessId() {
        if (Environment.is(Environment.DEV)) {
            return gcsCredential.getServiceAccountId();
        }
        return appIdentityService.getServiceAccountName();
    }

    private long getExpiration(Long minutesTillExpires) {
        minutesTillExpires = minutesTillExpires == null ? TEN_MINUTES : minutesTillExpires;
        return DateTime.now().plus(Duration.standardMinutes(minutesTillExpires)).getMillis() / 1000;
    }

    private String signRequest(String canonicalizedResource, long expiration) {
        byte[] data = String.format(
            "%s\n%s\n%s\n%s\n%s",
            HTTP_METHOD,
            "",  // Content MD5 is not required but could added for extra security
            "",  // Content Type is optional and best left out as it isn't included by default in browser GET requests
            expiration,
            canonicalizedResource).getBytes();

        byte[] signature = sign(data);
        return new String(Base64.encode(signature));
    }

    private byte[] sign(byte[] data) {
        byte[] signature;
        if (Environment.is(Environment.DEV)) {
            try {
                Signature rsa = Signature.getInstance("SHA256withRSA");
                rsa.initSign(gcsCredential.getServiceAccountPrivateKey());
                rsa.update(data);
                signature = rsa.sign();
            } catch (Exception e) {
                throw new GoogleCloudStorageException(e, "Error signing URL: %s", e.getMessage());
            }
        } else {
            AppIdentityService.SigningResult signingResult = appIdentityService.signForApp(data);
            signature = signingResult.getSignature();
        }
        return signature;
    }
}
