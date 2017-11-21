package threewks.framework.usermanager.controller;

import com.threewks.thundr.route.Router;

public class UserRoutes {

    public static void addRoutes(Router router) {
        router.post("/task/notify/invite-user", UserTaskQueueController.class, "sendUserInviteEmail", "task.notify.inviteUser");
        router.post("/task/notify/send-magic-link", UserTaskQueueController.class, "sendMagicLinkEmail", "task.notify.sendMagicLink");

        router.get("/login/{magicCode}", MagicLinkController.class, "login");

        router.post("/api/v1/users/login", UserController.class, "login");
        router.post("/api/v1/users/login/magic", MagicLinkController.class, "requestMagicLink");
        router.post("/api/v1/users/logout", UserController.class, "logout");
        router.get("/api/v1/users", UserController.class, "listAll");
        router.get("/api/v1/users/search", UserController.class, "search");
        router.get("/api/v1/users/me", UserController.class, "me");
        router.get("/api/v1/users/{username}", UserController.class, "get");
        router.put("/api/v1/users/{username}", UserController.class, "save");
        router.post("/api/v1/users/invite", UserController.class, "invite");
        router.post("/api/v1/users/invite/{code}", UserController.class, "redeemInvite");
    }
}
