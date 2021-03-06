package dev.slavin.controllers;

import dev.slavin.models.Composition;
import dev.slavin.models.Genre;
import dev.slavin.services.CompositionService;
import io.javalin.http.Context;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class CompositionControllerTest {
    @InjectMocks
    private CompositionController CompositionController;

    @Mock
    private CompositionService service;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllCompositionsCallsServiceMethod() {
        Context context = mock(Context.class);

        List<Composition> compositions = new ArrayList<>();
        compositions.add(new Composition("Appalachian Spring",2, 1930, Genre.BALLET,false));
        compositions.add(new Composition("Le grand Macabre",3, 1978, Genre.OPERA, true));

        when(service.getAllCompositions()).thenReturn(compositions);
        CompositionController.handleGetAllCompositions(context);
        verify(context).json(compositions);
    }
}
