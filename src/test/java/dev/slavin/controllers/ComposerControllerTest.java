package dev.slavin.controllers;

import dev.slavin.models.Composer;
import dev.slavin.services.ComposerService;
import io.javalin.http.Context;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class ComposerControllerTest {
    @InjectMocks
    private ComposerController composerController;

    @Mock
    private ComposerService service;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllComposersCallsServiceMethod() {
        Context context = mock(Context.class);

        List<Composer> composers = new ArrayList<>();
        composers.add(new Composer(2,"Aaron Copland",1900, 1990));
        composers.add(new Composer(4,"Gyorgy Ligeti",1923, 2006));
        composers.add(new Composer(5,"Johann Sebastian Bach",1685, 1750));

        when(service.getAllComposers()).thenReturn(composers);
        composerController.handleGetAllComposers(context);
        verify(context).json(composers);
    }
}
