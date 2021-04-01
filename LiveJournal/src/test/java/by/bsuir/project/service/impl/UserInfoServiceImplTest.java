package by.bsuir.project.service.impl;

import by.bsuir.project.dao.pool.ConnectionPool;
import by.bsuir.project.dao.transaction.TransactionFactoryImpl;
import by.bsuir.project.entity.Level;
import by.bsuir.project.entity.User;
import by.bsuir.project.entity.UserInfo;
import by.bsuir.project.exception.DateParseException;
import by.bsuir.project.exception.PersistentException;
import by.bsuir.project.service.UserInfoService;
import by.bsuir.project.service.factory.ServiceFactory;
import by.bsuir.project.service.factory.ServiceFactoryImpl;
import by.bsuir.project.util.UtilDate;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;
import java.util.ResourceBundle;


public class UserInfoServiceImplTest {
    private UserInfoService userInfoService;
    private UserInfo userInfo;

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
        userInfoService = serviceFactory.getService(UserInfoService.class);
    }

    @BeforeTest
    public void createUserInfo() throws DateParseException {
        userInfo = new UserInfo();
        userInfo.setName("User4Name");
        userInfo.setSurname("User4Surname");
        userInfo.setPseudonym("user4Pseudonym");
        userInfo.setLevel(Level.BEGINNER);
        userInfo.setDateOfBirth(UtilDate.fromString("2004-09-03"));
        User user = new User();
        user.setId(4);
        userInfo.setUser(user);
        userInfo.setId(4);
    }

    @Test
    public void findAllTest() throws PersistentException {
        List<UserInfo> users = userInfoService.findAll();
        Assert.assertNotNull(users);
    }

    @Test
    public void saveUpdateTest() throws PersistentException {
        userInfoService.saveUpdate(userInfo);
        List<UserInfo> userInfos = userInfoService.findAll();
        Assert.assertTrue(userInfos.contains(userInfo));
    }

    @Test
    public void findByIdentityTest() throws PersistentException {
        UserInfo foundUserInfo = userInfoService.findByIdentity(userInfo.getId());
        Assert.assertEquals(userInfo, foundUserInfo);
    }

    @Test
    public void findByUserIdTest() throws PersistentException {
        UserInfo foundUserInfo = userInfoService.findByUserId(userInfo.getUser().getId());
        Assert.assertEquals(userInfo, foundUserInfo);
    }

    @DataProvider(name = "NameToFind")
    public Object[] name() {

        return new Object[][]{
                {"User2"},
                {"User4Name"},
                {"User3"}
        };
    }

    @Test(dataProvider = "NameToFind")
    public void findByNameTest(String name) throws PersistentException {
        List<UserInfo> foundUserInfo = userInfoService.findByName(name);
        if(foundUserInfo.contains(userInfo)){
            Assert.assertTrue(foundUserInfo.contains(userInfo));
        }
        Assert.assertNotNull(foundUserInfo);
    }

    @DataProvider(name = "PseudonymToFind")
    public Object[] pseudonymPass() {

        return new Object[][]{
                {"ser2"},
                {"user4Pseudonym"},
                {"user3"}
        };
    }

    @Test(dataProvider = "PseudonymToFind")
    public void findByPseudonymTest(String name) throws PersistentException {
        List<UserInfo> foundUserInfo = userInfoService.findByName(name);
        if(foundUserInfo.contains(userInfo)){
            Assert.assertTrue(foundUserInfo.contains(userInfo));
        }
        Assert.assertNotNull(foundUserInfo);
    }
}