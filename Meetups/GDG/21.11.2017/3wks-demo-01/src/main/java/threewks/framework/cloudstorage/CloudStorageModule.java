package threewks.framework.cloudstorage;

import com.google.api.client.extensions.appengine.http.UrlFetchTransport;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.extensions.appengine.auth.oauth2.AppIdentityCredential;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.appengine.api.appidentity.AppIdentityService;
import com.google.appengine.api.appidentity.AppIdentityServiceFactory;
import com.threewks.thundr.configuration.ConfigurationException;
import com.threewks.thundr.configuration.Environment;
import com.threewks.thundr.injection.BaseModule;
import com.threewks.thundr.injection.UpdatableInjectionContext;
import com.threewks.thundr.route.Router;
import threewks.framework.cloudstorage.attachment.AttachmentController;
import threewks.framework.shared.util.PropertyHelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static com.threewks.thundr.configuration.Environment.DEV;
import static java.util.Collections.singletonList;

/**
 * Enabling this module will require setting up credentials for accessing GCS from a local environment. See
 * <a href="https://developers.google.com/identity/protocols/application-default-credentials">https://developers.google.com/identity/protocols/application-default-credentials</a>
 * for instructions on setting up default credentials and ensure you store the json file as {@value DEV_CREDENTIALS_FILE} in {@code src/main/resources}.
 */
public class CloudStorageModule extends BaseModule {

    private static final String DEV_CREDENTIALS_FILE = "/dev-gcs-credentials.json";
    private static final List<String> STORAGE_SCOPES = singletonList("https://www.googleapis.com/auth/devstorage.full_control");

    @Override
    public void configure(UpdatableInjectionContext injectionContext) {
        super.configure(injectionContext);

        HttpTransport httpTransport = new UrlFetchTransport();
        JsonFactory jsonFactory = new GsonFactory();
        injectionContext.inject(jsonFactory).as(JsonFactory.class);

        Router router = injectionContext.get(Router.class);
        attachmentsRoutes(router);

        GoogleCredential googleCredential;
        if (Environment.is(DEV)) {
            try (InputStream jsonCredentials = getClass().getResourceAsStream(DEV_CREDENTIALS_FILE)) {
                googleCredential = GoogleCredential
                    .fromStream(jsonCredentials, httpTransport, jsonFactory)
                    .createScoped(STORAGE_SCOPES);
            } catch (IOException e) {
                throw new ConfigurationException(e,
                    "Cloud storage client configuration failed. Ensure you have a local credentials file created in src/main/resources/%s." +
                        "See https://developers.google.com/identity/protocols/application-default-credentials. Alternatively you can remove " +
                        "this %s from your ApplicationModule if you do not require Google Cloud Storage (e.g. file uploads).",
                    DEV_CREDENTIALS_FILE, getClass().getSimpleName());
            }
        } else {
            try {
                googleCredential = new AppIdentityCredential.AppEngineCredentialWrapper(httpTransport, jsonFactory)
                    .createScoped(STORAGE_SCOPES);
            } catch (IOException e) {
                throw new ConfigurationException(e, "Cloud storage client configuration failed: %s", e.getMessage());
            }
        }

        injectionContext.inject(new PropertyHelper(injectionContext)).as(PropertyHelper.class);
        injectionContext.inject(AppIdentityServiceFactory.getAppIdentityService()).as(AppIdentityService.class);
        injectionContext.inject(googleCredential).named("gcsCredential").as(GoogleCredential.class);

        HttpRequestInitializer requestInitializer = googleCredential;
        HttpRequestFactory requestFactory = httpTransport.createRequestFactory(requestInitializer);
        injectionContext.inject(requestFactory).as(HttpRequestFactory.class);

        injectionContext.inject(GoogleCloudStorageJsonApiClient.class).as(GoogleCloudStorageJsonApiClient.class);
    }

    /**
     * This is public so that apps may refer to it if they wish to add it to any excluded routes. Defines all routes
     * that are used for attachments and is automatically configured by this module.
     *
     * @param router
     */
    public static void attachmentsRoutes(Router router) {
        router.post("/api/attachments", AttachmentController.class, "generateAttachmentUploadUrl");
        router.post("/api/attachments/{folder}", AttachmentController.class, "generateAttachmentUploadUrlForFolder");
        router.get("/api/attachments/{id}", AttachmentController.class, "get");
    }

}
