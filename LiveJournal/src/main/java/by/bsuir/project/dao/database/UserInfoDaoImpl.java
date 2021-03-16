package by.bsuir.project.dao.database;

import by.bsuir.project.dao.UserInfoDao;
import by.bsuir.project.entity.Level;
import by.bsuir.project.entity.User;
import by.bsuir.project.entity.UserInfo;
import by.bsuir.project.exception.DateParseException;
import by.bsuir.project.exception.PersistentException;
import by.bsuir.project.util.UtilDate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserInfoDaoImpl extends BaseDaoImpl implements UserInfoDao {
    private final Logger logger = LogManager.getLogger(this.getClass());

    private static final String DELETE_SQL = "DELETE FROM `user_info` WHERE `id` = ?";
    private static final String UPDATE_SQL = "UPDATE `user_info` SET `surname` = ?, `name` = ?, `pseudonym` = ?, `level` = ?,  `date_of_birth` = ?, `id` = ? WHERE `users_id` = ?";
    private static final String READ_BY_NAME_SQL = "SELECT `id`, `name`, `surname`, `pseudonym`, `level`,  `date_of_birth`, `users_id` FROM `user_info` WHERE `name` COLLATE UTF8_GENERAL_CI LIKE ? ORDER BY `date_of_birth`";
    private static final String READ_BY_PSEUDONYM_SQL = "SELECT `id`, `name`, `surname`, `pseudonym`, `level`,  `date_of_birth`, `users_id` FROM `user_info` WHERE `pseudonym` COLLATE UTF8_GENERAL_CI LIKE ? ORDER BY `pseudonym`";
    private static final String COUNT_ROW_USER_SQL = "SELECT COUNT(*) FROM `user_info` WHERE `users_id` COLLATE UTF8_GENERAL_CI LIKE ?";
    private static final String COUNT_ROW_LEVEL_SQL = "SELECT COUNT(*) FROM `user_info` WHERE `level` COLLATE UTF8_GENERAL_CI LIKE ?";
    private static final String READ_SUBLIST_BY_USER_SQL = "SELECT `id`, `name`, `surname`, `pseudonym`, `level`,  `date_of_birth`, `users_id` FROM `user_info` WHERE `users_id` = ? ORDER BY `date_of_birth` LIMIT ? OFFSET  ?";
    private static final String READ_SUBLIST_BY_LEVEL_SQL = "SELECT `id`, `name`, `surname`, `pseudonym`, `level`,  `date_of_birth`, `users_id` FROM `user_info` WHERE `level` COLLATE UTF8_GENERAL_CI LIKE ? ORDER BY `date_of_birth` LIMIT ? OFFSET  ?";
    private static final String COUNT_ROW_SQL = "SELECT COUNT(*) FROM `user_info`";
    private static final String READ_SUBLIST_SQL = "SELECT `id`, `name`, `surname`, `pseudonym`, `level`,  `date_of_birth`, `users_id` FROM `user_info` ORDER BY `date_of_birth` LIMIT ? OFFSET  ?";
    private static final String READ_ALL_SQL = "SELECT `id`, `name`, `surname`, `pseudonym`, `level`,  `date_of_birth`, `users_id` FROM `user_info` ORDER BY `date_of_birth`";
    private static final String READ_BY_USER_ID_SQL = "SELECT `id`, `name`, `surname`, `pseudonym`, `level`, `date_of_birth` FROM `user_info` WHERE `users_id`  = ?";
    private static final String READ_BY_ID_SQL = "SELECT `name`, `surname`, `pseudonym`, `level`,  `date_of_birth`, `users_id` FROM `user_info` WHERE `id`  = ?";
    private static final String CREATE_SQL = "INSERT INTO `user_info` (`surname`, `name`, `pseudonym`, `level`, `date_of_birth`, `users_id`) VALUES (?, ?, ?, ?, ?, ?)";

    /**
     * Creates userInfo (Dao)
     *
     * @param userInfo to get data from userInfo
     * @return created userInfo id
     * @throws PersistentException if happens some error
     */
    @Override
    public Integer create(UserInfo userInfo) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(CREATE_SQL, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, userInfo.getSurname());
            statement.setString(2, userInfo.getName());
            statement.setString(3, userInfo.getPseudonym());
            statement.setInt(4, userInfo.getLevel().getLevelName());
            statement.setString(5, UtilDate.toString(userInfo.getDateOfBirth()));
            if(userInfo.getUser() != null && userInfo.getUser().getId() != null){
                statement.setInt(6, userInfo.getUser().getId());
            } else {
                statement.setNull(6, Types.INTEGER);
            }
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
     * Reads userInfo by id (Dao)
     *
     * @param id userinfo
     * @return userInfo by id
     * @throws PersistentException if happens some error
     */
    @Override
    public UserInfo read(Integer id) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(READ_BY_ID_SQL);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            UserInfo userInfo = null;
            if (resultSet.next()) {
                userInfo = new UserInfo();
                userInfo.setId(id);
                userInfo.setName(resultSet.getString("name"));
                userInfo.setSurname(resultSet.getString("surname"));
                userInfo.setPseudonym(resultSet.getString("pseudonym"));
                userInfo.setLevel(Level.getById(resultSet.getInt("level")));
                userInfo.setDateOfBirth(UtilDate.fromString(resultSet.getString("date_of_birth")));
                if (!resultSet.wasNull()) {
                    User user = new User();
                    user.setId(resultSet.getInt("users_id"));
                    userInfo.setUser(user);
                }
            }
            return userInfo;
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
     * Reads userInfo by user id
     *
     * @param userId for userId
     * @return userInfo by userId
     * @throws PersistentException if happens some error
     */
    @Override
    public UserInfo readByUserId(Integer userId) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(READ_BY_USER_ID_SQL);
            statement.setInt(1, userId);
            resultSet = statement.executeQuery();
            UserInfo userInfo = null;
            if (resultSet.next()) {
                userInfo = new UserInfo();
                userInfo.setId(resultSet.getInt("id"));
                userInfo.setName(resultSet.getString("name"));
                userInfo.setSurname(resultSet.getString("surname"));
                userInfo.setPseudonym(resultSet.getString("pseudonym"));
                userInfo.setLevel(Level.getById(resultSet.getInt("level")));
                userInfo.setDateOfBirth(UtilDate.fromString(resultSet.getString("date_of_birth")));
                if (!resultSet.wasNull()) {
                    User user = new User();
                    user.setId(userId);
                    userInfo.setUser(user);
                }
            }
            return userInfo;
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
     * Reads all userInfo (UserInfoDao)
     *
     * @return list of userInfo
     * @throws PersistentException if happens some error
     */
    @Override
    public List<UserInfo> read() throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(READ_ALL_SQL);
            resultSet = statement.executeQuery();
            List<UserInfo> usersInfo = new ArrayList<>();
            while (resultSet.next()) {
                usersInfo.add(buildUserInfo(resultSet));
            }
            return usersInfo;
        } catch (SQLException | DateParseException e) {
            throw new PersistentException(e);
        } finally {
            try {
                assert resultSet != null;
                resultSet.close();
                statement.close();
            } catch (SQLException | NullPointerException ignored) {
                logger.error("Error of closing the database in the `userInfo` table when reading all userInfo");
            }
        }
    }

    /**
     * Method for getting a certain number of elements for pagination
     *
     * @param limit  number of elements
     * @param offset confusion (position where to start reading)
     * @return list of usersInfo
     * @throws PersistentException if happens some error
     */
    @Override
    public List<UserInfo> readSubList(int limit, int offset) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_SUBLIST_SQL)) {
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            ResultSet resultSet = statement.executeQuery();
            List<UserInfo> usersInfo = new ArrayList<>();
            while (resultSet.next()) {
                usersInfo.add(buildUserInfo(resultSet));
            }
            return usersInfo;
        } catch (SQLException | DateParseException e) {
            throw new PersistentException(e);
        }
    }

    /**
     * The method that creates and fills the userInfo with data from the database
     *
     * @param resultSet To get data from the database
     * @return created userInfo
     * @throws SQLException       if an error occurred while working with the database
     * @throws DateParseException if an error occurred while working with the date
     */
    private UserInfo buildUserInfo(ResultSet resultSet) throws SQLException, DateParseException {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(resultSet.getInt("id"));
        userInfo.setName(resultSet.getString("name"));
        userInfo.setSurname(resultSet.getString("surname"));
        userInfo.setPseudonym(resultSet.getString("pseudonym"));
        userInfo.setLevel(Level.getById(resultSet.getInt("level")));
        userInfo.setDateOfBirth(UtilDate.fromString(resultSet.getString("date_of_birth")));
        User user = new User();
        user.setId(resultSet.getInt("users_id"));
        userInfo.setUser(user);
        return userInfo;
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
     * Method for getting a certain number of elements for pagination by level
     *
     * @param limit  number of elements
     * @param offset confusion (position where to start reading)
     * @param search login you are looking for
     * @return list of usersInfo
     * @throws PersistentException if happens some error
     */
    @Override
    public List<UserInfo> readSubListByLevel(int limit, int offset, String search) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_SUBLIST_BY_LEVEL_SQL)) {
            statement.setString(1, "%" + search + "%");
            statement.setInt(2, limit);
            statement.setInt(3, offset);
            ResultSet resultSet = statement.executeQuery();
            List<UserInfo> usersInfo = new ArrayList<>();
            UserInfo userInfo;
            while (resultSet.next()) {
                userInfo = new UserInfo();
                userInfo.setId(resultSet.getInt("id"));
                userInfo.setName(resultSet.getString("name"));
                userInfo.setSurname(resultSet.getString("surname"));
                userInfo.setPseudonym(resultSet.getString("pseudonym"));
                userInfo.setLevel(Level.getById(Integer.parseInt(search)));
                userInfo.setDateOfBirth(UtilDate.fromString(resultSet.getString("date_of_birth")));
                User user = new User();
                user.setId(resultSet.getInt("users_id"));
                userInfo.setUser(user);
                usersInfo.add(userInfo);
            }
            return usersInfo;
        } catch (SQLException | DateParseException e) {
            throw new PersistentException(e);
        }
    }

    /**
     * A method that gets from the base the total number of elements of a certain table
     *
     * @param search login you are looking for
     * @return total number of elements
     * @throws PersistentException if happens some error
     */
    @Override
    public int readRowCountByLevel(String search) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(COUNT_ROW_LEVEL_SQL)) {
            statement.setString(1, "%" + search + "%");
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
     * Method for getting a certain number of elements for pagination by userId
     *
     * @param limit  number of elements
     * @param offset confusion (position where to start reading)
     * @param userId user id you are looking for
     * @return list of usersInfo
     * @throws PersistentException if happens some error
     */
    @Override
    public List<UserInfo> readSubListByUserId(int limit, int offset, Integer userId) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_SUBLIST_BY_USER_SQL)) {
            statement.setInt(1, userId);
            statement.setInt(2, limit);
            statement.setInt(3, offset);
            ResultSet resultSet = statement.executeQuery();
            List<UserInfo> usersInfo = new ArrayList<>();
            UserInfo userInfo;
            while (resultSet.next()) {
                userInfo = new UserInfo();
                userInfo.setId(resultSet.getInt("id"));
                userInfo.setName(resultSet.getString("name"));
                userInfo.setSurname(resultSet.getString("surname"));
                userInfo.setPseudonym(resultSet.getString("pseudonym"));
                userInfo.setLevel(Level.getById(resultSet.getInt("level")));
                userInfo.setDateOfBirth(UtilDate.fromString(resultSet.getString("date_of_birth")));
                User user = new User();
                user.setId(userId);
                userInfo.setUser(user);
                usersInfo.add(userInfo);
            }
            return usersInfo;
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
        try (PreparedStatement statement = connection.prepareStatement(COUNT_ROW_USER_SQL)) {
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

    /**
     * Method to get all users by name
     *
     * @param search name
     * @return usersInfo list
     * @throws PersistentException if happens some error
     */
    @Override
    public List<UserInfo> readByName(String search) throws PersistentException {
        return getSearchUserInfoList(search, READ_BY_NAME_SQL);
    }

    /**
     * Method to get all users by pseudonym
     *
     * @param search pseudonym
     * @return usersInfo list
     * @throws PersistentException if happens some error
     */
    @Override
    public List<UserInfo> readByPseudonym(String search) throws PersistentException {
        return getSearchUserInfoList(search, READ_BY_PSEUDONYM_SQL);
    }

    /**
     * Released method to get all usersInfo for specific database queries
     *
     * @param search what to look for
     * @param sql    database query
     * @return usersInfo list
     * @throws PersistentException if happens some error
     */
    private List<UserInfo> getSearchUserInfoList(String search, String sql) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + search + "%");
            resultSet = statement.executeQuery();
            List<UserInfo> userInfos = new ArrayList<>();
            while (resultSet.next()) {
                userInfos.add(buildUserInfo(resultSet));
            }
            return userInfos;
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
     * Updates userInfo (Dao)
     *
     * @param userInfo for update
     * @throws PersistentException if happens some error
     */
    @Override
    public void update(UserInfo userInfo) throws PersistentException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(UPDATE_SQL);
            statement.setString(1, userInfo.getSurname());
            statement.setString(2, userInfo.getName());
            statement.setString(3, userInfo.getPseudonym());
            statement.setInt(4, userInfo.getLevel().getLevelName());
            statement.setString(5, UtilDate.toString(userInfo.getDateOfBirth()));
            statement.setInt(6, userInfo.getId());
            if(userInfo.getUser() != null && userInfo.getUser().getId() != null) {
                statement.setInt(7, userInfo.getUser().getId());
            } else {
                statement.setNull(7, Types.INTEGER);
            }
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
     * Deletes userInfo (Dao)
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
