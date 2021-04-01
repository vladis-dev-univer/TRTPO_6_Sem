package by.bsuir.project.service.impl;

import by.bsuir.project.dao.pool.ConnectionPool;
import by.bsuir.project.dao.transaction.TransactionFactoryImpl;
import by.bsuir.project.entity.Role;
import by.bsuir.project.entity.User;
import by.bsuir.project.exception.DateParseException;
import by.bsuir.project.exception.PersistentException;
import by.bsuir.project.service.UserService;
import by.bsuir.project.service.factory.ServiceFactory;
import by.bsuir.project.service.factory.ServiceFactoryImpl;
import by.bsuir.project.util.UtilDate;
import by.bsuir.project.util.UtilPassword;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;
import java.util.ResourceBundle;

public class UserServiceImplTest {
    private UserService userService;
    private User user;
    private User newUser;

    @BeforeSuite
    public void setUpBeforeSuite() throws PersistentException {
        ResourceBundle resource = ResourceBundle.getBundle("test/database");
        String url = resource.getString("db.url");
        String user = resource.getString("db.user");
        String driver = resource.getString("db.driver");
        String password = resource.getString("db.password");
        int poolStartSize = Integer.parseInt(resource.getString("db.poolStartSize"));
        int maxSize = Integer.parseInt(resource.getString("db.poolMaxSize"));
        int checkConnectionTimeout = Integer.parseInt(resource.getString("db.poolCheckConnectionTimeOut"));
        ConnectionPool.getInstance().init(driver, url, user, password, poolStartSize, maxSize, checkConnectionTimeout);
    }

    @BeforeClass
    public void setUpBeforeClass() throws PersistentException {
        ServiceFactory serviceFactory = new ServiceFactoryImpl(new TransactionFactoryImpl());
        userService = serviceFactory.getService(UserService.class);
    }

    @BeforeTest
    public void createUser() throws DateParseException {
        user = new User();
        user.setLogin("user4");
        user.setPassword(UtilPassword.hashPassword("4321"));
        user.setRole(Role.USER);
        user.setDateOfReg(UtilDate.fromString("2007-09-20"));
        user.setActive(true);
        user.setEmail("user4@mail.com");
        user.setId(4);
    }

    @BeforeTest
    public void createNewUser() throws DateParseException {
        newUser = new User();
        newUser.setLogin("Vlad");
        newUser.setPassword(UtilPassword.hashPassword("888"));
        newUser.setRole(Role.USER);
        newUser.setDateOfReg(UtilDate.fromString("2001-01-18"));
        newUser.setActive(true);
        newUser.setEmail("vlad4@mail.com");
    }

    @Test
    public void createInDatabaseNewUser() throws PersistentException {
        userService.create(newUser);
        User foundUser = userService.findByIdentity(newUser.getId());
        Assert.assertEquals(newUser, foundUser);
    }


    @Test
    public void deleteUser() throws PersistentException {
        userService.delete(newUser.getId());
        User foundUser = userService.findByIdentity(newUser.getId());
        Assert.assertNull(foundUser);
    }

    @Test
    public void findAllTest() throws PersistentException {
        List<User> users = userService.findAll();
        Assert.assertNotNull(users);
    }

    @Test
    public void saveUpdateTest() throws PersistentException {
        userService.saveUpdate(user);
        List<User> users = userService.findAll();
        Assert.assertTrue(users.contains(user));
    }

    @Test
    public void findByIdentityTest() throws PersistentException {
        User foundUser = userService.findByIdentity(user.getId());
        Assert.assertEquals(user, foundUser);
    }

    @Test
    public void findByLoginPassTest() throws PersistentException {
        User foundUser = userService.findByLoginAndPassword("user4", "4321");
        int foundUserId = foundUser.getId();
        Assert.assertEquals(foundUserId, 4);
    }

    @DataProvider(name = "loginAndPassToFind")
    public Object[] loginPass() {

        return new Object[][]{
                {"user2", "1two345"},
                {"user4", "4321"},
                {"user3", "1234five"}
        };
    }

    @Test(dataProvider = "loginAndPassToFind")
    public void findByLoginPassExceptionTest(String login, String password) throws PersistentException {
        Assert.assertNotNull(userService.findByLoginAndPassword(login, password));
    }
}