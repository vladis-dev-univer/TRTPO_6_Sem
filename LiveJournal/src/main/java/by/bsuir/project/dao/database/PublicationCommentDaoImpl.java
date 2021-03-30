package by.bsuir.project.dao.database;

import by.bsuir.project.dao.PublicationCommentDao;
import by.bsuir.project.entity.Publication;
import by.bsuir.project.entity.PublicationComment;
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

public class PublicationCommentDaoImpl extends BaseDaoImpl implements PublicationCommentDao {
    private final Logger logger = LogManager.getLogger(this.getClass());

    private static final String CREATE_SQL = "INSERT INTO `publication_comment` (user_id, text, comment_data, publications_id) VALUES (?,?,?,?)";
    private static final String READ_BY_ID_SQL = "SELECT `user_id`, `text`, `comment_data`, `publications_id` FROM `publication_comment` WHERE `id`  = ?";
    private static final String UPDATE_SQL = "UPDATE `publication_comment` SET `text` = ?, `comment_data` = ?, `user_id` = ?, `publications_id` = ? WHERE `id` = ?";
    private static final String DELETE_SQL = "DELETE FROM `publication_comment` WHERE `id` = ?";
    private static final String READE_BY_PUBLICATION_SQL = "SELECT `id`, `user_id`, `text`, `comment_data` FROM `publication_comment` WHERE `publications_id` = ? ORDER BY `comment_data`";
    private static final String READE_BY_PUBLICATION_IS_NULL_SQL = "SELECT `id`, `user_id`, `text`, `comment_data` FROM `publication_comment` WHERE `publications_id` = ? IS NULL ORDER BY `comment_data`";

    /**
     * Creates publication comment (Dao)
     *
     * @param publicationComment to get data from publication comment
     * @return created publication comment id
     * @throws PersistentException if happens some error
     */
    @Override
    public Integer create(PublicationComment publicationComment) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(CREATE_SQL, Statement.RETURN_GENERATED_KEYS);
            if (publicationComment.getUserInfo().getUser() != null && publicationComment.getUserInfo().getUser().getId() != null) {
                statement.setInt(1, publicationComment.getUserInfo().getUser().getId());
            } else {
                statement.setNull(1, Types.INTEGER);
            }
            statement.setString(2, publicationComment.getText());
            statement.setString(3, UtilDate.toString(publicationComment.getCommentDate()));
            if (publicationComment.getPublication() != null && publicationComment.getPublication().getId() != null) {
                statement.setInt(4, publicationComment.getPublication().getId());
            } else {
                statement.setNull(4, Types.INTEGER);
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
     * Reads publication comment by id (Dao)
     *
     * @param id publication comment
     * @return publication comment by id
     * @throws PersistentException if happens some error
     */
    @Override
    public PublicationComment read(Integer id) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(READ_BY_ID_SQL);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            PublicationComment publicationComment = null;
            if (resultSet.next()) {
                publicationComment = new PublicationComment();
                publicationComment.setId(id);
                publicationComment.setText(resultSet.getString("text"));
                publicationComment.setCommentDate(UtilDate.fromString(resultSet.getString("comment_data")));
                if (!resultSet.wasNull()) {
                    Publication publication = new Publication();
                    publication.setId(resultSet.getInt("publications_id"));
                    publicationComment.setPublication(publication);
                }
                if (!resultSet.wasNull()) {
                    User user = new User();
                    user.setId(resultSet.getInt("user_id"));
                    UserInfo userInfo = new UserInfo();
                    userInfo.setUser(user);
                    publicationComment.setUserInfo(userInfo);
                }
            }
            return publicationComment;
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
     * Updates publication comment (Dao)
     *
     * @param publicationComment for update
     * @throws PersistentException if happens some error
     */
    @Override
    public void update(PublicationComment publicationComment) throws PersistentException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(UPDATE_SQL);
            statement.setString(1, publicationComment.getText());
            statement.setString(2, UtilDate.toString(publicationComment.getCommentDate()));
            if (publicationComment.getUserInfo().getUser() != null && publicationComment.getUserInfo().getUser().getId() != null) {
                statement.setInt(3, publicationComment.getUserInfo().getUser().getId());
            } else {
                statement.setNull(3, Types.INTEGER);
            }
            if (publicationComment.getPublication() != null && publicationComment.getPublication().getId() != null) {
                statement.setInt(4, publicationComment.getPublication().getId());
            } else {
                statement.setNull(4, Types.INTEGER);
            }
            statement.setInt(5, publicationComment.getId());
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
     * Deletes publication comment (Dao)
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

    /**
     * Method for reads all publication comments by publication id
     *
     * @param publicId for publication
     * @return publication comment list
     * @throws PersistentException if happens some error
     */
    @Override
    public List<PublicationComment> readByPublication(Integer publicId) throws PersistentException {
        String sql;
        if (publicId != null) {
            sql = READE_BY_PUBLICATION_SQL;
        } else {
            sql = READE_BY_PUBLICATION_IS_NULL_SQL;
        }
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(sql);
            if (publicId != null) {
                statement.setInt(1, publicId);
            }
            resultSet = statement.executeQuery();
            List<PublicationComment> publicationComments = new ArrayList<>();
            PublicationComment publicationComment;
            Publication publication = null;
            if (publicId != null) {
                publication = new Publication();
                publication.setId(publicId);
            }
            while ((resultSet.next())) {
                publicationComment = new PublicationComment();
                publicationComment.setId(resultSet.getInt("id"));
                publicationComment.setCommentDate(UtilDate.fromString(resultSet.getString("comment_data")));
                publicationComment.setText(resultSet.getString("text"));
                User user = new User();
                user.setId(resultSet.getInt("user_id"));
                UserInfo userInfo = new UserInfo();
                userInfo.setUser(user);
                publicationComment.setUserInfo(userInfo);
                publicationComment.setPublication(publication);
                publicationComments.add(publicationComment);
            }
            return publicationComments;
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
}
