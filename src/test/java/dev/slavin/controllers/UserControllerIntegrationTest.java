package dev.slavin.controllers;

import dev.slavin.JavalinApp;
import dev.slavin.models.User;
import kong.unirest.GenericType;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

 class UserControllerIntegrationTest {
    private static JavalinApp app = new JavalinApp();

    @BeforeAll
     static void startService(){
        app.start(7000);
    }

    @AfterAll
     static void stopService(){
        app.stop();
    }

    @Test
     void getAllUsersProhibitsUnauthorized() {
        HttpResponse<String> response = Unirest.get("http://localhost:7000/users").asString();
        assertAll(
                () -> assertEquals( 401, response.getStatus()),
                () -> assertEquals( "You are unauthorized.", response.getBody()));
    }

    @Test
     void getAllPermitsAuthorized() {
        HttpResponse<List<User>> response = Unirest.get("http://localhost:7000/users")
                .header("Authorization", "admin-auth-token")
                .asObject(new GenericType<List<User>>() {});
        assertAll(
                () -> assertEquals(200, response.getStatus()),
                () -> assertTrue(response.getBody().size() > 0)
        );
    }

    @Test
     void getUserProhibitsUnauthorized() {
        HttpResponse<String> response = Unirest.get("http://localhost:7000/users")
                .asString();
        assertAll(
                () -> assertEquals(401, response.getStatus()),
                () -> assertEquals("You are unauthorized.", response.getBody())
        );
    }

    @Test
     void getUserPermitsAuthorized() {
        HttpResponse<User> response = Unirest.get("http://localhost:7000/users/1")
                .header("Authorization", "admin-auth-token")
                .asObject(new GenericType<User>() {});
        assertAll(
                () -> assertEquals(200, response.getStatus()),
                () -> assertNotNull(response.getBody())
        );
    }
}
