package dev.slavin.controllers;

import dev.slavin.models.User;
import dev.slavin.services.UserService;

import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import io.javalin.http.UnauthorizedResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);
    private UserService userService = new UserService();

    public void logIn(Context ctx){
        String userName = ctx.formParam("userName");  //(Content-Type: application/x-www-form-urlencoded)
        String password = ctx.formParam("password");
//        if(user!=null && user.equals("crehm")){
//            if(pass!=null && pass.equals("supersecret")){
//                logger.info("successful login");
//                // send back token
//                ctx.header("Authorization", "admin-auth-token");
//                ctx.status(200);
//                return;
//            }
//            throw new UnauthorizedResponse("Incorrect password");

//        }
        // invoke service method checking for user
        // if I get back a user send a token with user ID and user role
        // else throw Unauthorized
//        throw new UnauthorizedResponse("Username not recognized");
        if (userName != null && userService.getUserByUserName(userName) != null) {
            User user = userService.getUserByUserName(userName);
            if (password != null && password.equals(user.getPassword())) {
                switch(user.getAuthLevel()) {
                    case 1:
                        ctx.header("Authorization", "general-auth-token");
                        break;
                    case 2:
                        ctx.header("Authorization", "admin-auth-token");
                }
            }
        }
    }

    public void adminAuth(Context ctx) {
        String authHeader = ctx.header("Authorization");

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
            throw new BadRequestResponse("\"" + pathParamId + "\" is not a valid user id.");
        }
    }

    public void handleGetUserByUserName(Context ctx) {
        String userName = ctx.pathParam("userName");
        try {
            User user = userService.getUserByUserName(userName);
            ctx.json(user);
        } catch (Exception e) {
            throw new BadRequestResponse("\"" + userName + "\" is not a valid username.");
        }
    }

    public void handleUpdateUser(Context ctx) {
        User user = ctx.bodyAsClass(User.class);
        userService.updateUser(user);
        ctx.status(201);
    }

    public void handleDeleteUser(Context ctx) {
        String pathParamId = "";
        try {
            pathParamId = ctx.pathParam("id");
            int id = Integer.parseInt(pathParamId);
           userService.deleteUser(id);
        } catch (Exception e) {
            throw new BadRequestResponse("\"" + pathParamId + "\" is not a valid composer id.");
        }
    }
}
