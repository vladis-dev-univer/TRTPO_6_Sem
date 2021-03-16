package by.bsuir.project.action;

import by.bsuir.project.entity.Role;
import by.bsuir.project.util.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Set;

public class ChangeLanguage extends Action {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public Set<Role> getAllowRoles() {
        return Collections.emptySet();
    }

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        String page = request.getParameter(Constant.PAGE);
        String language = request.getParameter(Constant.LANG);

        logger.debug(language);
        String locale = null;
        switch (language) {
            case "rus":
                locale = "ru_RU";
                logger.debug("locale = {}", locale);
                break;
            case "eng":
                locale = "en_US";
                logger.debug("locale = {}", locale);
                break;
            default:
                break;
        }

        session.setAttribute(Constant.LOCALE, locale);
        Cookie localeCookie = new Cookie(Constant.LOCALE, locale);
        response.addCookie(localeCookie);

        return new Forward(page);
    }

}
