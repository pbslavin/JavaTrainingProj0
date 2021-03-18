package dev.slavin.controllers;

import dev.slavin.models.Composer;
import dev.slavin.services.ComposerService;

import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComposerController {

    private ComposerService composerService = new ComposerService();
    private static final String invalidComposer = "That is not a valid composer.";
    private static final String invalidComposerId = "is not a valid composer id.";

    public void handleGetAllComposers (Context ctx) {
        ctx.json(composerService.getAllComposers());
    }

    public void handleGetComposerById(Context ctx) {
        String pathParamId = "";
        try {
            pathParamId = ctx.pathParam("id");
            int id = Integer.parseInt(pathParamId);
            ctx.json(composerService.getComposer(id));
        } catch (Exception e) {
            throw new BadRequestResponse("\"" + pathParamId + ComposerController.invalidComposerId);
        }
    }

    public void handleAddNewComposer(Context ctx) {
        Composer composer = new Composer();
        try {
            composer = ctx.bodyAsClass(Composer.class);
            composerService.addComposer(composer);
            ctx.status(201);
        } catch (Exception e) {
            throw new BadRequestResponse(ComposerController.invalidComposer);
        }
    }

    public void handleUpdateComposer(Context ctx) {
        Composer composer = new Composer();
        try {
            composer = ctx.bodyAsClass(Composer.class);
            composerService.updateComposer(composer);
        } catch (Exception e) {
            throw new BadRequestResponse(ComposerController.invalidComposer);
        }
    }

    public void handleDeleteComposer(Context ctx) {
        String pathParamId = "";
        try {
            pathParamId = ctx.pathParam("id");
            int id = Integer.parseInt(pathParamId);
            composerService.deleteComposer(id);
        } catch (Exception e) {
            throw new BadRequestResponse(ComposerController.invalidComposer);
        }
    }
}
