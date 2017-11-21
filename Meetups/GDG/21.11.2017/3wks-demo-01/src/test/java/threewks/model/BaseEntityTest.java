package threewks.model;

import com.threewks.thundr.gae.objectify.Refs;
import com.threewks.thundr.test.TestSupport;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import threewks.BaseTest;
import threewks.TestData;
import threewks.framework.usermanager.context.SecurityContextHolder;
import threewks.framework.usermanager.model.AppUser;

import static com.threewks.thundr.gae.objectify.Refs.key;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.joda.time.DateTime.now;
import static org.junit.Assert.assertThat;

public class BaseEntityTest extends BaseTest {
    private BaseEntity entity = new BaseEntity() {
    };

    private AppUser user1;
    private AppUser user2;

    @Before
    public void before() {
        user1 = TestData.user("user1@email.com");
        user2 = TestData.user("user2@email.com");
    }

    @Test
    public void setAuditableFieldsOnSave_willSetAuditFields_whenFirstTimeCalled() {
        SecurityContextHolder.get().setUser(user1);

        TestData.setAuditableFieldsOnSave(entity);

        assertThat(entity.getCreated(), is(now()));
        assertThat(entity.getCreatedByKey(), is(key(user1)));
        assertThat(entity.getUpdated(), is(now()));
        assertThat(entity.getUpdatedByKey(), is(key(user1)));

    }

    @Test
    public void setAuditableFieldsOnSave_willOverwriteExistingUpdated_AndNotCreated_whenExistingValues() {
        DateTime lastYear = now().minusYears(1);
        TestSupport.setField(entity, "created", lastYear);
        TestSupport.setField(entity, "createdBy", Refs.ref(user1));
        TestSupport.setField(entity, "updated", now().minusMonths(1));
        TestSupport.setField(entity, "updatedBy", Refs.ref(user1));
        SecurityContextHolder.get().setUser(user2);

        TestData.setAuditableFieldsOnSave(entity);

        assertThat(entity.getCreated(), is(lastYear));
        assertThat(entity.getCreatedByKey(), is(key(user1)));
        assertThat(entity.getUpdated(), is(now()));
        assertThat(entity.getUpdatedByKey(), is(key(user2)));
    }

    @Test
    public void setAuditableFieldsOnSave_willLeaveCreatedByNull_whenOriginalCreatedByWasNull() {
        DateTime lastYear = now().minusYears(1);
        TestSupport.setField(entity, "created", lastYear);
        TestSupport.setField(entity, "createdBy", null);
        SecurityContextHolder.get().setUser(user2);

        TestData.setAuditableFieldsOnSave(entity);

        assertThat(entity.getCreated(), is(lastYear));
        assertThat(entity.getCreatedByKey(), nullValue());
    }

    @Test
    public void setAuditableFieldsOnSave_willNotUpdateAnything_whenSkipSettingAuditableFieldsCalled() {
        entity.skipSettingAuditableFields();

        TestData.setAuditableFieldsOnSave(entity);

        assertThat(entity.getCreated(), nullValue());
        assertThat(entity.getCreatedByKey(), nullValue());
        assertThat(entity.getUpdated(), nullValue());
        assertThat(entity.getUpdatedByKey(), nullValue());
    }
}

