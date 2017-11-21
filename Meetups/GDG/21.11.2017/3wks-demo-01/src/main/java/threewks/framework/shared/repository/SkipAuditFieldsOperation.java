package threewks.framework.shared.repository;

import com.threewks.thundr.gae.objectify.repository.ReindexOperation;
import threewks.model.BaseEntity;

import java.util.List;

public class SkipAuditFieldsOperation<T extends BaseEntity> implements ReindexOperation<T> {

    public static <E extends BaseEntity> SkipAuditFieldsOperation<E> instance() {
        return new SkipAuditFieldsOperation<>();
    }

    @Override
    public List<T> apply(List<T> batch) {
        for (T form : batch) {
            form.skipSettingAuditableFields();
        }
        return batch;
    }

}
