package threewks;

import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.impl.translate.TranslatorFactory;
import com.threewks.thundr.gae.objectify.SetupObjectify;
import com.threewks.thundr.user.gae.UserGae;
import threewks.framework.usermanager.UserManagerModule;

import java.util.Set;

/**
 * An easy central way to configure how tests that require objectify are setup. Configures objectify with all
 * of the same entities and translators that are registered in {@link ApplicationModule}.
 * <p>
 * In tests that require objectify and app engine, define the following:"
 * <code>
 *
 * @Rule public final SetupObjectify setupObjectify = new TestObjectify();
 * @Rule public final SetupAppengine setupAppengine = new SetupAppengine();
 * </code>
 */
public class TestObjectify extends SetupObjectify {

    public TestObjectify() {
        super(getEntities());
    }

    private static Class[] getEntities() {
        final Set<Class<?>> classes = ApplicationModule.objectifyEntities();
        classes.add(UserGae.class);
        classes.addAll(UserManagerModule.ENTITIES);
        return classes.toArray(new Class[classes.size()]);
    }

    @Override
    protected void addTranslators(ObjectifyFactory factory) {
        for (TranslatorFactory translatorFactory : ApplicationModule.objectifyTranslatorFactories()) {
            factory.getTranslators().add(translatorFactory);
        }
    }

}
