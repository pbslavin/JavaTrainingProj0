package dev.slavin;

import dev.slavin.controllers.ComposerController;
import dev.slavin.controllers.CompositionController;
import dev.slavin.controllers.UserController;
import io.javalin.Javalin;

import static io.javalin.apibuilder.ApiBuilder.*;

public class JavalinApp {

    CompositionController compositionController = new CompositionController();
    ComposerController composerController = new ComposerController();
    UserController userController = new UserController();

    Javalin app = Javalin.create().routes(() -> {
        before(ctx -> {
            if (!ctx.path().equals("/login")) {
                userController.isLoggedIn(ctx);
            }
        });
        before(ctx -> {
            if (!ctx.method().equals("GET") && !ctx.path().equals("/login")) {
                userController.adminAuth(ctx);
            }
        });
        before("users", userController::adminAuth);
        path("login", () -> post(userController::logIn));
        path("users", () -> {
            get(userController::handleGetAllUsers);
            post(userController::handleAddUser);
            path("username/:userName", () -> get(userController::handleGetUserByUserName));
            path(":id", () -> {
                get(userController::handleGetUserById);
                put(userController::handleUpdateUser);
                delete(userController::handleDeleteUser);
            });
        });
//        before("composers", ctx -> {
//            if (!ctx.method().equals("GET")) {
//                userController.adminAuth(ctx);
//            }
//        });
        path("composers", () -> {
            get(composerController::handleGetAllComposers);
            post(composerController::handleAddNewComposer);
            path(":id", () -> {
                get(composerController::handleGetComposerById);
                put(composerController::handleUpdateComposer);
                delete(composerController::handleDeleteComposer);
            });
        });
        path("compositions", () -> {
            get(compositionController::handleGetAllCompositions);
            post(compositionController::handleAddNewComposition);
            path(":id", () -> {
                get(compositionController::handleGetCompositionById);
                put(compositionController::handleUpdateComposition);
                delete(compositionController::handleDeleteComposition);
            });
            path("composer/:composerId", () -> get(compositionController::handleGetCompositionsByComposer));
        });
    });

    public void start(int port) {
        this.app.start(port);
    }

    public void stop() {
        this.app.stop();
    }
}
