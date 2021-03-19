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
                userController.isLoggedIn(ctx); // checks for any Authorization token
            }
        });
        before(ctx -> {
            if (!ctx.method().equals("GET") && !ctx.path().equals("/login")) {
                userController.adminAuth(ctx); // checks for admin Authorization token
            }
        });
        path("login", () -> post(userController::logIn));
        before("users", userController::adminAuth);
        path("users", () -> {
            get(userController::handleGetAllUsers);
            post(userController::handleAddUser);
            before("username/:userName", userController::adminAuth);
            path("username/:userName", () -> get(userController::handleGetUserByUserName));
            before(":id", userController::adminAuth);
            path(":id", () -> {
                get(userController::handleGetUserById);
                put(userController::handleUpdateUser);
                delete(userController::handleDeleteUser);
            });
        });
        path("composers", () -> {
            get(composerController::handleGetAllComposers);
            post(composerController::handleAddNewComposer);
            path(":id", () -> {
                get(composerController::handleGetComposerById);
                put(composerController::handleUpdateComposer);
                delete(composerController::handleDeleteComposer);
                path("compositions", () -> get(compositionController::handleGetCompositionsByComposer));
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
//            path("composer/:composerId", () -> get(compositionController::handleGetCompositionsByComposer));
        });
    });

    public void start(int port) {
        this.app.start(port);
    }

    public void stop() {
        this.app.stop();
    }
}
