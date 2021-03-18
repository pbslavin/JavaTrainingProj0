package dev.slavin.controllers;

import dev.slavin.JavalinApp;
import dev.slavin.models.Composition;
import kong.unirest.GenericType;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CompositionControllerIntegrationTest {
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
    void getAllPermitsAnyAndFetchesList() {
        HttpResponse<List<Composition>> response = Unirest.get("http://localhost:7000/compositions")
                .asObject(new GenericType<List<Composition>>() {});
        assertAll(
                () -> assertEquals(200, response.getStatus()),
                () -> assertTrue(response.getBody().size() > 0)
        );
    }

    @Test
    void getPermitsAnyAndFetchesComposition() {
        HttpResponse<Composition> response = Unirest.get("http://localhost:7000/compositions/1")
                .asObject(new GenericType<Composition>() {});
        assertAll(
                () -> assertEquals(200, response.getStatus()),
                () -> assertNotNull(response.getBody())
        );
    }

    @Test
    void postProhibitsUnauthorized() {
        HttpResponse<String> response = Unirest.post("http://localhost:7000/compositions")
                .asString();
        assertAll(
                () -> assertEquals( 401, response.getStatus()),
                () -> assertEquals( "You are unauthorized.", response.getBody()));
    }

    @Test
    void postPermitsAuthorized() {
        HttpResponse<String> response = Unirest.post("http://localhost:7000/compositions")
                .header("Authorization", "admin-auth-token")
                .asString();
        assertAll(
                () -> assertEquals( 400, response.getStatus()),
                () -> assertEquals( "That is not a valid composition.", response.getBody()));
    }

    @Test
    void putProhibitsUnauthorized() {
        HttpResponse<String> response = Unirest.put("http://localhost:7000/compositions")
                .asString();
        assertAll(
                () -> assertEquals( 401, response.getStatus()),
                () -> assertEquals( "You are unauthorized.", response.getBody()));
    }

    @Test
    void putPermitsAuthorized() {
        HttpResponse<String> response = Unirest.put("http://localhost:7000/compositions/1")
                .header("Authorization", "admin-auth-token")
                .asString();
        assertAll(
                () -> assertEquals( 400, response.getStatus()),
                () -> assertEquals( "That is not a valid composition.", response.getBody()));
    }

    @Test
    void deleteProhibitsUnauthorized() {
        HttpResponse<String> response = Unirest.delete("http://localhost:7000/compositions/hello")
                .asString();
        assertAll(
                () -> assertEquals( 401, response.getStatus()),
                () -> assertEquals( "You are unauthorized.", response.getBody()));
    }

    @Test
    void deletePermitsAuthorized() {
        HttpResponse<String> response = Unirest.delete("http://localhost:7000/compositions/hello")
                .header("Authorization", "admin-auth-token")
                .asString();
        assertAll(
                () -> assertEquals( 400, response.getStatus()),
                () -> assertEquals( "That is not a valid composition id.", response.getBody()));
    }
}
