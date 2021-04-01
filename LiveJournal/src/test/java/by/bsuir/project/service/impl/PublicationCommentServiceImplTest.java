package by.bsuir.project.service.impl;

import by.bsuir.project.dao.pool.ConnectionPool;
import by.bsuir.project.dao.transaction.TransactionFactoryImpl;
import by.bsuir.project.entity.Publication;
import by.bsuir.project.entity.PublicationComment;
import by.bsuir.project.entity.User;
import by.bsuir.project.entity.UserInfo;
import by.bsuir.project.exception.DateParseException;
import by.bsuir.project.exception.PersistentException;
import by.bsuir.project.service.PublicationCommentService;
import by.bsuir.project.service.factory.ServiceFactory;
import by.bsuir.project.service.factory.ServiceFactoryImpl;
import by.bsuir.project.util.UtilDate;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.ResourceBundle;


public class PublicationCommentServiceImplTest {
    private PublicationCommentService publicationCommentService;
    private PublicationComment publicationComment;

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
        publicationCommentService = serviceFactory.getService(PublicationCommentService.class);
    }

    @BeforeTest
    public void createUserInfo() throws DateParseException {
        publicationComment = new PublicationComment();
        publicationComment.setText("В стихе есть неуловимая гармония!");
        publicationComment.setCommentDate(UtilDate.fromString("2021-01-22"));
        Publication publication = new Publication();
        publication.setId(1);
        publicationComment.setPublication(publication);
        UserInfo userInfo = new UserInfo();
        User user = new User();
        user.setId(2);
        userInfo.setUser(user);
        publicationComment.setUserInfo(userInfo);
        publicationComment.setId(2);
    }

    @Test
    public void findByPublicationTest() throws PersistentException {
        Assert.assertNotNull(publicationCommentService.findByPublication(publicationComment.getPublication().getId()));
    }

    @DataProvider(name = "findByID")
    public Object[] loginPass() {

        return new Object[][]{
                {1},
                {2},
                {3}
        };
    }

    @Test(dataProvider = "findByID")
    public void findByIdTest(Integer id) throws PersistentException {
        Assert.assertNotNull(publicationCommentService.findByIdentity(id));
    }

    @Test
    public void saveUpdateTest() throws PersistentException {
        publicationCommentService.save(publicationComment);
        Assert.assertFalse(publicationCommentService.findByPublication(publicationComment.getPublication().getId()).isEmpty());
    }



}