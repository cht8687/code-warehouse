package threewks.framework.usermanager.context;

public final class SecurityContextHolder {
    private static final ThreadLocal<SecurityContext> contextHolder = new ThreadLocal<>();

    private SecurityContextHolder() {
        // static only
    }

    public static void clear() {
        contextHolder.remove();
    }

    public static SecurityContext get() {
        SecurityContext ctx = contextHolder.get();

        if (ctx == null) {
            ctx = createEmptyContext();
            contextHolder.set(ctx);
        }

        return ctx;
    }

    private static SecurityContext createEmptyContext() {
        return new SecurityContextImpl();
    }

}
