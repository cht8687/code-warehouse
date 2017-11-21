package threewks.framework.usermanager.repository;

import com.googlecode.objectify.cmd.LoadType;
import com.threewks.thundr.search.Search;
import com.threewks.thundr.search.gae.SearchConfig;
import com.threewks.thundr.user.gae.UserGae;
import com.threewks.thundr.user.gae.UserRepositoryImpl;
import org.apache.commons.lang3.StringUtils;
import threewks.framework.usermanager.model.AppUser;
import threewks.framework.usermanager.model.UserStatus;

import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;
import static threewks.framework.shared.repository.BaseRepository.SEARCH_LAST_CHAR;

/**
 * This Repository class aims to get around limitations of {@link com.googlecode.objectify.annotation.Subclass} combined
 * with indexed queries and search indexes. By having a concrete subclass of {@link UserGae} you can add fields that are
 * easier to use throughout code than map entries, and also have the power to index additional fields: something not possible
 * by using {@link UserGae} itself.
 *
 * @param <U> - ultimately will be {@link AppUser} in the instance that is injected and used in code. Type erasure allows
 *            us to create the <i>actual</i> instance with {@link UserGae}. This assumes that all users are stored as {@link AppUser},
 *            which ultimately makes this safe.
 */
public class AppUserRepository<U extends UserGae> extends UserRepositoryImpl<U> {

    public AppUserRepository(Class<U> entityType, SearchConfig searchConfig) {
        super(entityType, searchConfig);
    }

    @Override
    public List<U> list() {
        return load()
            .filter(AppUser.Fields.Status, UserStatus.ACTIVE)
            .list();
    }

    public List<U> listAll() {
        return load()
            .list();
    }

    public List<U> searchByEmail(String email) {
        String searchString = StringUtils.defaultString(email).toLowerCase();
        return load()
            .filter(UserGae.Fields.Email + " >=", searchString)
            .filter(UserGae.Fields.Email + " <", searchString + SEARCH_LAST_CHAR)
            .list();
    }

    @SuppressWarnings("unchecked")
    private LoadType<U> load() {
        return (LoadType<U>) ofy().load().type(UserGae.class);
    }

    /**
     * You can only use search service for fields on {@link UserGae}. Be sure to only
     * use fields from {@link UserGae.Fields}.
     *
     * @return {@link Search} casted for use with {@link U} although internally it can only search with {@link UserGae.Fields}.
     */
    private Search<U, String> createSearch() {
        return search();
    }


}
