package by.bsuir.project.dao.transaction;

import by.bsuir.project.dao.pool.ConnectionPool;
import by.bsuir.project.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionFactoryImpl implements TransactionFactory {
    private final Logger logger = LogManager.getLogger(this.getClass());

    private final Connection connection;

    public TransactionFactoryImpl() throws PersistentException {
        connection = ConnectionPool.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            logger.error("It is impossible to turn off auto-committing for database connection", e);
            throw new PersistentException(e);
        }
    }

    @Override
    public Transaction createTransaction() {
        return new TransactionImpl(connection);
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            logger.error(e.toString());
        }
    }
}
