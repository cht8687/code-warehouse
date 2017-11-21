package threewks.framework.cloudstorage.attachment.transformer;

import org.joda.time.DateTime;
import org.junit.Rule;
import org.junit.Test;
import threewks.BaseTest;
import threewks.RunWithUser;
import threewks.framework.cloudstorage.attachment.AttachmentDto;
import threewks.framework.cloudstorage.attachment.model.Attachment;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static threewks.Matchers.hasFieldWithUserRef;

public class ToAttachmentEntityTest extends BaseTest {

    @Rule
    public RunWithUser runWithUser = new RunWithUser();

    @Test
    public void apply() throws Exception {
        AttachmentDto dto = new AttachmentDto()
            .setBucket("bucket")
            .setContentType("content-type")
            .setFilename("filename")
            .setGcsObjectName("gcsObjectName")
            .setId("id")
            .setName("name")
            .setSize(12345L);


        Attachment entity = new ToAttachmentEntity().apply(dto);

        assertThat(entity, notNullValue());
        assertThat(entity.getBucket(), is("bucket"));
        assertThat(entity.getContentType(), is("content-type"));
        assertThat(entity.getFilename(), is("filename"));
        assertThat(entity.getGcsObjectName(), is("gcsObjectName"));
        assertThat(entity.getId(), is("id"));
        assertThat(entity.getName(), is("name"));
        assertThat(entity.getSize(), is(12345L));
        assertThat(entity.getCreated(), is(DateTime.now()));
        assertThat(entity, hasFieldWithUserRef("uploadedBy", runWithUser.getUser()));
    }
}
