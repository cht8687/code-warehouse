package threewks.framework.shared.repository;

import com.atomicleopard.expressive.ETransformer;
import com.googlecode.objectify.Key;
import com.threewks.thundr.search.gae.SearchConfig;

import java.util.List;

public abstract class KeyRepository<E> extends BaseRepository<E, Key<E>> {

    public KeyRepository(Class<E> entityType, SearchConfig searchConfig) {
        super(entityType, KeyRepository.<E>noopTransformer(), KeyRepository.<E>noopTransformer(), searchConfig);
    }

    public List<E> listByAncestor(Key<?> ancestor) {
        return load()
            .ancestor(ancestor)
            .list();
    }

    static <E> ETransformer<Key<E>, Key<E>> noopTransformer() {
        return from -> from;
    }
}
