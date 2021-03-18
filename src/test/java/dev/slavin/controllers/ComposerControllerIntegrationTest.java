package dev.slavin.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.slavin.JavalinApp;
import dev.slavin.models.Composer;
import dev.slavin.models.User;
import kong.unirest.GenericType;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.GenericType;
import dev.slavin.JavalinApp;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ComposerControllerIntegrationTest {
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
        HttpResponse<List<Composer>> response = Unirest.get("http://localhost:7000/composers")
                .asObject(new GenericType<List<Composer>>() {});
        assertAll(
                () -> assertEquals(200, response.getStatus()),
                () -> assertTrue(response.getBody().size() > 0)
        );
    }

    @Test
    void getPermitsAnyAndFetchesComposer() {
        HttpResponse<Composer> response = Unirest.get("http://localhost:7000/composers/1")
                .asObject(new GenericType<Composer>() {});
        assertAll(
                () -> assertEquals(200, response.getStatus()),
                () -> assertNotNull(response.getBody())
        );
    }

    @Test
    void postProhibitsUnauthorized() {
        HttpResponse<String> response = Unirest.post("http://localhost:7000/composers")
                .asString();
        assertAll(
                () -> assertEquals( 401, response.getStatus()),
                () -> assertEquals( "You are unauthorized.", response.getBody()));
    }

    @Test
    void postPermitsAuthorized() {
        HttpResponse<String> response = Unirest.post("http://localhost:7000/composers")
                .header("Authorization", "admin-auth-token")
                .asString();
        assertAll(
                () -> assertEquals( 400, response.getStatus()),
                () -> assertEquals( "That is not a valid composer.", response.getBody()));
    }

    @Test
    void putProhibitsUnauthorized() {
        HttpResponse<String> response = Unirest.put("http://localhost:7000/composers")
                .asString();
        assertAll(
                () -> assertEquals( 401, response.getStatus()),
                () -> assertEquals( "You are unauthorized.", response.getBody()));
    }

    @Test
    void putPermitsAuthorized() {
        HttpResponse<String> response = Unirest.put("http://localhost:7000/composers/1")
                .header("Authorization", "admin-auth-token")
                .asString();
        assertAll(
                () -> assertEquals( 400, response.getStatus()),
                () -> assertEquals( "That is not a valid composer.", response.getBody()));
    }

    @Test
    void deleteProhibitsUnauthorized() {
        HttpResponse<String> response = Unirest.delete("http://localhost:7000/composers/hello")
                .asString();
        assertAll(
                () -> assertEquals( 401, response.getStatus()),
                () -> assertEquals( "You are unauthorized.", response.getBody()));
    }

    @Test
    void deletePermitsAuthorized() {
        HttpResponse<String> response = Unirest.delete("http://localhost:7000/composers/hello")
                .header("Authorization", "admin-auth-token")
                .asString();
        assertAll(
                () -> assertEquals( 400, response.getStatus()),
                () -> assertEquals( "That is not a valid composer.", response.getBody()));
    }
}
