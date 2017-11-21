package threewks.framework.cloudstorage.attachment.transformer;

import org.junit.Test;
import threewks.framework.cloudstorage.attachment.AttachmentDto;
import threewks.framework.cloudstorage.attachment.model.Attachment;
import threewks.framework.usermanager.controller.dto.transformer.ToUserDto;
import threewks.framework.usermanager.model.AppUser;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

public class ToAttachmentDtoTest {

    @Test
    public void apply() throws Exception {
        Attachment entity = spy(new Attachment()
            .setBucket("bucket")
            .setContentType("content-type")
            .setFilename("filename")
            .setGcsObjectName("gcsObjectName")
            .setId("id")
            .setName("name")
            .setSize(12345L)
        );

        AppUser user = new AppUser("user");
        doReturn(user).when(entity).getUploadedBy();

        AttachmentDto dto = new ToAttachmentDto().apply(entity);

        assertThat(dto, notNullValue());
        assertThat(dto.getBucket(), is("bucket"));
        assertThat(dto.getContentType(), is("content-type"));
        assertThat(dto.getFilename(), is("filename"));
        assertThat(dto.getGcsObjectName(), is("gcsObjectName"));
        assertThat(dto.getId(), is("id"));
        assertThat(dto.getName(), is("name"));
        assertThat(dto.getSize(), is(12345L));
        assertThat(dto.getCreated(), is(entity.getCreated()));
        assertThat(dto.getUploadedBy(), is(ToUserDto.INSTANCE.apply(user)));
    }
}
