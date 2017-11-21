package threewks.framework.usermanager.context;

import com.threewks.thundr.request.Request;
import com.threewks.thundr.request.Response;
import com.threewks.thundr.session.CookieSessionStore;
import com.threewks.thundr.session.Session;
import com.threewks.thundr.session.SessionService;
import com.threewks.thundr.session.SessionStore;

public class SessionHelper {
    private final SessionService<?> sessionService;

    public SessionHelper(SessionService<?> sessionService) {
        this.sessionService = sessionService;
    }

    public Session getCurrentSession(Request req, Response resp) {
        SessionStore sessionStore = getSessionStoreForRequest(req, resp);
        return sessionService.getCurrentSession(sessionStore);
    }

    /**
     * This extension point allows overriding implementations to use a different {@link SessionStore} that the default.
     *
     * @param req
     * @param resp
     * @return
     */
    protected SessionStore getSessionStoreForRequest(Request req, Response resp) {
        return new CookieSessionStore(req, resp);
    }

}
