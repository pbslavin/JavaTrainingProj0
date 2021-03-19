package dev.slavin.services;

import dev.slavin.data.CompositionDAO;
import dev.slavin.data.CompositionDAOImpl;
import dev.slavin.models.Composition;

import java.util.List;

public class CompositionService {

    private CompositionDAO compositionDAO = new CompositionDAOImpl();

    public List<Composition> getAllCompositions() {
        return compositionDAO.getAllCompositions();
    }

    public Composition getComposition(int id) {
        return compositionDAO.getCompositionById(id);
    }
    
    public List<Composition> getCompositionsByComposer(int composerId) {
        return compositionDAO.getCompositionsByComposer(composerId);
    }

    public Composition addComposition(Composition composition) {
        compositionDAO.addNewComposition(composition);
        return composition;
    }

    public Composition updateComposition(Composition composition) {
        Composition oldComposition = getComposition(composition.getId());
        compositionDAO.updateComposition(composition);
        return oldComposition;
    }

    public void deleteComposition(int id) {
        compositionDAO.deleteComposition(id);
    }
}

