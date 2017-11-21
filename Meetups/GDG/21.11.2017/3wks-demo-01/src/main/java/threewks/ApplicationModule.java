package threewks;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.common.collect.Sets;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.impl.translate.TranslatorFactory;
import com.threewks.thundr.configuration.Environment;
import com.threewks.thundr.gae.GaeModule;
import com.threewks.thundr.gae.objectify.ObjectifyModule;
import com.threewks.thundr.http.StatusCode;
import com.threewks.thundr.injection.BaseModule;
import com.threewks.thundr.injection.UpdatableInjectionContext;
import com.threewks.thundr.module.DependencyRegistry;
import com.threewks.thundr.route.Router;
import com.threewks.thundr.route.controller.FilterRegistry;
import com.threewks.thundr.session.SessionService;
import com.threewks.thundr.user.controller.SessionFilter;
import com.threewks.thundr.user.gae.UserGaeModule;
import threewks.controller.Routes;
import threewks.framework.filter.ExceptionMappingFilter;
import threewks.framework.ref.ReferenceDataService;
import threewks.framework.ref.ReferenceDataWithCode;
import threewks.framework.usermanager.UserManagerModule;
import threewks.framework.usermanager.context.SecurityContextFilter;
import threewks.framework.usermanager.context.SessionHelper;
import threewks.service.bootstrap.BootstrapService;
import threewks.util.DoesNotExistException;

import java.util.Set;

import static threewks.framework.filter.ExceptionMappingFilter.LogLevel.ERROR_WITH_STACKTRACE;

public class ApplicationModule extends BaseModule {

    @Override
    public void requires(DependencyRegistry dependencyRegistry) {
        super.requires(dependencyRegistry);

        dependencyRegistry.addDependency(GaeModule.class);
        dependencyRegistry.addDependency(ObjectifyModule.class);
        dependencyRegistry.addDependency(UserGaeModule.class);
        dependencyRegistry.addDependency(UserManagerModule.class);
    }

    @Override
    public void configure(UpdatableInjectionContext injectionContext) {
        super.configure(injectionContext);

        configureObjectify();
        configureHelpers(injectionContext);
        configureFilters(injectionContext);
        configureReferenceData(injectionContext);
        configureQueues(injectionContext);
        configureRepositories(injectionContext);
        configureServices(injectionContext);
    }

    @Override
    public void start(UpdatableInjectionContext injectionContext) {
        super.start(injectionContext);

        Router router = injectionContext.get(Router.class);
        Routes.addRoutes(router);

        // bootstrap data on local dev only
        if (Environment.is(Environment.DEV)) {
            BootstrapService bootstrapService = injectionContext.get(BootstrapService.class);
            bootstrapService.bootstrapLocal();
        }
    }

    private void configureHelpers(UpdatableInjectionContext injectionContext) {
        injectAsSelf(injectionContext, SessionHelper.class);
    }


    private void configureObjectify() {
        for (TranslatorFactory translatorFactory : objectifyTranslatorFactories()) {
            ObjectifyService.factory().getTranslators().add(translatorFactory);
        }

        for (Class<?> entityClass : objectifyEntities()) {
            ObjectifyService.register(entityClass);
        }
    }

    private void configureFilters(UpdatableInjectionContext injectionContext) {
        FilterRegistry filterRegistry = injectionContext.get(FilterRegistry.class);

        SessionService sessionService = injectionContext.get(SessionService.class);
        SessionHelper sessionHelper = injectionContext.get(SessionHelper.class);
        //noinspection unchecked
        filterRegistry.add(new SessionFilter(sessionService), "/**");

        ExceptionMappingFilter exceptionMappingFilter = new ExceptionMappingFilter()
            .withMapping(IllegalArgumentException.class, StatusCode.BadRequest)
            .withMapping(DoesNotExistException.class, StatusCode.NotFound)
            .withMapping(RuntimeException.class, StatusCode.InternalServerError, ERROR_WITH_STACKTRACE);
        filterRegistry.add(exceptionMappingFilter, "/api/**");
        filterRegistry.add(new SecurityContextFilter(sessionHelper), "/**");
    }

    private void configureQueues(UpdatableInjectionContext injectionContext) {
        // Default queue is adequate for most cases, unless of course you need fine grained control over queue behaviour
        // for specific task types in which case named queues are the way to go.
        injectionContext.inject(QueueFactory.getDefaultQueue()).as(Queue.class);
    }

    private void configureRepositories(UpdatableInjectionContext injectionContext) {
        // Inject repositories here
    }

    private void configureServices(UpdatableInjectionContext injectionContext) {
        injectAsSelf(injectionContext,
            BootstrapService.class
        );
    }

    public static Set<Class<?>> objectifyEntities() {
        return Sets.<Class<?>>newHashSet(
            // Add any entity classes here
        );
    }

    public static Set<TranslatorFactory> objectifyTranslatorFactories() {
        return Sets.newHashSet(
            // Add BigDecimal support here when you need it. BigDecimalLongTransformer requires a decision to be made about expected precision (default 6).
        );
    }

    @SuppressWarnings("unchecked")
    private void configureReferenceData(UpdatableInjectionContext injectionContext) {
        ReferenceDataService referenceDataService = new ReferenceDataService()
            .withCustomTransformer(ReferenceDataWithCode.class, ReferenceDataWithCode.TO_DTO_TRANSFORMER)
            .registerClasses(
                // ReferenceData implementation classes go here
            );
        injectionContext.inject(referenceDataService).as(ReferenceDataService.class);
    }

    @SuppressWarnings("unchecked")
    private static void injectAsSelf(UpdatableInjectionContext injectionContext, Class... classes) {
        for (Class clazz : classes) {
            injectionContext.inject(clazz).as(clazz);
        }
    }
}
