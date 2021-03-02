package by.bsuir.project.dao.database;


import by.bsuir.project.dao.UserDao;
import by.bsuir.project.entity.Role;
import by.bsuir.project.entity.User;
import by.bsuir.project.exception.DateParseException;
import by.bsuir.project.exception.PersistentException;
import by.bsuir.project.util.UtilDate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl extends BaseDaoImpl implements UserDao {
    private final Logger logger = LogManager.getLogger(this.getClass());

    private static final String READ_SQL = "SELECT `login`, `password`,`email`, `date_of_reg`, `role`, `is_active` FROM `users` WHERE `id` = ?";
    private static final String CREATE_SQL = "INSERT INTO `users` (`login`, `password`, `email`, `date_of_reg`, `role`, `is_active`) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE `users` SET `login` = ?, `password` = ?, `email`  = ?,`date_of_reg` = ?, `role` = ?, `is_active` = ? WHERE `id` = ?";
    private static final String DELETE_SQL = "DELETE FROM `users` WHERE `id` = ?";
    private static final String READ_ALL_SQL = "SELECT `id`, `login`, `password`, `email`, `date_of_reg`, `role`, `is_active` FROM `users` ORDER BY `date_of_reg`";
    private static final String READ_LOGIN_PASS_SQL = "SELECT `id`, `email`, `date_of_reg`, `role`, `is_active` FROM `users` WHERE `login` = ? AND `password` = ?";
    private static final String READ_SUBLIST_SQL = "SELECT `id`, `login`, `password`, `email`, `date_of_reg`, `role`, `is_active` FROM `users` ORDER BY `date_of_reg` LIMIT ? OFFSET  ?";
    private static final String READ_BY_LOGIN_SQL = "SELECT `id`, `login`, `password`, `email`, `date_of_reg`, `role`, `is_active` FROM `users` WHERE `login` COLLATE UTF8_GENERAL_CI LIKE ? ORDER BY `date_of_reg`";
    private static final String COUNT_ROW_SQL = "SELECT COUNT(*) FROM `users`";

    /**
     * Reads user by id (Dao)
     *
     * @param id for user
     * @return user by id
     * @throws PersistentException if happens some error
     */
    @Override
    public User read(Integer id) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(READ_SQL);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            User user = null;
            if(resultSet.next()) {
                user = new User();
                user.setId(id);
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
                user.setDateOfReg(UtilDate.fromString(resultSet.getString("date_of_reg")));
                user.setRole(Role.getById(resultSet.getInt("role")));
                user.setActive(resultSet.getBoolean("is_active"));
            }
            return user;
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
     * Reads user by login and password (UserDao)
     *
     * @param login    for user
     * @param password for user
     * @return user by login and password
     * @throws PersistentException if happens some error
     */
    @Override
    public User read(String login, String password) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(READ_LOGIN_PASS_SQL);
            statement.setString(1, login);
            statement.setString(2, password);
            resultSet = statement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setDateOfReg(UtilDate.fromString(resultSet.getString("date_of_reg")));
                user.setRole(Role.getById(resultSet.getInt("role")));
                user.setActive(resultSet.getBoolean("is_active"));
                user.setLogin(login);
                user.setPassword(password);
            }
            return user;
        } catch (SQLException | DateParseException e) {
            throw new PersistentException(e);
        } finally {
            try {
                assert resultSet != null;
                resultSet.close();
                statement.close();
            } catch (SQLException | NullPointerException ignored) {
                logger.error("Error of closing the database in the `users` table when reading by login and password");
            }
        }
    }

    /**
     * Reads all users (UserDao)
     *
     * @return list of users
     * @throws PersistentException if happens some error
     */
    @Override
    public List<User> read() throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(READ_ALL_SQL);
            resultSet = statement.executeQuery();
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(buildUser(resultSet));
            }
            logger.info("user ready");
            return users;
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
     * Reads all users by login
     *
     * @param search login you are looking for
     * @return list of users
     * @throws PersistentException if happens some error
     */
    @Override
    public List<User> readByLogin(String search) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(READ_BY_LOGIN_SQL);
            statement.setString(1, "%" + search + "%");
            resultSet = statement.executeQuery();
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(buildUser(resultSet));
            }
            return users;
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
     * Method for getting a certain number of elements for pagination
     *
     * @param limit  number of elements
     * @param offset confusion (position where to start reading)
     * @return list of users
     * @throws PersistentException if happens some error
     */
    @Override
    public List<User> readSubList(int limit, int offset) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_SUBLIST_SQL)) {
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            ResultSet resultSet = statement.executeQuery();
            List<User> usersInfo = new ArrayList<>();
            while (resultSet.next()) {
                usersInfo.add(buildUser(resultSet));
            }
            return usersInfo;
        } catch (SQLException | DateParseException e) {
            throw new PersistentException(e);
        }
    }

    /**
     * The method that creates and fills the user with data from the database
     *
     * @param resultSet To get data from the database
     * @return created user
     * @throws SQLException       if an error occurred while working with the database
     * @throws DateParseException if an error occurred while working with the date
     */
    private User buildUser(ResultSet resultSet) throws SQLException, DateParseException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setLogin(resultSet.getString("login"));
        user.setPassword(resultSet.getString("password"));
        user.setEmail(resultSet.getString("email"));
        user.setDateOfReg(UtilDate.fromString(resultSet.getString("date_of_reg")));
        user.setRole(Role.getById(resultSet.getInt("role")));
        user.setActive(resultSet.getBoolean("is_active"));
        return user;
    }

    /**
     * A method that gets from the base the total number of elements of a certain table
     *
     * @return total number of elements
     * @throws PersistentException if happens some error
     */
    @Override
    public int readRowCount() throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(COUNT_ROW_SQL)) {
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
     * Creates user (Dao)
     *
     * @param user which needs to be created
     * @return created user id
     * @throws PersistentException if happens some error
     */
    @Override
    public Integer create(User user) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(CREATE_SQL, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, UtilDate.toString(user.getDateOfReg()));
            statement.setInt(5, user.getRole().getRoleName());
            statement.setInt(6, user.isActive() ? 1 : 0);
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
     * Updates user (Dao)
     *
     * @param user which needs to be updated
     * @throws PersistentException if happens some error
     */
    @Override
    public void update(User user) throws PersistentException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(UPDATE_SQL);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, UtilDate.toString(user.getDateOfReg()));
            statement.setInt(5, user.getRole().getRoleName());
            statement.setInt(6, user.isActive() ? 1 : 0);
            statement.setInt(7, user.getId());
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
     * Deletes user (Dao)
     *
     * @param id user which needs to be deleted
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