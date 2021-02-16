package by.bsuir.project.controller;

import by.bsuir.project.dao.pool.ConnectionPool;
import by.bsuir.project.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ResourceBundle;


public class DispatcherServlet extends HttpServlet {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public void init() {
        try {
            ResourceBundle resource = ResourceBundle.getBundle("database");
            ConnectionPool.getInstance().init(
                    resource.getString("db.driver"),
                    resource.getString("db.url"),
                    resource.getString("db.user"),
                    resource.getString("db.password"),
                    Integer.parseInt(resource.getString("db.poolStartSize")),
                    Integer.parseInt(resource.getString("db.poolMaxSize")),
                    Integer.parseInt(resource.getString("db.poolCheckConnectionTimeOut")));
        } catch (PersistentException e) {
            logger.error("It is impossible to initialize application", e);
            destroy();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) {

    }

}
