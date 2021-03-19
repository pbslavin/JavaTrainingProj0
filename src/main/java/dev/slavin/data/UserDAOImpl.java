package dev.slavin.data;

import dev.slavin.models.User;
import dev.slavin.util.ConnectionUtility;
import dev.slavin.util.ErrorLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class UserDAOImpl implements UserDAO {
    private Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);
    private ErrorLogger errorLogger = new ErrorLogger(UserDAOImpl.class, logger);

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (Connection connection = ConnectionUtility.getConnection();
            Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("select * from user_data");
            while (resultSet.next()) {
                int id = resultSet.getInt(UserMapping.ID);
                String userName = resultSet.getString(UserMapping.USERNAME);
                String password = resultSet.getString(UserMapping.PASSWORD);
                int authLevel = resultSet.getInt(UserMapping.AUTH_LEVEL);
                users.add(new User(id, userName, password, authLevel));
            }
        } catch (SQLException e) {
            errorLogger.logError(e);
            throw new NoSuchElementException();
        }
        return users;
    }

    @Override
    public User getUserById(int id) {
        try (Connection connection = ConnectionUtility.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from user_data where id = ?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String userName = resultSet.getString(UserMapping.USERNAME);
                String password = resultSet.getString(UserMapping.PASSWORD);
                int authLevel = resultSet.getInt(UserMapping.AUTH_LEVEL);
                return new User(id, userName, password, authLevel);
            }
        } catch (SQLException e) {
            errorLogger.logError(e);
            throw new NoSuchElementException();
        }
        return null;
    }

    @Override
    public User getUserByUserName(String userName) {
        try (Connection connection = ConnectionUtility.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from user_data where user_name = ?")) {
            preparedStatement.setString(1, userName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String password = resultSet.getString(UserMapping.PASSWORD);
                int authLevel = resultSet.getInt(UserMapping.AUTH_LEVEL);
                return new User(id, userName, password, authLevel);
            }
        } catch (SQLException e) {
            errorLogger.logError(e);
            throw new NoSuchElementException();
        }
        return null;
    }

    @Override
    public User addNewUser(User user) {
        try (Connection connection = ConnectionUtility.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "insert into user_data (user_name, password, auth_level) values (?, ?, ?)")) {
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setInt(3, user.getAuthLevel());
            preparedStatement.executeQuery();
        } catch (Exception e) {
            errorLogger.logError(e);
            throw new NoSuchElementException();
        }
        return user;
    }

    @Override
    public User updateUser(User user) {
        try (Connection connection = ConnectionUtility.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "update user_data set password = ?, auth_level = ? where id = ?")) {
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setInt(2, user.getAuthLevel());
            preparedStatement.setInt(3, user.getId());
            preparedStatement.executeQuery();
        } catch (SQLException e) {
            errorLogger.logError(e);
        }
        return user;
    }

    @Override
    public void deleteUser(int id) {
        try (Connection connection = ConnectionUtility.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("delete from user where id = ?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeQuery();
        } catch (SQLException e) {
            errorLogger.logError(e);
        }
    }

    @Override
    public User logIn(String userName, String password) {
        User user = new User();
        if (userName != null && password != null) {
            user = getUserByUserName(userName);
        }
        return user;
    }

    private static class UserMapping {
        private static final String ID = "id";
        private static final String USERNAME = "user_name";
        private static final String PASSWORD = "password";
        private static final String AUTH_LEVEL = "auth_level";
    }
}
