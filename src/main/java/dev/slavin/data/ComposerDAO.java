package dev.slavin.data;

import dev.slavin.models.Composer;

import java.util.List;

public interface ComposerDAO {

    public List<Composer> getAllComposers();
    public Composer getComposerById(int id);
    public Composer addNewComposer(Composer composer);
    public void updateComposer(Composer composer);
    public void deleteComposer(int id);

}
