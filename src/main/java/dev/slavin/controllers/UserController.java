package dev.slavin.controllers;

import dev.slavin.models.User;
import dev.slavin.services.UserService;

import dev.slavin.util.ErrorLogger;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.http.UnauthorizedResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.NoSuchElementException;

public class UserController {
    private static final String AUTH_HEADER = "Authorization";
    private static final String INVALID_USER = "That is not a valid user.";
    private static final String INVALID_USER_ID = " is not a valid user id.";
    private static final String INVALID_USERNAME = " is not a valid username.";

    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final ErrorLogger errorLogger = new ErrorLogger(UserController.class, logger);

    private UserService userService = new UserService();

    public void logIn(Context ctx){
        String userName = ctx.formParam("userName");  //(Content-Type: application/x-www-form-urlencoded)
        String password = ctx.formParam("password");
        if (userName != null && userService.getUserByUserName(userName) != null) {
            try {
                User user = userService.getUserByUserName(userName);
                if (password != null && password.equals(user.getPassword())) {
                    if (user.getAuthLevel() == 1) {
                        ctx.header(AUTH_HEADER, "general-auth-token");
                        return;
                    }
                    if (user.getAuthLevel() == 2) {
                        ctx.header(AUTH_HEADER, "admin-auth-token");
                        return;
                    }
                    throw new NoSuchElementException("There was an unexpected error logging in.");
                }
            } catch (Exception e) {
                logger.warn("Failed login attempt");
                throw new UnauthorizedResponse("That username and password combination is incorrect.");
            }

        }
    }

    public void adminAuth(Context ctx) {
        String authHeader = ctx.header(AUTH_HEADER);
        if (authHeader == null || !authHeader.equals("admin-auth-token")) {
            throw new UnauthorizedResponse("You are unauthorized.");
        }
    }

    public void handleGetAllUsers(Context ctx) {
        ctx.json(userService.getAllUsers());
    }

    public void handleAddUser(Context ctx) {
        User user = ctx.bodyAsClass(User.class);
        userService.addUser(user);
        ctx.status(201);
    }

    public void handleGetUserById(Context ctx) {
        String pathParamId = ctx.pathParam("id");
        try {
           int id = Integer.parseInt(pathParamId);
           ctx.json(userService.getUser(id));
        } catch (Exception e) {
            errorLogger.logError(e);
            throw new BadRequestResponse("\"" + pathParamId + UserController.INVALID_USER_ID);
        }
    }

    public void handleGetUserByUserName(Context ctx) {
        String userName = ctx.pathParam("userName");
        try {
            User user = userService.getUserByUserName(userName);
            ctx.json(user);
        } catch (Exception e) {
            errorLogger.logError(e);
            throw new BadRequestResponse("\"" + userName + UserController.INVALID_USERNAME);
        }
    }

    public void handleUpdateUser(Context ctx) {
        User user = ctx.bodyAsClass(User.class);
        try {
            userService.updateUser(user);
            ctx.status(201);
        } catch (Exception e) {
            errorLogger.logError(e);
            throw new BadRequestResponse(UserController.INVALID_USER);
        }
    }

    public void handleDeleteUser(Context ctx) {
        String pathParamId = "";
        try {
            pathParamId = ctx.pathParam("id");
            int id = Integer.parseInt(pathParamId);
           userService.deleteUser(id);
           ctx.status(204);
        } catch (Exception e) {
            errorLogger.logError(e);
            throw new BadRequestResponse("\"" + pathParamId + UserController.INVALID_USER_ID);
        }
    }
}
