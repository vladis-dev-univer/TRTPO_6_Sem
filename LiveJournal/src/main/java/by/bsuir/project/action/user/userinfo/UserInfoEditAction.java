package by.bsuir.project.action.user.userinfo;

import by.bsuir.project.action.user.UserAction;
import by.bsuir.project.entity.Level;
import by.bsuir.project.entity.Publication;
import by.bsuir.project.entity.User;
import by.bsuir.project.entity.UserInfo;
import by.bsuir.project.exception.PersistentException;
import by.bsuir.project.service.UserInfoService;
import by.bsuir.project.util.Constant;
import by.bsuir.project.util.UtilValidation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class UserInfoEditAction extends UserAction {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        HttpSession session = request.getSession();
        try {
            User user = (User) session.getAttribute(Constant.AUTHORIZED_USER);
            UserInfoService userInfoService = factory.getService(UserInfoService.class);
            UserInfo userInfo = userInfoService.findByUserId(user.getId());
            if (userInfo != null) {
                if (checkUserLevel(request, userInfo, user)) {
                    userInfoService.saveUpdate(userInfo);
                }
                session.setAttribute(Constant.USER_INFO, userInfo);
            } else {
                String level = getStringFromResourceBundle(session,"levelReader.name");
                request.setAttribute(Constant.LEVEL, level);
            }
        } catch (NumberFormatException e) {
            logger.error(e.toString());
        }
        return null;
    }

    private boolean checkUserLevel(HttpServletRequest request, UserInfo userInfo, User user) throws PersistentException {
        UtilValidation.updatePublicationListInUser(user, factory);
        List<Publication> publications = user.getPublications();
        String level;
        if (publications.isEmpty()) {
            level = getStringFromResourceBundle(request.getSession(), "levelReader.name");
            request.setAttribute(Constant.LEVEL, level);
            userInfo.setLevel(Level.READER);
            return true;
        }
        if (publications.size() < 15 && publications.size() >= 6) {
            level = getStringFromResourceBundle(request.getSession(), "levelAmateur.name");
            request.setAttribute(Constant.LEVEL, level);
            userInfo.setLevel(Level.AMATEUR);
            return true;
        } else if (publications.size() <= 5) {
            level = getStringFromResourceBundle(request.getSession(), "levelBeginner.name");
            request.setAttribute(Constant.LEVEL, level);
            userInfo.setLevel(Level.BEGINNER);
            return true;
        } else if (publications.size() > 15) {
            level = getStringFromResourceBundle(request.getSession(), "levelProfessional.name");
            request.setAttribute(Constant.LEVEL, level);
            userInfo.setLevel(Level.PROFESSIONAL);
            return true;
        }
        return false;
    }
}
