package dev.slavin.controllers;

import dev.slavin.models.Composition;
import dev.slavin.services.CompositionService;
import dev.slavin.util.ErrorLogger;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompositionController {
    private static final String INVALID_COMPOSITION = "That is not a valid composition.";
    private static final String INVALID_COMPOSITION_ID = " is not a valid composition id.";
    private static final String INVALID_COMPOSER_ID = " is not a valid composer id.";

    private final Logger logger = LoggerFactory.getLogger(CompositionController.class);
    private final ErrorLogger errorLogger = new ErrorLogger(CompositionController.class, logger);

    private CompositionService compositionService = new CompositionService();

    private Composition composition;

    public void handleGetAllCompositions(Context ctx) {
        ctx.json(compositionService.getAllCompositions());
    }

    public void handleGetCompositionById(Context ctx) {
        String pathParamId = "";
        try {
            pathParamId = ctx.pathParam("id");
            ctx.json(compositionService.getComposition(Integer.parseInt(pathParamId)));
        } catch (NullPointerException e) {
            errorLogger.logError(e);
            throw new BadRequestResponse(pathParamId + CompositionController.INVALID_COMPOSITION_ID);
        }
    }

    public void handleGetCompositionsByComposer(Context ctx) {
        String pathParamComposerId = "";
        try {
            pathParamComposerId = ctx.pathParam("id");
            int id = Integer.parseInt(pathParamComposerId);
            ctx.json(compositionService.getCompositionsByComposer(id));
        } catch (Exception e) {
            errorLogger.logError(e);
            throw new BadRequestResponse(pathParamComposerId + CompositionController.INVALID_COMPOSER_ID);
        }
    }

    public void handleAddNewComposition(Context ctx) {
        try {
            composition = ctx.bodyAsClass(Composition.class);
            ctx.json(compositionService.addComposition(composition));
            ctx.status(201);
        } catch (Exception e) {
            errorLogger.logError(e);
            throw new BadRequestResponse(CompositionController.INVALID_COMPOSITION);
        }
    }

    public void handleUpdateComposition(Context ctx) {
        try {
            composition = ctx.bodyAsClass(Composition.class);
            ctx.json(compositionService.updateComposition(composition));
            ctx.status(201);
        } catch (Exception e) {
            errorLogger.logError(e);
            throw new BadRequestResponse(CompositionController.INVALID_COMPOSITION);
        }
    }

    public void handleDeleteComposition(Context ctx) {
        String pathParamId = "";
        try {
            pathParamId = ctx.pathParam("id");
            int id = Integer.parseInt(pathParamId);
            compositionService.deleteComposition(id);
            ctx.status(204);
        } catch (Exception e) {
            errorLogger.logError(e);
            throw new BadRequestResponse(pathParamId + CompositionController.INVALID_COMPOSITION_ID);
        }
    }
}
