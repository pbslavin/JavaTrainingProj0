package dev.slavin;

import dev.slavin.controllers.*;
import dev.slavin.models.*;
import io.javalin.Javalin;
import static io.javalin.apibuilder.ApiBuilder.*;

public class Driver {
    public static void main(String[] args) {

        JavalinApp javalinApp = new JavalinApp();
        javalinApp.start(7000);
    }
}

