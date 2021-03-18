package dev.slavin.data;

import dev.slavin.models.Composer;
import dev.slavin.models.Composition;
import dev.slavin.models.Genre;
import dev.slavin.util.ConnectionUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComposerDAOImpl implements ComposerDAO {

    private Logger logger = LoggerFactory.getLogger(ComposerDAOImpl.class);

    @Override
    public List<Composer> getAllComposers() {
        List<Composer> composers = new ArrayList<>();

        try (Connection connection = ConnectionUtility.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("select * from composer");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int birthYear = resultSet.getInt("birth_year");
                int deathYear = resultSet.getInt("death_year");
                Composer composer = new Composer(id, name, birthYear, deathYear);
                composers.add(composer);
            }
        } catch (SQLException e) {
            logger.error(e.getClass() + " " + e.getMessage());
        }
        return composers;
    }

    @Override
    public Composer getComposerById(int id) {
        try (Connection connection = ConnectionUtility.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select * from composer where id = ?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                int birthYear = resultSet.getInt("birth_year");
                int deathYear = resultSet.getInt("death_year");
                return new Composer(id, name, birthYear, deathYear);
            }
        } catch (SQLException e) {
            logger.error(e.getClass() + " " + e.getMessage());
        }
        return null;
    }

    @Override
    public Composer addNewComposer(Composer composer) {
        try (Connection connection = ConnectionUtility.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "insert into composer (name, birth_year, death_year) " +
                             "values (?, ?, ?)"
             )) {
            preparedStatement.setString(1, composer.getName());
            preparedStatement.setInt(2, composer.getBirthYear());
            preparedStatement.setInt(3, composer.getDeathYear());
            ResultSet resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            logger.error(e.getClass() + " " + e.getMessage());
        }
        return null;
    }

    @Override
    public void updateComposer(Composer composer) {
        Composer oldComposer = getComposerById(composer.getId());
        try (Connection connection = ConnectionUtility.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "update table composer set name = ?, birth_year = ?, death_year = ? where id = ?"
             )) {
            preparedStatement.setString(1, composer.getName());
            preparedStatement.setInt(2, composer.getBirthYear());
            preparedStatement.setInt(3, composer.getDeathYear());
            preparedStatement.setInt(4, composer.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            logger.error(e.getClass() + " " + e.getMessage());
        }
    }

    @Override
    public void deleteComposer(int id) {
        try (Connection connection = ConnectionUtility.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("delete from composer where id = ?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            logger.error(e.getClass() + " " + e.getMessage());
        }
    }
}
