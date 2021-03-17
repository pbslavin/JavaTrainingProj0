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

public class ComposerControllerIntegrationTest {
    private static JavalinApp app = new JavalinApp();

    @BeforeAll
    public static void startService(){
        app.start(7000);
    }

    @AfterAll
    public static void stopService(){
        app.stop();
    }

    @Test
    public void testAddComposerUnauthorized() {
        HttpResponse<String> response = Unirest.post("http://localhost:7000/composers")
                .asString();
        assertAll(
                () -> assertEquals( 403, response.getStatus()),
                () -> assertEquals( "You are unauthorized.", response.getBody()));
    }

    @Test
    public void testAddComposerAuthorized() {
        HttpResponse<String> response = Unirest.post("http://localhost:7000/composers")
                .header("Authorization", "admin-auth-token")
                .asString();
        assertAll(
                () -> assertEquals( 400, response.getStatus()),
                () -> assertEquals( "That is not a valid composer.", response.getBody()));

    }

    @Test
    public void testUpdateComposerUnauthorized() {
        HttpResponse<String> response = Unirest.put("http://localhost:7000/composers/hello")
                .asString();
        assertAll(
                () -> assertEquals( 403, response.getStatus()),
                () -> assertEquals( "You are unauthorized.", response.getBody()));
    }

    @Test
    public void testUpdateComposerAuthorized() {
        HttpResponse<String> response = Unirest.put("http://localhost:7000/composers/hello")
                .header("Authorization", "admin-auth-token")
                .asString();
        assertAll(
                () -> assertEquals( 400, response.getStatus()),
                () -> assertEquals( "That is not a valid composer.", response.getBody()));

    }

    @Test
    public void testDeleteComposerUnauthorized() {
        HttpResponse<String> response = Unirest.delete("http://localhost:7000/composers/hello")
                .asString();
        assertAll(
                () -> assertEquals( 403, response.getStatus()),
                () -> assertEquals( "You are unauthorized.", response.getBody()));
    }

    @Test
    public void testDeleteComposerAuthorized() {
        HttpResponse<String> response = Unirest.delete("http://localhost:7000/composers/hello")
                .header("Authorization", "admin-auth-token")
                .asString();
        assertAll(
                () -> assertEquals( 400, response.getStatus()),
                () -> assertEquals( "That is not a valid composer.", response.getBody()));

    }

    @Test
    public void testGetComposerById() {
        HttpResponse<Composer> response = Unirest.get("http://localhost:7000/composers/1")
                .asObject(new GenericType<Composer>() {});
        assertAll(
                () -> assertEquals(200, response.getStatus()),
                () -> assertTrue(response.getBody() != null)
        );
    }

}
