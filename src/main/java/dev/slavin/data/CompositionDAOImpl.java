package dev.slavin.data;

import dev.slavin.models.Composer;
import dev.slavin.models.Composition;
import dev.slavin.models.Genre;
import dev.slavin.util.ConnectionUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CompositionDAOImpl implements CompositionDAO {

    private Logger logger = LoggerFactory.getLogger(CompositionDAOImpl.class);

    @Override
    public List<Composition> getAllCompositions() {
        List<Composition> compositions = new ArrayList<>();

        try (Connection connection = ConnectionUtility.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from composition");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                int composerId = resultSet.getInt("composer_id");
                int yearComposed = resultSet.getInt("year_composed");
                Genre genre = Genre.valueOf(resultSet.getString("genre"));
                boolean multiMovement = resultSet.getBoolean(("multi_movement"));
                Composition composition = new Composition(id, title, composerId, yearComposed, genre, multiMovement);
                compositions.add(composition);
            }
        } catch (SQLException e) {
            logger.error(e.getClass() + " " + e.getMessage());
        }
        return compositions;
    }

    @Override
    public Composition getCompositionById(int id) {
        try (Connection connection = ConnectionUtility.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from composition where id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String title = resultSet.getString("title");
                int composerId = resultSet.getInt("composer_id");
                int yearComposed = resultSet.getInt("year_composed");
                Genre genre = Genre.valueOf(resultSet.getString("genre"));
                Boolean multiMovement = resultSet.getBoolean("multi_movement");
                return new Composition(id, title, composerId, yearComposed, genre, multiMovement);
            }
        } catch (SQLException e) {
            logger.error(e.getClass() + " " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Composition> getCompositionsByComposer(int composerId) {
        List<Composition> compositions = new ArrayList<>();
        try (Connection connection = ConnectionUtility.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from composition where composer_id = ?");
            preparedStatement.setInt(1, composerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                int yearComposed = resultSet.getInt("year_composed");
                Genre genre = Genre.valueOf(resultSet.getString("genre"));
                Boolean multiMovement = resultSet.getBoolean("multi_movement");
                Composition composition = new Composition(id, title, composerId, yearComposed, genre, multiMovement);
                compositions.add(composition);
            }
        } catch (SQLException e) {
            logger.error(e.getClass() + " " + e.getMessage());
        }
        return compositions;
    }

    @Override
    public Composition addNewComposition(Composition composition) {
        try (Connection connection = ConnectionUtility.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into composition (title, composer_id, year_composed, genre, multi_movement) " +
                            "values (?, ?, ?, ?, ?)"
            );
            preparedStatement.setString(1, composition.getTitle());
            preparedStatement.setInt(2, composition.getComposerId());
            preparedStatement.setInt(3, composition.getYearComposed());
            preparedStatement.setString(4, composition.getGenre().toString());
            preparedStatement.setBoolean(5, composition.getMultiMovement());
            ResultSet resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            logger.error(e.getClass() + " " + e.getMessage());
        }
        return null;
    }

    @Override
    public void updateComposition(Composition composition) {
        Composition oldComposition = getCompositionById(composition.getId());
        try (Connection connection = ConnectionUtility.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "update composition set title = ?, composer_id = ?, year_composed = ?, genre = ?, multi_movement = ? where id = ?"
            );
            preparedStatement.setString(1, composition.getTitle());
            preparedStatement.setInt(2, composition.getComposerId());
            preparedStatement.setInt(3, composition.getYearComposed());
            preparedStatement.setString(4, composition.getGenre().toString());
            preparedStatement.setBoolean(5, composition.getMultiMovement());
            preparedStatement.setInt(6, composition.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            logger.error(e.getClass() + " " + e.getMessage());
        }
    }

    @Override
    public void deleteComposition(int id) {
        try (Connection connection = ConnectionUtility.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from table composition where id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeQuery();
        } catch (SQLException e) {
            logger.error(e.getClass() + " " + e.getMessage());
        }
    }
}
