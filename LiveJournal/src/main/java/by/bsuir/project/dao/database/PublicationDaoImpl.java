package by.bsuir.project.dao.database;

import by.bsuir.project.dao.PublicationDao;
import by.bsuir.project.entity.Genre;
import by.bsuir.project.entity.Publication;
import by.bsuir.project.entity.User;
import by.bsuir.project.exception.DateParseException;
import by.bsuir.project.exception.PersistentException;
import by.bsuir.project.util.UtilDate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PublicationDaoImpl extends BaseDaoImpl implements PublicationDao {
    private final Logger logger = LogManager.getLogger(this.getClass());

    private static final String CREATE_SQL = "INSERT INTO `publications` (name, content, public_date, user_id, genre_id) VALUES (?,?,?,?,?)";
    private static final String READ_BY_ID_SQL = "SELECT `name`, `content`, `public_date`, `user_id`, `genre_id` FROM `publications` WHERE `id` = ?";
    private static final String READ_SQL = "SELECT `id`, `name`, `content`, `public_date`, `user_id`, `genre_id` FROM `publications` ORDER BY `public_date`";
    private static final String UPDATE_SQL = "UPDATE `publications` SET `name` = ?, `content` = ?, `public_date` = ?,  `user_id` = ?, `genre_id` = ? WHERE `id` = ?";
    private static final String DELETE_SQL = "DELETE FROM `publications` WHERE `id` = ?";
    private static final String READ_BY_USER_SQL = "SELECT `id`, `name`, `content`, `public_date`, `genre_id` FROM `publications` WHERE `user_id` = ? ORDER BY `public_date`";
    private static final String READ_BY_USER_IS_NULL_SQL = "SELECT `id`, `name`, `content`, `public_date`, `genre_id` FROM `publications` WHERE `user_id` = ? IS NULL ORDER BY `public_date`";
    private static final String READ_BY_NAME_SQL = "SELECT `id`, `name`, `content`, `public_date`, `user_id`, `genre_id` FROM `publications` WHERE `name` COLLATE UTF8_GENERAL_CI LIKE ? ORDER BY `public_date`";
    private static final String READ_SUBLIST_SQL = "SELECT `id`, `name`, `content`, `public_date`, `user_id`, `genre_id` FROM `publications` ORDER BY `public_date` LIMIT ? OFFSET  ?";
    private static final String READ_SUBLIST_BY_USER_SQL = "SELECT `id`, `name`, `content`, `public_date`, `genre_id` FROM `publications` WHERE `user_id` = ? ORDER BY `public_date` LIMIT ? OFFSET  ?";
    private static final String READ_COUNT_ROW_SQL = "SELECT COUNT(*) FROM `publications`";
    private static final String READ_COUNT_ROW_BY_USER_SQL = "SELECT COUNT(*) FROM `publications` WHERE `user_id` = ?";

    /**
     * Creates publication (Dao)
     *
     * @param publication to get data from publication
     * @return created publication id
     * @throws PersistentException if happens some error
     */
    @Override
    public Integer create(Publication publication) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(CREATE_SQL, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, publication.getName());
            statement.setString(2, publication.getContent());
            statement.setString(3, UtilDate.toString(publication.getPublicDate()));
            statement.setInt(4, publication.getUser().getId());
            statement.setInt(5, publication.getGenre().getId());
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
     * Reads publication by id (Dao)
     *
     * @param id publication
     * @return publication by id
     * @throws PersistentException if happens some error
     */
    @Override
    public Publication read(Integer id) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(READ_BY_ID_SQL);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            Publication publication = null;
            if (resultSet.next()) {
                publication = new Publication();
                publication.setId(id);
                publication.setName(resultSet.getString("name"));
                publication.setContent(resultSet.getString("content"));
                publication.setPublicDate(UtilDate.fromString(resultSet.getString("public_date")));
                User user = new User();
                user.setId(resultSet.getInt("user_id"));
                publication.setUser(user);

                Genre genre = new Genre();
                genre.setId(resultSet.getInt("genre_id"));
                publication.setGenre(genre);

            }
            return publication;
        } catch (SQLException | DateParseException e) {
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
     * Updates publication (Dao)
     *
     * @param publication for update
     * @throws PersistentException if happens some error
     */
    @Override
    public void update(Publication publication) throws PersistentException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(UPDATE_SQL);
            statement.setString(1, publication.getName());
            statement.setString(2, publication.getContent());
            statement.setString(3, UtilDate.toString(publication.getPublicDate()));
            if (publication.getUser() != null && publication.getUser().getId() != null) {
                statement.setInt(4, publication.getUser().getId());
            } else {
                statement.setNull(4, Types.INTEGER);
            }
            if (publication.getGenre() != null && publication.getGenre().getId() != null) {
                statement.setInt(5, publication.getGenre().getId());
            } else {
                statement.setNull(5, Types.INTEGER);
            }
            statement.setInt(6, publication.getId());
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
     * Deletes publication (Dao)
     *
     * @param id for delete by id
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

    /**
     * Method for reads all publications by user id
     *
     * @param userId for user
     * @return publication list
     * @throws PersistentException if happens some error
     */
    @Override
    public List<Publication> readByUser(Integer userId) throws PersistentException {
        String sql;
        if (userId != null) {
            sql = READ_BY_USER_SQL;
        } else {
            sql = READ_BY_USER_IS_NULL_SQL;
        }
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(sql);
            if (userId != null) {
                statement.setInt(1, userId);
            }
            resultSet = statement.executeQuery();
            List<Publication> publications = new ArrayList<>();
            Publication publication;
            User user = null;
            if (userId != null) {
                user = new User();
                user.setId(userId);
            }
            while (resultSet.next()) {
                publication = new Publication();
                publication.setId(resultSet.getInt("id"));
                publication.setName(resultSet.getString("name"));
                publication.setContent(resultSet.getString("content"));
                publication.setPublicDate(UtilDate.fromString(resultSet.getString("public_date")));
                Genre genre = new Genre();
                genre.setId(resultSet.getInt("genre_id"));
                publication.setGenre(genre);
                publication.setUser(user);
                publications.add(publication);
            }
            return publications;
        } catch (SQLException | DateParseException e) {
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
     * Method for reads all publication by name (search)
     *
     * @param search publication name
     * @return publication list
     * @throws PersistentException if happens some error
     */
    @Override
    public List<Publication> readByName(String search) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(READ_BY_NAME_SQL);
            statement.setString(1, "%" + search + "%");
            resultSet = statement.executeQuery();
            List<Publication> publications = new ArrayList<>();
            while (resultSet.next()) {
                publications.add(buildPublication(resultSet));
            }
            return publications;
        } catch (SQLException | DateParseException e) {
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
     * Reads all publications (PublicationDao)
     *
     * @return publication list
     * @throws PersistentException if happens some error
     */
    @Override
    public List<Publication> read() throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(READ_SQL);
            resultSet = statement.executeQuery();
            List<Publication> publications = new ArrayList<>();
            while (resultSet.next()) {
                publications.add(buildPublication(resultSet));
            }
            return publications;
        } catch (SQLException | DateParseException e) {
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
     * Method for getting a certain number of elements for pagination
     *
     * @param limit  number of elements
     * @param offset confusion (position where to start reading)
     * @return list of publications
     * @throws PersistentException if happens some error
     */
    @Override
    public List<Publication> readSubList(int limit, int offset) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_SUBLIST_SQL)) {
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            ResultSet resultSet = statement.executeQuery();
            List<Publication> publications = new ArrayList<>();
            while (resultSet.next()) {
                publications.add(buildPublication(resultSet));
            }
            return publications;
        } catch (SQLException | DateParseException e) {
            throw new PersistentException(e);
        }
    }

    /**
     * The method that creates and fills the publication with data from the database
     *
     * @param resultSet To get data from the database
     * @return created publication
     * @throws SQLException       if an error occurred while working with the database
     * @throws DateParseException if an error occurred while working with the date
     */
    private Publication buildPublication(ResultSet resultSet) throws SQLException, DateParseException {
        Publication publication = new Publication();
        publication.setId(resultSet.getInt("id"));
        publication.setName(resultSet.getString("name"));
        publication.setContent(resultSet.getString("content"));
        publication.setPublicDate(UtilDate.fromString(resultSet.getString("public_date")));
        User user = new User();
        user.setId(resultSet.getInt("user_id"));
        publication.setUser(user);
        Genre genre = new Genre();
        genre.setId(resultSet.getInt("genre_id"));
        publication.setGenre(genre);
        return publication;
    }

    /**
     * A method that gets from the base the total number of elements of a certain table
     *
     * @return total number of elements
     * @throws PersistentException if happens some error
     */
    @Override
    public int readRowCount() throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_COUNT_ROW_SQL)) {
            ResultSet resultSet = statement.executeQuery();
            int count = 0;
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            return count;
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    /**
     * Method for getting a certain number of elements for pagination by level
     *
     * @param limit  number of elements
     * @param offset confusion (position where to start reading)
     * @param userId user id you are looking for
     * @return publication list
     * @throws PersistentException if happens some error
     */
    @Override
    public List<Publication> readSubListByUserId(int limit, int offset, Integer userId) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_SUBLIST_BY_USER_SQL)) {
            statement.setInt(1, userId);
            statement.setInt(2, limit);
            statement.setInt(3, offset);
            ResultSet resultSet = statement.executeQuery();
            List<Publication> publications = new ArrayList<>();
            Publication publication;
            while (resultSet.next()) {
                publication = new Publication();
                publication.setId(resultSet.getInt("id"));
                publication.setName(resultSet.getString("name"));
                publication.setContent(resultSet.getString("content"));
                publication.setPublicDate(UtilDate.fromString(resultSet.getString("public_date")));
                User user = new User();
                user.setId(userId);
                publication.setUser(user);
                Genre genre = new Genre();
                genre.setId(resultSet.getInt("genre_id"));
                publication.setGenre(genre);
                publications.add(publication);
            }
            return publications;
        } catch (SQLException | DateParseException e) {
            throw new PersistentException(e);
        }
    }

    /**
     * A method that gets from the base the total number of elements of a certain table
     *
     * @param userId user id you are looking for
     * @return total number of elements
     * @throws PersistentException if happens some error
     */
    @Override
    public int readRowCountByUserId(Integer userId) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_COUNT_ROW_BY_USER_SQL)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            int count = 0;
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            return count;
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

}
