package by.bsuir.project.dao.pool;

import by.bsuir.project.exception.PersistentException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ResourceBundle;

public class ConnectionPoolTest {
    ConnectionPool instance;

    @BeforeTest
    public void setUp() throws PersistentException {
        ResourceBundle resource = ResourceBundle.getBundle("test/database");
        String url = resource.getString("db.url");
        String user = resource.getString("db.user");
        String driver = resource.getString("db.driver");
        String password = resource.getString("db.password");
        int poolStartSize = Integer.parseInt(resource.getString("db.poolStartSize"));
        int maxSize = Integer.parseInt(resource.getString("db.poolMaxSize"));
        int checkConnectionTimeout = Integer.parseInt(resource.getString("db.poolCheckConnectionTimeOut"));
        instance = ConnectionPool.getInstance();
        instance.init(driver, url, user, password, poolStartSize, maxSize, checkConnectionTimeout);
    }

    @Test
    public void initExceptionTest() {
        ResourceBundle resource = ResourceBundle.getBundle("test/database_exp");
        String url = resource.getString("db.url");
        String user = resource.getString("db.user");
        String driver = resource.getString("db.driver");
        String password = resource.getString("db.password");
        int poolStartSize = Integer.parseInt(resource.getString("db.poolStartSize"));
        int maxSize = Integer.parseInt(resource.getString("db.poolMaxSize"));
        int checkConnectionTimeout = Integer.parseInt(resource.getString("db.poolCheckConnectionTimeOut"));
        Assert.assertThrows(PersistentException.class, () -> ConnectionPool.getInstance()
                .init(driver, url, user, password, poolStartSize, maxSize, checkConnectionTimeout));
    }

    @Test
    public void destroyTest() throws PersistentException {
        instance.destroy();
        Assert.assertEquals(instance.getFreeConnectionsSize(), 0);
    }

    @Test(expectedExceptions = PersistentException.class)
    public void availableConnectionsMaxPoolSizeExceptionText() throws PersistentException {
        instance.destroy();
        ResourceBundle resource = ResourceBundle.getBundle("test/database_exp");
        int maxSize = Integer.parseInt(resource.getString("db.poolMaxSize"));
        for (int i = 0; i < maxSize; i++) {
            instance.getConnection();
        }
    }

}