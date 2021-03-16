package by.bsuir.project.action.admin.user;

import by.bsuir.project.action.admin.AdminAction;
import by.bsuir.project.entity.User;
import by.bsuir.project.entity.UserInfo;
import by.bsuir.project.exception.PersistentException;
import by.bsuir.project.service.UserService;
import by.bsuir.project.util.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AdminActiveAction extends AdminAction {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        Forward forward = new Forward(Constant.ADMIN_USER_LIST);
        try {
            Integer currentUserId = (Integer) request.getAttribute(Constant.CURRENT_USER_ID);
            if (currentUserId == null) {
                currentUserId = Integer.parseInt(request.getParameter(Constant.CURRENT_USER_ID));
            }
            UserService userService = factory.getService(UserService.class);
            User currentUser = userService.findByIdentity(currentUserId);
            currentUser.setActive(!currentUser.isActive());
            userService.saveUpdate(currentUser);
            if (request.getParameter(Constant.FROM_SEARCH).equalsIgnoreCase("yes")) {
                forward = new Forward(Constant.SEARCH_ADMIN_RESULT);
                @SuppressWarnings("unchecked")
                List<UserInfo> userInfos = (List<UserInfo>) request.getSession(false).getAttribute(Constant.ADMIN_USERS_INFO);
                for (UserInfo userInfo : userInfos) {
                    if (userInfo.getUser().getId().equals(currentUser.getId())) {
                        userInfo.setUser(currentUser);
                    }
                }
                request.getSession(false).setAttribute(Constant.ADMIN_USERS_INFO, userInfos);
            }
        } catch (NumberFormatException e) {
            logger.error(e.toString());
        }
        return forward;
    }
}
