package by.bsuir.project.dao.pool;

import by.bsuir.project.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public final class ConnectionPool {
    private final Logger logger = LogManager.getLogger(this.getClass());

    private String url;
    private String user;
    private String password;
    private int maxSize;
    private int checkConnectionTimeout;

    private static final ReentrantLock lock = new ReentrantLock();

    // Available connections
    private final BlockingQueue<PooledConnection> freeConnections = new LinkedBlockingQueue<>();
    // Used connections (we work with this connections)
    private final Set<PooledConnection> usedConnections = new ConcurrentSkipListSet<>();

    //ConnectionPool constructor.
    private ConnectionPool() {
    }

    private static ConnectionPool instance;

    public static ConnectionPool getInstance() {
        lock.lock();
        try {
            if (instance == null) {
                instance = new ConnectionPool();
            }
            return instance;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Gets connection.
     *
     * @return obtained connection instance
     * @throws PersistentException if limit of number of database connections is exceeded or
     *                           it is impossible to connect to a database
     */
    public Connection getConnection() throws PersistentException {
        PooledConnection connection = null;
        while (connection == null) {
            lock.lock();
            try {
                if (!freeConnections.isEmpty()) {
                    connection = freeConnections.take();
                    if (!connection.isValid(checkConnectionTimeout)) {
                        connection.getConnection().close();
                        connection = null;
                    }
                } else if (usedConnections.size() < maxSize) {
                    connection = createConnection();
                } else {
                    logger.error("The limit of number of database connections is exceeded");
                    throw new PersistentException();
                }
            } catch (SQLException | InterruptedException e) {
                logger.error("It is impossible to connect to a database or failed to close connection", e);
                Thread.currentThread().interrupt();
                throw new PersistentException(e);
            } finally {
                lock.unlock();
            }
        }
        usedConnections.add(connection);
        String strDebug = "Connection was received from pool. Current pool size: " + usedConnections.size()
                + " used connections; " + freeConnections.size() + " free connection";
        logger.debug(strDebug);
        return connection;
    }

    /**
     * Releases connection and returnees it to pool.
     *
     * @param connection - pooled connection
     * @throws PersistentException if free connection error occurs
     */
    public void freeConnection(PooledConnection connection) throws PersistentException {
        lock.lock();
        try {
            if (connection.isValid(checkConnectionTimeout)) {
                connection.clearWarnings();
                connection.setAutoCommit(true);
                usedConnections.remove(connection);
                freeConnections.put(connection);
                String strDebug = "Connection was returned into pool. Current pool size: " + usedConnections.size()
                        + " used connections; " + freeConnections.size() + " free connection";
                logger.debug(strDebug);
            }
        } catch (SQLException | InterruptedException e1) {
            logger.warn("It is impossible to return database connection into pool", e1);
            Thread.currentThread().interrupt();
            try {
                connection.getConnection().close();
            } catch (SQLException e2) {
                logger.error("Failed to close connection");
                throw new PersistentException(e2);
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * Initializes connection with url,user,password. Creates PooledConnection.
     *
     * @throws PersistentException if initializing error occurs
     */
    public void init(String driverClass, String url, String user, String password,
                     int startSize, int maxSize, int checkConnectionTimeout) throws PersistentException {
        lock.lock();
        try {
            destroy();
            Class.forName(driverClass);
            this.url = url;
            this.user = user;
            this.password = password;
            this.maxSize = maxSize;
            this.checkConnectionTimeout = checkConnectionTimeout;
            for (int counter = 0; counter < startSize; counter++) {
                freeConnections.put(createConnection());
            }
        } catch (ClassNotFoundException | SQLException | InterruptedException e) {
            logger.fatal("It is impossible to initialize connection pool", e);
            Thread.currentThread().interrupt();
            throw new PersistentException(e);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Creates connection.
     *
     * @return PooledConnection instance
     * @throws SQLException if there was an error creating a connection
     */
    private PooledConnection createConnection() throws SQLException {
        return new PooledConnection(DriverManager.getConnection(url, user, password));
    }

    /**
     * Destroys connections.
     */
    public void destroy() throws PersistentException {
        usedConnections.addAll(freeConnections);
        freeConnections.clear();
        for (PooledConnection connection : usedConnections) {
            lock.lock();
            try {
                connection.getConnection().close();
            } catch (SQLException e) {
                logger.error("Failed to close connection");
                throw new PersistentException(e);
            } finally {
                lock.unlock();
            }
        }
        usedConnections.clear();
    }

    @Override
    protected void finalize() throws Throwable {
        destroy();
    }

    /**
     * Provides count of available connections which can be picked from this pool now.
     *
     * @return available connection count
     */
    public int getFreeConnectionsSize() {
        return freeConnections.size();
    }
}