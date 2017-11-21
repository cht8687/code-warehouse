package threewks.framework.usermanager;

import com.google.api.client.extensions.appengine.datastore.AppEngineDataStoreFactory;
import com.google.api.client.extensions.appengine.http.UrlFetchTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.util.store.DataStoreFactory;
import com.googlecode.objectify.ObjectifyService;
import com.threewks.thundr.configuration.Environment;
import com.threewks.thundr.gmail.GmailMailer;
import com.threewks.thundr.gmail.GmailModule;
import com.threewks.thundr.handlebars.HandlebarsModule;
import com.threewks.thundr.injection.BaseModule;
import com.threewks.thundr.injection.UpdatableInjectionContext;
import com.threewks.thundr.mail.BaseMailer;
import com.threewks.thundr.mail.Mailer;
import com.threewks.thundr.module.DependencyRegistry;
import com.threewks.thundr.route.Router;
import com.threewks.thundr.search.gae.SearchConfig;
import com.threewks.thundr.user.gae.UserGae;
import com.threewks.thundr.user.gae.UserGaeModule;
import threewks.framework.usermanager.controller.UserRoutes;
import threewks.framework.usermanager.mail.LoggingMailer;
import threewks.framework.usermanager.model.AppUser;
import threewks.framework.usermanager.model.LoginIdentifier;
import threewks.framework.usermanager.model.MagicLink;
import threewks.framework.usermanager.model.UserInviteLink;
import threewks.framework.usermanager.repository.AppUserRepository;
import threewks.framework.usermanager.repository.LoginIdentifierRepository;
import threewks.framework.usermanager.repository.MagicLinkRepository;
import threewks.framework.usermanager.repository.UserInviteLinkRepository;
import threewks.framework.usermanager.service.AppUserService;
import threewks.framework.usermanager.service.LoginIdentifierService;
import threewks.framework.usermanager.service.MagicLinkService;
import threewks.framework.usermanager.service.UserInviteService;

import java.util.Arrays;
import java.util.List;

public class UserManagerModule extends BaseModule {

    public static List<Class<?>> ENTITIES = Arrays.asList(
        LoginIdentifier.class,
        UserInviteLink.class,
        MagicLink.class,
        AppUser.class
    );

    @Override
    public void requires(DependencyRegistry dependencyRegistry) {
        super.requires(dependencyRegistry);
        dependencyRegistry.addDependency(UserGaeModule.class);
        dependencyRegistry.addDependency(GmailModule.class);
        dependencyRegistry.addDependency(HandlebarsModule.class);
    }

    @Override
    public void initialise(UpdatableInjectionContext injectionContext) {
        super.initialise(injectionContext);

        // for gmail API as the setup stores the creds established at setup time in DS
        injectionContext.inject(AppEngineDataStoreFactory.getDefaultInstance()).as(DataStoreFactory.class);
        injectionContext.inject(UrlFetchTransport.getDefaultInstance()).as(HttpTransport.class);

        Class<? extends Mailer> mailerClass = Environment.is(Environment.DEV) ? LoggingMailer.class : GmailMailer.class;
        injectionContext.inject(mailerClass).as(Mailer.class);
    }

    @Override
    public void configure(UpdatableInjectionContext injectionContext) {
        super.configure(injectionContext);

        configureObjectify();
        configureRepositories(injectionContext);
        configureServices(injectionContext);
    }

    @Override
    public void start(UpdatableInjectionContext injectionContext) {
        super.start(injectionContext);

        Router router = injectionContext.get(Router.class);
        UserRoutes.addRoutes(router);
    }

    private void configureServices(UpdatableInjectionContext injectionContext) {
        injectionContext.inject(LoginIdentifierService.class).as(LoginIdentifierService.class);
        injectionContext.inject(UserInviteService.class).as(UserInviteService.class);
        injectionContext.inject(MagicLinkService.class).as(MagicLinkService.class);
        injectionContext.inject(AppUserService.class).as(AppUserService.class);
    }

    private void configureRepositories(UpdatableInjectionContext injectionContext) {
        configureAppUserRepository(injectionContext);
        injectionContext.inject(LoginIdentifierRepository.class).as(LoginIdentifierRepository.class);
        injectionContext.inject(UserInviteLinkRepository.class).as(UserInviteLinkRepository.class);
        injectionContext.inject(MagicLinkRepository.class).as(MagicLinkRepository.class);
    }

    /**
     * To get around limitations in subclasses, indexed queries and search queries we do some generics trickery
     * to inject a repository with generics as {@link AppUser} when it is actually backed by {@link UserGae}. All
     * entities will be stored as the subclass however so this will be safe. See {@link AppUserRepository} for more
     * info.
     */
    @SuppressWarnings("unchecked")
    private void configureAppUserRepository(UpdatableInjectionContext injectionContext) {
        SearchConfig searchConfig = injectionContext.get(SearchConfig.class);
        AppUserRepository<AppUser> appUserRepository = new AppUserRepository(UserGae.class, searchConfig);

        injectionContext.inject(appUserRepository).as(AppUserRepository.class);
    }

    private void configureObjectify() {
        for (Class<?> entityClass : ENTITIES) {
            ObjectifyService.register(entityClass);
        }
    }
}
