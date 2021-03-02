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
import java.util.Collections;
import java.util.Date;
import java.util.Set;

public class RegisterAction extends Action {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public Set<Role> getAllowRoles() {
        return Collections.emptySet();
    }

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        String newLogin = request.getParameter(Constant.NEW_LOGIN);
        String newEmail = request.getParameter(Constant.NEW_EMAIL);
        String newPassword = request.getParameter(Constant.NEW_PASSWORD);
        String confirmPassword = request.getParameter(Constant.CONFIRM_PASSWORD);
        if (newLogin != null && newPassword != null && newEmail != null && confirmPassword != null) {
            UserService service = factory.getService(UserService.class);
            if (isCorrectInput(newLogin, newEmail, newPassword, confirmPassword)) {
                Date date = new Date(System.currentTimeMillis());
                User user = new User(newLogin, newPassword, newEmail, Role.USER, date, true);
                service.create(user);
                HttpSession session = request.getSession();
                session.setAttribute(Constant.AUTHORIZED_USER, user);
                String strInfo = "user " + user.getLogin() + " is logged in from " + request.getRemoteAddr() +
                        " (" + request.getRemoteHost() + ":" + request.getRemotePort() + ")";
                logger.info(strInfo);
                return new Forward(Constant.LOGOUT_LOGIN);
            } else {
                String message = getStringFromResourceBundle(request.getSession(), "correctAllFields");
                request.setAttribute(Constant.ERR_MESSAGE, message);
                String strInfo = "user unsuccessfully tried to register in from " + request.getRemoteAddr() +
                        " (" + request.getRemoteHost() + ":" + request.getRemotePort() + ")";
                logger.info(strInfo);
            }
        }
        return null;
    }

    private boolean isCorrectInput(String login, String email, String password, String rePass) {
        return UtilValidation.isCorrectEmail(email) && UtilValidation.isFilled(login, password, rePass);
    }
}
