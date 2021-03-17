package dev.slavin.services;

import dev.slavin.data.UserDAOImpl;
import dev.slavin.models.User;

import java.util.List;
import java.util.NoSuchElementException;

public class UserService {

    private UserDAOImpl userDAO = new UserDAOImpl();

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public User getUserByUserName(String userName) {
        return userDAO.getUserByUserName(userName);
    }

    public User getUser(int id) {
        return userDAO.getUserById(id);
    }

    public User addUser(User user) {
        userDAO.addNewUser(user);
        return user;
    }

    public User updateUser(User user) {
        userDAO.updateUser(user);
        return user;
    }

    public void deleteUser(int id) {
        userDAO.deleteUser(id);
    }

    public User logIn(String userName, String password) {
        User user = userDAO.logIn(userName, password);
        return user;
    }
}
