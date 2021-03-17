package dev.slavin.services;

import dev.slavin.models.Composer;
import dev.slavin.data.*;
import dev.slavin.models.Composer;

import java.util.List;

public class ComposerService {

    private ComposerDAOImpl composerDAO = new ComposerDAOImpl();

    public List<Composer> getAllComposers() {
        return composerDAO.getAllComposers();
    }

    public Composer getComposer(int id) {
        return composerDAO.getComposerById(id);
    }

    public Composer addComposer(Composer composer) {
        composerDAO.addNewComposer(composer);
        return composer;
    }

    public Composer updateComposer(Composer composer) {
        Composer oldComposer = getComposer(composer.getId());
        composerDAO.updateComposer(composer);
        return oldComposer;
    }
    
    public void deleteComposer(int id) {
        composerDAO.deleteComposer(id);
    }
}
