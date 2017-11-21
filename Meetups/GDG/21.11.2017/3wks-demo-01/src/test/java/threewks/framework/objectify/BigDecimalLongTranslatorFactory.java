package threewks.framework.objectify;

import com.googlecode.objectify.impl.Path;
import com.googlecode.objectify.impl.translate.CreateContext;
import com.googlecode.objectify.impl.translate.LoadContext;
import com.googlecode.objectify.impl.translate.SaveContext;
import com.googlecode.objectify.impl.translate.SkipException;
import com.googlecode.objectify.impl.translate.TypeKey;
import com.googlecode.objectify.impl.translate.ValueTranslator;
import com.googlecode.objectify.impl.translate.ValueTranslatorFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Stores as a {@link Long} by using an internal factor for multiplication.
 * An improvement over {@link com.googlecode.objectify.impl.translate.opt.BigDecimalLongTranslatorFactory}:
 * <ul>
 * <li>Configure <code>scale</code> (number of decimal places) instead of exact <code>factor</code></li>
 * <li>Configure a {@link RoundingMode} instead of mandatory failures when precision extends beyond scale</li>
 * </ul>
 *
 * <p>
 *     Defaults: <pre>scale: 6, roundingMode: HALF_UP</pre>.
 * </p>
 */
public class BigDecimalLongTranslatorFactory extends ValueTranslatorFactory<BigDecimal, Long> {
    private final BigDecimal factor;
    private final RoundingMode roundingMode;

    /**
     * Construct this converter with the default factor (1000), which can store three points of
     * precision past the decimal point.
     */
    public BigDecimalLongTranslatorFactory() {
        this(6, RoundingMode.HALF_UP);
    }


    public BigDecimalLongTranslatorFactory(int scale, RoundingMode roundingMode) {
        super(BigDecimal.class);
        this.factor = BigDecimal.ONE.scaleByPowerOfTen(scale);
        this.roundingMode = roundingMode;
    }

    @Override
    protected ValueTranslator<BigDecimal, Long> createValueTranslator(TypeKey<BigDecimal> tk, CreateContext ctx, Path path) {
        return new ValueTranslator<BigDecimal, Long>(Long.class) {
            @Override
            protected BigDecimal loadValue(Long value, LoadContext ctx, Path path) throws SkipException {
                return new BigDecimal(value).divide(factor);
            }

            @Override
            protected Long saveValue(BigDecimal value, boolean index, SaveContext ctx, Path path) throws SkipException {
                return value.multiply(factor).setScale(0, roundingMode).longValueExact();
            }
        };
    }
}
