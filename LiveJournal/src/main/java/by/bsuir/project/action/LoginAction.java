package by.bsuir.project.action;

import by.bsuir.project.entity.Role;
import by.bsuir.project.entity.User;
import by.bsuir.project.exception.PersistentException;
import by.bsuir.project.service.UserService;
import by.bsuir.project.util.Constant;
import by.bsuir.project.util.UtilValidation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class LoginAction extends Action {
    private final Logger logger = LogManager.getLogger(this.getClass());

    private static final Map<Role, List<MenuItem>> menu = new ConcurrentHashMap<>();

    static {
        menu.put(Role.USER, new ArrayList<>(Collections.singletonList(
                new MenuItem(Constant.USER_PROFILE, Constant.PROFILE))));
        menu.put(Role.ADMINISTRATOR, new ArrayList<>(Collections.singletonList(
                new MenuItem(Constant.ADMIN_PROFILE, Constant.PROFILE))));
    }

    @Override
    public Set<Role> getAllowRoles() {
        return Collections.emptySet();
    }

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        String login = request.getParameter(Constant.LOGIN);
        String password = request.getParameter(Constant.PASSWORD);
        if (login != null && password != null) {
            UserService service = factory.getService(UserService.class);
            User user = service.findByLoginAndPassword(login, password);
            if (user != null) {
                //TODO:UtilValidation.updatePublicationListInUser(user, factory);
                HttpSession session = request.getSession();
                session.setAttribute(Constant.AUTHORIZED_USER, user);
                session.setAttribute(Constant.MENU, menu.get(user.getRole()));
                String strInfo = "user " + login + " is logged in from " + request.getRemoteAddr() +
                        " (" + request.getRemoteHost() + ":" + request.getRemotePort() + ")";
                logger.info(strInfo);
                return new Forward(Constant.INDEX);
            } else {
                String message = getStringFromResourceBundle(request.getSession(), "messageErrorLoginOrPassword");
                request.setAttribute(Constant.ERR_MESSAGE, message);
                String strInfo = "user " + login + " unsuccessfully tried to log in from " + request.getRemoteAddr() +
                        " (" + request.getRemoteHost() + ":" + request.getRemotePort() + ")";
                logger.warn(strInfo);
            }
        } else {
            String accessMessage = (String) request.getAttribute("securityMessage");
            if (accessMessage != null) {
                String message = getStringFromResourceBundle(request.getSession(), accessMessage);
                request.setAttribute(Constant.ERR_MESSAGE, message);
            }
        }
        return null;
    }
}
