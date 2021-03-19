package dev.slavin.services;

import dev.slavin.data.UserDAO;
import dev.slavin.data.UserDAOImpl;
import dev.slavin.models.User;

import java.util.List;

public class UserService {

    private final UserDAO userDAO = new UserDAOImpl();

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
        return userDAO.logIn(userName, password);
    }
}
