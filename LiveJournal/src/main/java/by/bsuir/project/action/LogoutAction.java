package by.bsuir.project.action;

import by.bsuir.project.entity.User;
import by.bsuir.project.util.Constant;
import by.bsuir.project.util.UtilValidation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutAction extends AuthorizedUserAction {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) {
        UtilValidation.getCurrantLocale(request.getSession(), response);
        User user = getAuthorizedUser();
        String strInfo = "user " + user.getLogin() + " is logged out";
        logger.info(strInfo);
        request.getSession(false).invalidate();
        return new Forward(Constant.LOGOUT_LOGIN);
    }
}
