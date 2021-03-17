package dev.slavin.controllers;

import dev.slavin.models.Composition;
import dev.slavin.services.CompositionService;

import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompositionController {

    private Logger logger = LoggerFactory.getLogger(CompositionController.class);
    private CompositionService compositionService = new CompositionService();

    public void handleGetAllCompositions(Context ctx) {
        ctx.json(compositionService.getAllCompositions());
    }

    public void handleGetCompositionById(Context ctx) {
        String pathParamId = "";
        try {
            pathParamId = ctx.pathParam("id");
            ctx.json(compositionService.getComposition(Integer.parseInt(pathParamId)));
        } catch (Exception e) {
            throw new BadRequestResponse("\"" + pathParamId + "\" is not a valid composition id.");
        }
    }

    public void handleGetCompositionsByComposer(Context ctx) {
        String pathParamComposerId = "";
        try {
            pathParamComposerId = ctx.pathParam("composerId");
            int id = Integer.parseInt(pathParamComposerId);
            ctx.json(compositionService.getCompositionsByComposer(id));
        } catch (Exception e) {
            throw new BadRequestResponse("\"" + pathParamComposerId + "\" is not a valid composition id.");
        }
    }

    public void handleAddNewComposition(Context ctx) {
        Composition composition = new Composition();
        try {
            composition = ctx.bodyAsClass(Composition.class);
            ctx.json(compositionService.addComposition(composition));
        } catch (Exception e) {
            throw new BadRequestResponse("That is not a valid composition.");
        }
    }

    public void handleUpdateComposition(Context ctx) {
        Composition composition = new Composition();
        try {
            composition = ctx.bodyAsClass(Composition.class);
            ctx.json(compositionService.updateComposition(composition));
        } catch (Exception e) {
            throw new BadRequestResponse("That is not a valid composition.");
        }
    }

    public void handleDeleteComposition(Context ctx) {
        String pathParamId = "";
        try {
            pathParamId = ctx.pathParam("composerId");
            int id = Integer.parseInt(pathParamId);
            compositionService.deleteComposition(id);
        } catch (Exception e) {
            throw new BadRequestResponse("\"" + pathParamId + "\" is not a valid composition id.");
        }
    }
}
