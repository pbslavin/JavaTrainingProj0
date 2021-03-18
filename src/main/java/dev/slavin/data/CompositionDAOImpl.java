package dev.slavin.data;

import dev.slavin.models.Composition;
import dev.slavin.models.Genre;
import dev.slavin.util.ConnectionUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompositionDAOImpl implements CompositionDAO {

    private Logger logger = LoggerFactory.getLogger(CompositionDAOImpl.class);

    @Override
    public List<Composition> getAllCompositions() {
        List<Composition> compositions = new ArrayList<>();

        try (Connection connection = ConnectionUtility.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("select * from composition");

            while (resultSet.next()) {
                int id = resultSet.getInt(CompositionMapping.ID);
                String title = resultSet.getString(CompositionMapping.TITLE);
                int composerId = resultSet.getInt(CompositionMapping.COMPOSER_ID);
                int yearComposed = resultSet.getInt(CompositionMapping.YEAR_COMPOSED);
                Genre genre = Genre.valueOf(resultSet.getString(CompositionMapping.GENRE));
                boolean multiMovement = resultSet.getBoolean(CompositionMapping.MULTI_MOVEMENT);
                Composition composition = new Composition(id, title, composerId, yearComposed, genre, multiMovement);
                compositions.add(composition);
            }
        } catch (SQLException e) {
            logException(e);
        }
        return compositions;
    }

    @Override
    public Composition getCompositionById(int id) {
        try (Connection connection = ConnectionUtility.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select * from composition where id = ?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String title = resultSet.getString(CompositionMapping.TITLE);
                int composerId = resultSet.getInt(CompositionMapping.COMPOSER_ID);
                int yearComposed = resultSet.getInt(CompositionMapping.YEAR_COMPOSED);
                Genre genre = Genre.valueOf(resultSet.getString(CompositionMapping.GENRE));
                boolean multiMovement = resultSet.getBoolean(CompositionMapping.MULTI_MOVEMENT);
                return new Composition(id, title, composerId, yearComposed, genre, multiMovement);
            }
        } catch (SQLException e) {
            logException(e);
        }
        return null;
    }

    @Override
    public List<Composition> getCompositionsByComposer(int composerId) {
        List<Composition> compositions = new ArrayList<>();
        try (Connection connection = ConnectionUtility.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select * from composition where composer_id = ?")) {
            preparedStatement.setInt(1, composerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString(CompositionMapping.TITLE);
                int yearComposed = resultSet.getInt(CompositionMapping.YEAR_COMPOSED);
                Genre genre = Genre.valueOf(resultSet.getString(CompositionMapping.GENRE));
                Boolean multiMovement = resultSet.getBoolean(CompositionMapping.MULTI_MOVEMENT);
                Composition composition = new Composition(id, title, composerId, yearComposed, genre, multiMovement);
                compositions.add(composition);
            }
        } catch (SQLException e) {
            logException(e);
        }
        return compositions;
    }

    @Override
    public Composition addNewComposition(Composition composition) {
        try (Connection connection = ConnectionUtility.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "insert into composition (TITLE, composer_id, year_composed, GENRE, multi_movement) " +
                             "values (?, ?, ?, ?, ?)"
             )) {
            preparedStatement.setString(1, composition.getTitle());
            preparedStatement.setInt(2, composition.getComposerId());
            preparedStatement.setInt(3, composition.getYearComposed());
            preparedStatement.setString(4, composition.getGenre().toString());
            preparedStatement.setBoolean(5, composition.getMultiMovement());
            preparedStatement.executeQuery();
        } catch (SQLException e) {
            logException(e);
        }
        return null;
    }

    @Override
    public void updateComposition(Composition composition) {
        try (Connection connection = ConnectionUtility.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "update composition set TITLE = ?, composer_id = ?, year_composed = ?, GENRE = ?, multi_movement = ? where id = ?"
             )) {
            preparedStatement.setString(1, composition.getTitle());
            preparedStatement.setInt(2, composition.getComposerId());
            preparedStatement.setInt(3, composition.getYearComposed());
            preparedStatement.setString(4, composition.getGenre().toString());
            preparedStatement.setBoolean(5, composition.getMultiMovement());
            preparedStatement.setInt(6, composition.getId());
            preparedStatement.executeQuery();
        } catch (SQLException e) {
            logException(e);
        }
    }

    @Override
    public void deleteComposition(int id) {
        try (Connection connection = ConnectionUtility.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "delete from table composition where id = ?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeQuery();
        } catch (SQLException e) {
            logException(e);
        }
    }
    
    private void logException(Exception e) {
        logger.error("{} - {}", e.getClass(), e.getMessage());
    }

    private static class CompositionMapping {
        private static final String ID = "id";
        private static final String TITLE = "title";
        private static final String COMPOSER_ID = "composer_id";
        private static final String YEAR_COMPOSED = "year_composed";
        private static final String GENRE = "genre";
        private static final String MULTI_MOVEMENT = "multi_movement";
    }
}
