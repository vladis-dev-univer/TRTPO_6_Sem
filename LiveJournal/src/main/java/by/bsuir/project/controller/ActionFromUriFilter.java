package by.bsuir.project.controller;

import by.bsuir.project.action.*;
import by.bsuir.project.action.admin.AdminProfileAction;
import by.bsuir.project.action.admin.user.AdminActiveAction;
import by.bsuir.project.action.admin.user.AdminUserListAction;
import by.bsuir.project.action.menu.HomeAction;
import by.bsuir.project.action.user.UserProfileAction;
import by.bsuir.project.action.user.publication.PublicationEditAction;
import by.bsuir.project.action.user.publication.PublicationListAction;
import by.bsuir.project.action.user.publication.PublicationSaveAction;
import by.bsuir.project.action.user.userinfo.UserInfoEditAction;
import by.bsuir.project.action.user.userinfo.UserInfoSaveAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ActionFromUriFilter implements Filter {
    private final Logger logger = LogManager.getLogger(this.getClass());

    private static final Map<String, Class<? extends Action>> actions = new ConcurrentHashMap<>();

    static {
        actions.put("/", MainAction.class);
        actions.put("/index", MainAction.class);

        actions.put("/language", ChangeLanguage.class);
        actions.put("/login", LoginAction.class);
        actions.put("/logout", LogoutAction.class);
        actions.put("/register", RegisterAction.class);

        actions.put("/menu/home", HomeAction.class);

        actions.put("/admin/profile", AdminProfileAction.class);
        actions.put("/admin/user/list", AdminUserListAction.class);
        actions.put("/admin/user/active", AdminActiveAction.class);

        actions.put("/user/profile", UserProfileAction.class);
        actions.put("/user/edit", UserInfoEditAction.class);
        actions.put("/user/save", UserInfoSaveAction.class);

        actions.put("/user/public/list", PublicationListAction.class);
        actions.put("/user/public/edit", PublicationEditAction.class);
        actions.put("/user/public/save", PublicationSaveAction.class);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String contextPath = httpRequest.getContextPath();
            String uri = httpRequest.getRequestURI();
            String strDebug = "Starting of processing of request for URI " + uri;
            logger.debug(strDebug);
            int beginAction = contextPath.length();
            int endAction = uri.lastIndexOf('.');
            String actionName;
            if (endAction >= 0) {
                actionName = uri.substring(beginAction, endAction);
            } else {
                actionName = uri.substring(beginAction);
            }
            if (actionName.contains("/language")) {
                actionName = "/language";
            }
            Class<? extends Action> actionClass = actions.get(actionName);
            try {
                Action action = actionClass.newInstance();
                action.setName(actionName);
                httpRequest.setAttribute("action", action);
                chain.doFilter(request, response);
            } catch (InstantiationException | IllegalAccessException e) {
                logger.error("It is impossible to create action handler object", e);
                httpRequest.setAttribute("error", "The requested address " + uri + " could not be processed by the server");
                httpRequest.getServletContext().getRequestDispatcher("/WEB-INF/jsp/views/error.jsp").forward(request, response);
            }
        } else {
            logger.error("It is impossible to use HTTP filter");
            request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/views/error.jsp").forward(request, response);
        }
    }

    @Override
    public void init(FilterConfig config) {
        //Default empty method
    }

    @Override
    public void destroy() {
        //Default empty method
    }
}
