package threewks;

import com.googlecode.objectify.Ref;
import com.threewks.thundr.gae.objectify.repository.Repository;
import com.threewks.thundr.test.TestSupport;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@SuppressWarnings("unchecked")
public class MockHelpers {

    /**
     * Mocks the {@link Repository} behaviour of returning the same entity when a {@link Repository#put(Object)}
     */
    @SuppressWarnings("unchecked")
    public static <T> void mockPut(Repository<T, ?> repository, Class<T> entityType) {
        returnFirstArgument(repository.put(any(entityType)));
    }

    /**
     * Mocks the {@link Repository} behaviour of returning the same entity when a {@link Repository#put(List)}
     */
    public static <T> void mockPutList(Repository<T, ?> repository) {
        returnFirstArgument(repository.put(any(List.class)));
    }

    /**
     * Convenience for returning the first argument of any method call on a mock.
     * Example usages:
     * <ul>
     *     <li><code>returnFirstArgument(myMethod(param1));</code></li>
     *     <li><code>returnFirstArgument(myMethod(any(String.class));</code></li>
     * </ul>
     * <code>
     */
    public static <T> void returnFirstArgument(T methodCall) {
        when(methodCall).thenAnswer(invocation -> invocation.getArgument(0));
    }

    /**
     * Create a {@link Ref<T>} spy internally using {@link #refSpy(Object)} and set the specified fieldName with
     * this ref on the target entity.
     *
     * @param entity Entity that requires a ref
     * @param fieldName Internal field name of the entity to store the ref on
     * @param expectedResult Expected result of {@link Ref#get()}
     * @param <E> Entity type
     * @param <T> Ref type
     *
     * @return Entity instance (entity is mutated, but instance is returned for convenience/chaining).
     */
    public static <E, T> E setRef(E entity, String fieldName, T expectedResult) {
        TestSupport.setField(entity, fieldName, refSpy(expectedResult));
        return entity;
    }

    /**
     * Create a {@link Ref<T>} and spy on the object so that it always returns the expectedResult
     * @param expectedResult Expected result from {@link Ref#get()}.
     * @param <T> Type of expectedResult
     * @return {@link Ref<T>} instance.
     */
    public static <T> Ref<T> refSpy(T expectedResult) {
        Ref<T> ref = spy(Ref.create(expectedResult));
        when(ref.get()).thenReturn(expectedResult);
        return ref;
    }

}
