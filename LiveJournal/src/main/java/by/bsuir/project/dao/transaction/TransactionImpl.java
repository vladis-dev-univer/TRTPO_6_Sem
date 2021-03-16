package by.bsuir.project.dao.transaction;


import by.bsuir.project.dao.Dao;
import by.bsuir.project.dao.PublicationDao;
import by.bsuir.project.dao.UserDao;
import by.bsuir.project.dao.UserInfoDao;
import by.bsuir.project.dao.database.BaseDaoImpl;
import by.bsuir.project.dao.database.PublicationDaoImpl;
import by.bsuir.project.dao.database.UserDaoImpl;
import by.bsuir.project.dao.database.UserInfoDaoImpl;
import by.bsuir.project.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TransactionImpl implements Transaction {
    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * This field is map for our entity classes
     */
    private static final Map<Class<? extends Dao<?>>, Class<? extends BaseDaoImpl>> classMap = new ConcurrentHashMap<>();

    //This static block for filling classMap
    static {
        classMap.put(UserDao.class, UserDaoImpl.class);
        classMap.put(UserInfoDao.class, UserInfoDaoImpl.class);
        classMap.put(PublicationDao.class, PublicationDaoImpl.class);
    }

    /**
     * This is our connection
     */
    private final Connection connection;


    /**
     * @param connection we should get this
     */
    public TransactionImpl(Connection connection) {
        this.connection = connection;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <Type extends Dao<?>> Type createDao(Class<Type> key) throws PersistentException {
        Class<? extends BaseDaoImpl> tempValue = classMap.get(key);
        if (tempValue != null) {
            try {
                BaseDaoImpl dao = tempValue.newInstance();
                dao.setConnection(connection);
                return (Type) dao;
            } catch (InstantiationException | IllegalAccessException e) {
                logger.error("It is impossible to create data access object", e);
                throw  new PersistentException(e);
            }
        }
        return null;
    }

    @Override
    public void commit() throws PersistentException {
        try {
            connection.commit();
        } catch (SQLException e) {
            logger.error("It is impossible to commit transaction", e);
            throw new PersistentException(e);
        }
    }

    @Override
    public void rollback() throws PersistentException {
        try {
            connection.rollback();
        } catch (SQLException e) {
            logger.error("It is impossible to rollback transaction", e);
            throw new PersistentException(e);
        }
    }
}
