package dev.slavin.data;

import dev.slavin.models.Composer;
import dev.slavin.util.ConnectionUtility;
import dev.slavin.util.ErrorLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ComposerDAOImpl implements ComposerDAO {

    private Logger logger = LoggerFactory.getLogger(ComposerDAOImpl.class);
    private ErrorLogger errorLogger = new ErrorLogger(ComposerDAOImpl.class, logger);

    @Override
    public List<Composer> getAllComposers() {
        List<Composer> composers = new ArrayList<>();

        try (Connection connection = ConnectionUtility.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("select * from composer");

            while (resultSet.next()) {
                int id = resultSet.getInt(ComposerMapping.ID);
                String name = resultSet.getString(ComposerMapping.NAME);
                int birthYear = resultSet.getInt(ComposerMapping.BIRTH_YEAR);
                int deathYear = resultSet.getInt(ComposerMapping.DEATH_YEAR);
                Composer composer = new Composer(id, name, birthYear, deathYear);
                composers.add(composer);
            }
        } catch (SQLException e) {
            errorLogger.logError(e);
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
                String name = resultSet.getString(ComposerMapping.NAME);
                int birthYear = resultSet.getInt(ComposerMapping.BIRTH_YEAR);
                int deathYear = resultSet.getInt(ComposerMapping.DEATH_YEAR);
                return new Composer(id, name, birthYear, deathYear);
            }
        } catch (SQLException e) {
            errorLogger.logError(e);
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
            preparedStatement.executeQuery();
        } catch (SQLException e) {
            errorLogger.logError(e);
        }
        return null;
    }

    @Override
    public void updateComposer(Composer composer) {
        try (Connection connection = ConnectionUtility.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "update composer set name = ?, birth_year = ?, death_year = ? where id = ?"
             )) {
            preparedStatement.setString(1, composer.getName());
            preparedStatement.setInt(2, composer.getBirthYear());
            preparedStatement.setInt(3, composer.getDeathYear());
            preparedStatement.setInt(4, composer.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            errorLogger.logError(e);
        }
    }

    @Override
    public void deleteComposer(int id) {
        try (Connection connection = ConnectionUtility.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("delete from composer where id = ?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeQuery();
        } catch (SQLException e) {
            errorLogger.logError(e);
            throw new NoSuchElementException();
        }
    }

    private static class ComposerMapping {
        private static final String ID = "id";
        private static final String NAME = "name";
        private static final String BIRTH_YEAR = "birth_year";
        private static final String DEATH_YEAR = "death_year";
    }

}
