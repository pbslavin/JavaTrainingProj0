package dev.slavin.controllers;

import dev.slavin.models.User;
import dev.slavin.services.UserService;
import io.javalin.http.Context;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

 class UserControllerTest {
    @InjectMocks
    private UserController UserController;

    @Mock
    private UserService service;

    @BeforeEach
     void initMocks() {
       MockitoAnnotations.initMocks(this);
    }

    @Test
     public void getAllUsersCallsServiceMethod() {
        Context context = mock(Context.class);

        List<User> users = new ArrayList<>();
        users.add(new User("user1","password", 1));
        users.add(new User("user2","password", 2));

        when(service.getAllUsers()).thenReturn(users);
        UserController.handleGetAllUsers(context);
        verify(context).json(users);
    }
}
