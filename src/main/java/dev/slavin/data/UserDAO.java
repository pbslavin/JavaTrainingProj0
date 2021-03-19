package dev.slavin.data;

import dev.slavin.models.User;

import java.util.List;

public interface UserDAO {
    public List<User> getAllUsers();
    public User getUserById(int id);
    public User getUserByUserName(String userName);
    public User addNewUser(User user);
    public void updateUser(User user);
    public void deleteUser(int id);
    public User logIn(String userName, String password);
}
