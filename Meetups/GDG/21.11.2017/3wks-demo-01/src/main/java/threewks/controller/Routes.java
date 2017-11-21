package threewks.controller;

import com.threewks.thundr.route.HttpMethod;
import com.threewks.thundr.route.Router;
import threewks.framework.ref.ReferenceDataController;

public class Routes {
    public static void addRoutes(Router router) {
        system(router);
        referenceData(router);

        // Keep these at the end
        notFoundRoutes(router, HttpMethod.GET,"/system/**", "/api/**", "/task/**", "/cron/**");
        router.get("/**", IndexController.class, "index");
    }

    private static void system(Router router) {
        router.get("/system/bootstrap", BootstrapController.class, "bootstrap");
    }

    private static void referenceData(Router router) {
        router.get("/api/reference-data", ReferenceDataController.class, "getReferenceData");
    }


    private static void notFoundRoutes(Router router, HttpMethod method, String... routes) {
        for (String route : routes) {
            router.add(method, route, IndexController.class, "notFound", null);
        }
    }

}
