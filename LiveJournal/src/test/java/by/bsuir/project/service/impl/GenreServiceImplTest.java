package by.bsuir.project.service.impl;

import by.bsuir.project.dao.pool.ConnectionPool;
import by.bsuir.project.dao.transaction.TransactionFactoryImpl;
import by.bsuir.project.entity.Genre;
import by.bsuir.project.exception.PersistentException;
import by.bsuir.project.service.GenreService;
import by.bsuir.project.service.factory.ServiceFactory;
import by.bsuir.project.service.factory.ServiceFactoryImpl;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;
import java.util.ResourceBundle;


public class GenreServiceImplTest {
    private GenreService genreService;
    private Genre genre;

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
        genreService = serviceFactory.getService(GenreService.class);
    }

    @BeforeTest
    public void createUserInfo() {
        genre = new Genre();
        genre.setTitle("любовь");
        genre.setId(1);
    }

    @Test
    public void saveUpdateTest() throws PersistentException {
        genreService.save(genre);
        List<Genre> genres = genreService.findAll();
        Assert.assertTrue(genres.contains(genre));
    }

}