package dev.slavin.data;

import dev.slavin.models.User;
import dev.slavin.util.ConnectionUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    private Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (Connection connection = ConnectionUtility.getConnection();
            Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("select * from user_data");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String userName = resultSet.getString("user_name");
                String password = resultSet.getString("password");
                int authLevel = resultSet.getInt("auth_level");
                users.add(new User(id, userName, password, authLevel));
            }
        } catch (SQLException e) {
            logger.error(String.format("%s %s", e.getClass(), e.getMessage()));
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
                String userName = resultSet.getString("user_name");
                String password = resultSet.getString("password");
                int authLevel = resultSet.getInt("auth_level");
                return new User(id, userName, password, authLevel);
            }
        } catch (SQLException e) {
            logger.error(String.format("%s %s", e.getClass(), e.getMessage()));
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
                String password = resultSet.getString("password");
                int authLevel = resultSet.getInt("auth_level");
                return new User(id, userName, password, authLevel);
            }
        } catch (SQLException e) {
            logger.error(String.format("%s %s", e.getClass(), e.getMessage()));
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
            logger.error(String.format("%s %s", e.getClass(), e.getMessage()));
        }
        return user;
    }

    @Override
    public User updateUser(User user) {
        try (Connection connection = ConnectionUtility.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "update table user_data set userName = ?, password = ?, authLevel = ?")) {
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.executeQuery();
        } catch (SQLException e) {
            logger.error(String.format("%s %s", e.getClass(), e.getMessage()));
        }
        return user;
    }

    @Override
    public void deleteUser(int id) {
        try (Connection connection = ConnectionUtility.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("delete from table user where id = ?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeQuery();
        } catch (SQLException e) {
            logger.error(String.format("%s %s", e.getClass(), e.getMessage()));
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
}
