package threewks.framework.shared.repository;

import com.atomicleopard.expressive.ETransformer;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.LoadType;
import com.googlecode.objectify.cmd.Query;
import com.threewks.thundr.gae.objectify.repository.AbstractRepository;
import com.threewks.thundr.gae.objectify.repository.ReindexOperation;
import com.threewks.thundr.search.gae.SearchConfig;
import threewks.model.BaseEntity;

import java.util.ArrayList;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

public abstract class BaseRepository<E, K> extends AbstractRepository<E, K> {
    public static final char SEARCH_LAST_CHAR = '\ufffd';
    private static final int BATCH_SIZE = 200;


    public BaseRepository(Class<E> entityType, ETransformer<K, Key<E>> toKey, ETransformer<Key<E>, K> fromKey, SearchConfig searchConfig) {
        super(entityType, toKey, fromKey, searchConfig);
    }

    /**
     * Convenience method for sub-classes, so you don't need to static import and write {@code ofy().load().type(MyType.class)} for every
     * query.
     *
     * @return Loader from {@code ofy().load().type(MyType.class)} for executing queries.
     */
    protected LoadType<E> load() {
        return ofy().load().type(this.entityType);
    }

    public boolean notExists(K key) {
        return !exists(key);
    }

    public boolean exists(K key) {
        return get(key) != null;
    }

    public List<E> getByKeys(Iterable<Key<E>> keys) {
        return loadInternal(keys);
    }

    public List<E> listAll() {
        return listAllForQuery(ofy().load().type(entityType));
    }

    public List<E> listAllForQuery(Query<E> query) {
        QueryResultIterator<E> iterator = query.iterator();
        List<E> entities = new ArrayList<>();
        while (iterator.hasNext()) {
            entities.add(iterator.next());
            if (!iterator.hasNext()) {
                iterator = query.startAt(iterator.getCursor()).iterator();
            }
        }
        return entities;
    }

    public E loadByWebsafeKey(String websafeKey) {
        Key<E> key = Key.create(websafeKey);
        return loadInternal(key);
    }

    /**
     * Delete all entities in batches of {@value BATCH_SIZE}.
     *
     * @return number of entities deleted
     */
    public long deleteAll() {
        Query<E> query = ofy().load().type(entityType).limit(BATCH_SIZE);
        QueryResultIterator<Key<E>> iterator = query.keys().iterator();
        List<Key<E>> keysToDelete = new ArrayList<>();
        long numDeleted = 0;
        while (iterator.hasNext()) {
            Key<E> key = iterator.next();
            keysToDelete.add(key);
            if (!iterator.hasNext()) {
                deleteByKey(fromKeys.from(keysToDelete));
                numDeleted += keysToDelete.size();
                keysToDelete.clear();
                iterator = query.startAt(iterator.getCursor()).keys().iterator();
            }
        }
        return numDeleted;
    }

    /**
     * Clear all records in the search index.
     */
    public void clearSearchIndex() {
        searchService.removeAll();
    }

    /**
     * Reindex all DataStore entities for this Kind and all docs in the Search Index.
     * <p>
     * Use with care i.e. if there are heaps of
     * entities then consider triggering this from a queue or a backend (so that the request
     * has more time to complete). Otherwise, craft your own mechanism to reindex a batch
     * then create a queue task to continue on (from a cursor).
     *
     * @return number of entities reindexed
     */
    public int reindexDataAndSearch() {
        //noinspection unchecked
        ReindexOperation<E> reindexOperation = BaseEntity.class.isAssignableFrom(entityType)
            ? (ReindexOperation<E>) SkipAuditFieldsOperation.instance()
            : IDENTITY_REINDEX_OP;
        return reindex(reindexOperation);
    }

    /**
     * Find all entities and reindex their associated docs in the Search Index.
     *
     * @return number of entities reindexed
     */
    public int reindexSearch() {
        // by not supplying a ReindexOperation, DataStore entities will
        // not be mutated, hence will not be saved
        return reindex(null);
    }

    /**
     * Find all entities and reindex their associated docs in the Search Index. If
     * a non-null ReindexOperation is supplied then entities will have that operation
     * applied to them prior to being saved in DataStore and saved in the Search Index.
     * <p>
     * A null ReindexOperation will result in no DataStore updates, but only Search Index updates.
     * <p>
     * Use with care i.e. if there are heaps of
     * entities then consider triggering this from a queue or a backend (so that the request
     * has more time to complete). Otherwise, craft your own mechanism to reindex a batch
     * then create a queue task to continue on (from a cursor).
     *
     * @return number of entities reindexed
     */
    public int reindex(ReindexOperation<E> reindexOperation) {
        List<Key<E>> keys = load().keys().list();
        return super.reindex(fromKeys.from(keys), BATCH_SIZE, reindexOperation);
    }

    private final ReindexOperation<E> IDENTITY_REINDEX_OP = batch -> batch;
}
