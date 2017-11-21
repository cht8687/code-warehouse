package threewks.framework.shared.repository;

import com.atomicleopard.expressive.ETransformer;
import com.googlecode.objectify.Key;
import com.threewks.thundr.search.gae.SearchConfig;

public abstract class StringRepository<E> extends BaseRepository<E, String> {

    public StringRepository(Class<E> entityType, SearchConfig searchConfig) {
        super(entityType, fromString(entityType), toString(entityType), searchConfig);
    }

    static <E> ETransformer<String, Key<E>> fromString(final Class<E> type) {
        return from -> Key.create(type, from);
    }

    static <E> ETransformer<Key<E>, String> toString(final Class<E> type) {
        return Key::getName;
    }
}
