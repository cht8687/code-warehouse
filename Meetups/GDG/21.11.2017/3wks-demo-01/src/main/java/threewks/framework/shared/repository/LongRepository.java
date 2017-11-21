package threewks.framework.shared.repository;

import com.atomicleopard.expressive.ETransformer;
import com.googlecode.objectify.Key;
import com.threewks.thundr.search.gae.SearchConfig;

public abstract class LongRepository<E> extends BaseRepository<E, Long> {

    public LongRepository(Class<E> entityType, SearchConfig searchConfig) {
        super(entityType, fromLong(entityType), toLong(entityType), searchConfig);
    }

    static <E> ETransformer<Long, Key<E>> fromLong(final Class<E> type) {
        return from -> Key.create(type, from);
    }

    static <E> ETransformer<Key<E>, Long> toLong(final Class<E> type) {
        return Key::getId;
    }
}
