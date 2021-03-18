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

class ComposerControllerTest {
    @InjectMocks
    private ComposerController composerController;

    @Mock
    private ComposerService service;

    @BeforeEach
    void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllCallsServiceMethod() {
        Context context = mock(Context.class);

        List<Composer> composers = new ArrayList<>();
        composers.add(new Composer("Aaron Copland",1900, 1990));
        composers.add(new Composer("Gyorgy Ligeti",1923, 2006));
        composers.add(new Composer("Johann Sebastian Bach",1685, 1750));

        when(service.getAllComposers()).thenReturn(composers);
        composerController.handleGetAllComposers(context);
        verify(context).json(composers);
    }
}
