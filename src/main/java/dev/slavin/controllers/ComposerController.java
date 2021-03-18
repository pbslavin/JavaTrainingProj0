package dev.slavin.controllers;

import dev.slavin.models.Composer;
import dev.slavin.services.ComposerService;

import dev.slavin.util.ErrorLogger;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComposerController {


    private Logger logger = LoggerFactory.getLogger(ComposerController.class);
    private ErrorLogger errorLogger = new ErrorLogger(ComposerController.class, logger);
    private ComposerService composerService = new ComposerService();

    private static final String INVALID_COMPOSER = "That is not a valid composer.";
    private static final String INVALID_COMPOSER_ID = " is not a valid composer id.";

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
            errorLogger.logError(e);
            throw new BadRequestResponse("\"" + pathParamId + ComposerController.INVALID_COMPOSER_ID);
        }
    }

    public void handleAddNewComposer(Context ctx) {
        Composer composer = new Composer();
        try {
            composer = ctx.bodyAsClass(Composer.class);
            composerService.addComposer(composer);
            ctx.status(201);
        } catch (Exception e) {
            errorLogger.logError(e);
            throw new BadRequestResponse(ComposerController.INVALID_COMPOSER);
        }
    }

    public void handleUpdateComposer(Context ctx) {
        Composer composer = new Composer();
        try {
            composer = ctx.bodyAsClass(Composer.class);
            composerService.updateComposer(composer);
            ctx.status(204);
        } catch (Exception e) {
            errorLogger.logError(e);
            throw new BadRequestResponse(ComposerController.INVALID_COMPOSER);
        }
    }

    public void handleDeleteComposer(Context ctx) {
        String pathParamId = "";
        try {
            pathParamId = ctx.pathParam("id");
            int id = Integer.parseInt(pathParamId);
            composerService.deleteComposer(id);
            ctx.status(204);
        } catch (Exception e) {
            errorLogger.logError(e);
            throw new BadRequestResponse(ComposerController.INVALID_COMPOSER_ID);
        }
    }
}
