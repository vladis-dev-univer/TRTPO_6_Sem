package by.bsuir.project.controller;

import by.bsuir.project.action.Action;
import by.bsuir.project.action.ActionManager;
import by.bsuir.project.action.ActionManagerFactory;
import by.bsuir.project.dao.pool.ConnectionPool;
import by.bsuir.project.dao.transaction.TransactionFactoryImpl;
import by.bsuir.project.exception.PersistentException;
import by.bsuir.project.service.factory.ServiceFactory;
import by.bsuir.project.service.factory.ServiceFactoryImpl;
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

    public ServiceFactory getFactory() throws PersistentException {
        return new ServiceFactoryImpl(new TransactionFactoryImpl());
    }

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

    private void process(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        Action action = (Action) request.getAttribute("action");
        try {
            ActionManager actionManager = ActionManagerFactory.getManager(getFactory());
            Action.Forward forward = actionManager.execute(action, request, response);
            actionManager.close();

            String requestedUri = request.getRequestURI();
            if (forward != null && forward.isRedirect()) {
                String redirectedUri = request.getContextPath() + forward.getForth();
                String strDebug = "Request for URI " + requestedUri + " id redirected to URI " + redirectedUri;
                logger.debug(strDebug);
                response.sendRedirect(redirectedUri);
            } else {
                String jspPage;
                if (forward != null) {
                    jspPage = forward.getForth();
                } else {
                    jspPage = action.getName() + ".jsp";
                }
                jspPage = "/WEB-INF/jsp/views" + jspPage;
                String strDebug = "Request for URI " + requestedUri + " is forwarded to JSP " + jspPage;
                logger.debug(strDebug);
                getServletContext().getRequestDispatcher(jspPage).forward(request, response);
            }
        } catch (PersistentException e) {
            logger.error("It is impossible to process request", e);
            request.setAttribute("error", "Data processing error");
            getServletContext().getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }
    }

}
