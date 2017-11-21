package threewks;

import threewks.framework.usermanager.model.AppUser;
import threewks.model.BaseEntity;

import java.lang.reflect.Method;

import static java.util.UUID.randomUUID;

public class TestData {

    public static AppUser user() {
        return user("dummy@example.com");
    }

    public static AppUser user(String email) {
        AppUser user = new AppUser(randomUUID().toString());
        user.setEmail(email);
        return user;
    }

    public static void setAuditableFieldsOnSave(BaseEntity entity) {
        try {
            Method method = BaseEntity.class.getDeclaredMethod("setAuditableFieldsOnSave");
            method.setAccessible(true);
            method.invoke(entity);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Unexpected exception calling setAuditableFieldsOnSave", e);
        }

    }
}
