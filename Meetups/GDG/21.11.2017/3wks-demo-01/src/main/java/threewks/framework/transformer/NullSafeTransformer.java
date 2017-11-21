package threewks.framework.transformer;

import com.google.common.base.Function;

import javax.annotation.Nullable;

/**
 * Basic transformer that protects the {@link #transform(Object)} method from being passed a null input.
 * The transformer will always return {@code null} when passed a {@link null}.
 *
 * @param <I> Input type.
 * @param <O> Output type.
 */
public abstract class NullSafeTransformer<I, O> implements Function<I, O> {

    @Nullable
    @Override
    public O apply(@Nullable I input) {
        return input != null ? transform(input) : null;
    }

    protected abstract O transform(I input);
}
