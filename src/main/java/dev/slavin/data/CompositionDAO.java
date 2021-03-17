package dev.slavin.data;

import dev.slavin.models.Composition;

import java.util.List;

public interface CompositionDAO {

    public List<Composition> getAllCompositions();
    public Composition getCompositionById(int id);
    public List<Composition> getCompositionsByComposer(int composerId);
    public Composition addNewComposition(Composition composition);
    public void updateComposition(Composition composition);
    public void deleteComposition(int id);

}
