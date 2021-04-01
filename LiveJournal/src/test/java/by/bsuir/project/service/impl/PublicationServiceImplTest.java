package by.bsuir.project.service.impl;

import by.bsuir.project.dao.pool.ConnectionPool;
import by.bsuir.project.dao.transaction.TransactionFactoryImpl;
import by.bsuir.project.entity.Genre;
import by.bsuir.project.entity.Publication;
import by.bsuir.project.entity.User;
import by.bsuir.project.exception.DateParseException;
import by.bsuir.project.exception.PersistentException;
import by.bsuir.project.service.PublicationService;
import by.bsuir.project.service.factory.ServiceFactory;
import by.bsuir.project.service.factory.ServiceFactoryImpl;
import by.bsuir.project.util.UtilDate;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;
import java.util.ResourceBundle;

public class PublicationServiceImplTest {
    private PublicationService publicationService;
    private Publication publication;

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
        publicationService = serviceFactory.getService(PublicationService.class);
    }

    @BeforeTest
    public void createUserInfo() throws DateParseException {
        publication = new Publication();
        publication.setPublicDate(UtilDate.fromString("2021-02-02"));
        publication.setName("Стих");
        publication.setContent("Стих, стих, стих..." +
                "Стих, стих, стих...");
        User user = new User();
        user.setId(4);
        publication.setUser(user);
        Genre genre = new Genre();
        genre.setId(1);
        publication.setGenre(genre);
        publication.setId(1);
    }

    @Test
    public void findAllTest() throws PersistentException {
        List<Publication> publications = publicationService.findAll();
        Assert.assertNotNull(publications);
    }

    @Test
    public void saveUpdateTest() throws PersistentException {
        publicationService.save(publication);
        List<Publication> publications = publicationService.findAll();
        Assert.assertTrue(publications.contains(publication));
    }


}