package by.bsuir.project.dao.database;

import by.bsuir.project.dao.GenreDao;
import by.bsuir.project.entity.Genre;
import by.bsuir.project.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GenreDaoImpl extends BaseDaoImpl implements GenreDao {
    private final Logger logger = LogManager.getLogger(this.getClass());

    private static final String CREATE_SQL = "INSERT INTO `genre` (name) VALUES (?)";
    private static final String READE_SQL = "SELECT `id`, `name` FROM `genre` ORDER BY `name`";
    private static final String READ_BY_ID_SQL = "SELECT `name` FROM `genre` WHERE `id` = ?";
    private static final String UPDATE_SQL = "UPDATE `genre` SET `name` = ? WHERE `id` = ?";
    private static final String DELETE_SQL = "DELETE FROM `genre` WHERE `id` = ?";

    /**
     * Creates genre (Dao)
     *
     * @param genre to get data from genre
     * @return created genre id
     * @throws PersistentException if happens some error
     */
    @Override
    public Integer create(Genre genre) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(CREATE_SQL, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, genre.getTitle());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                logger.error("There is no autoincrement index after trying to add record into table `users`");
                throw new PersistentException();
            }
        } catch (SQLException e) {
            throw new PersistentException(e);
        } finally {
            try {
                assert resultSet != null;
                resultSet.close();
                statement.close();
            } catch (SQLException | NullPointerException ignored) {
                logger.error("Error of closing the database in the `users` table when creating user");
            }
        }
    }

    /**
     * Reads all genre
     *
     * @return genre list
     * @throws PersistentException if happens some error
     */
    @Override
    public List<Genre> read() throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(READE_SQL);
            resultSet = statement.executeQuery();
            List<Genre> genres = new ArrayList<>();
            Genre genre;
            while (resultSet.next()) {
                genre = new Genre();
                genre.setId(resultSet.getInt("id"));
                genre.setTitle(resultSet.getString("name"));
                genres.add(genre);
            }
            return genres;
        } catch (SQLException e) {
            throw new PersistentException(e);
        } finally {
            try {
                assert resultSet != null;
                resultSet.close();
                statement.close();
            } catch (SQLException | NullPointerException ignored) {
                logger.error("Error of closing the database in the `users` table when reading all users");
            }
        }
    }

    /**
     * Reads genre by id (Dao)
     *
     * @param id genre
     * @return genre by id
     * @throws PersistentException if happens some error
     */
    @Override
    public Genre read(Integer id) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(READ_BY_ID_SQL);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            Genre genre = null;
            if (resultSet.next()) {
                genre = new Genre();
                genre.setId(id);
                genre.setTitle(resultSet.getString("name"));
            }
            return genre;
        } catch (SQLException e) {
            throw new PersistentException(e);
        } finally {
            try {
                assert resultSet != null;
                resultSet.close();
                statement.close();
            } catch (SQLException | NullPointerException ignored) {
                logger.error("Error of closing the database in the `users` table when reading by id");
            }
        }
    }

    /**
     * Updates genre (Dao)
     *
     * @param genre for update
     * @throws PersistentException if happens some error
     */
    @Override
    public void update(Genre genre) throws PersistentException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(UPDATE_SQL);
            statement.setString(1, genre.getTitle());
            statement.setInt(2, genre.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new PersistentException(e);
        } finally {
            try {
                assert statement != null;
                statement.close();
            } catch (SQLException | NullPointerException ignored) {
                logger.error("Error of closing the database in the `users` table when updating user");
            }
        }
    }

    /**
     * Deletes genre (Dao)
     *
     * @param id for delete by id
     * @throws PersistentException if happens some error
     */
    @Override
    public void delete(Integer id) throws PersistentException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(DELETE_SQL);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new PersistentException(e);
        } finally {
            try {
                assert statement != null;
                statement.close();
            } catch (SQLException | NullPointerException ignored) {
                logger.error("Error of closing the database in the `users` table when deleting user");
            }
        }
    }
}
