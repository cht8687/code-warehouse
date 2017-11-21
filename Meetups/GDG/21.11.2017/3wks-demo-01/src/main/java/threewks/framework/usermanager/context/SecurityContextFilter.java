package threewks.framework.usermanager.context;

import com.threewks.thundr.logger.Logger;
import com.threewks.thundr.request.Request;
import com.threewks.thundr.request.Response;
import com.threewks.thundr.route.controller.BaseFilter;
import com.threewks.thundr.session.Session;
import com.threewks.thundr.user.User;

/**
 * Set SecurityContext (ThreadLocal) on all requests based on the "user" request attribute.
 */
public class SecurityContextFilter extends BaseFilter {
    private final SessionHelper sessionHelper;

    public SecurityContextFilter(SessionHelper sessionHelper) {
        this.sessionHelper = sessionHelper;
    }

    @Override
    public <T> T before(Request req, Response resp) {
        Session session = sessionHelper.getCurrentSession(req, resp);
        User user = session == null ? null : session.getUser();
        SecurityContextHolder.get().setUser(user);
        Logger.debug("[%s] Setting user on SecurityContext to: %s",
            Thread.currentThread().getName(),
            user == null ? "<null>" : user.getUsername());
        return null;
    }

    @Override
    public <T> T after(Object view, Request req, Response resp) {
        SecurityContextHolder.clear();
        return null;
    }

}
