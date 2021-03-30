package by.bsuir.project.action.menu.poets;

import by.bsuir.project.action.Action;
import by.bsuir.project.entity.*;
import by.bsuir.project.exception.PersistentException;
import by.bsuir.project.service.UserInfoService;
import by.bsuir.project.service.UserService;
import by.bsuir.project.util.Constant;
import by.bsuir.project.util.UtilValidation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class AllPoetListAction extends Action {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public Set<Role> getAllowRoles() {
        return Collections.emptySet();
    }

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        String currentPageParameter = request.getParameter(Constant.CURRENT_PAGE);
        Integer pageSize = (Integer) request.getAttribute(Constant.PAGE_SIZE);

        int currentPage = 1;
        if (currentPageParameter != null) {
            currentPage = Integer.parseInt(currentPageParameter);
        }
        if (pageSize == null) {
            pageSize = 3;
        }
        try {
            int offset = pageSize * (currentPage - 1);
            UserInfoService userInfoService = factory.getService(UserInfoService.class);
            List<UserInfo> usersInfo = userInfoService.findAllForPagination(pageSize, offset);
            int totalRecords = userInfoService.getRowCountForPagination();
            int pages = totalRecords / pageSize;
            int lastPage = pages * pageSize < totalRecords ? pages + 1 : pages;
            request.setAttribute(Constant.PAGE_SIZE, pageSize);
            request.setAttribute(Constant.CURRENT_PAGE, currentPage);
            request.setAttribute(Constant.LAST_PAGE, lastPage);

            /* TODO: can be done better (You need to update the level every time you add).*/
            for (UserInfo userInfo : usersInfo) {
                if (checkUserLevel(userInfo)) {
                    userInfoService.saveUpdate(userInfo);
                }
            }

            UtilValidation.deleteAdminFromUserInfoList(usersInfo, factory);
            request.setAttribute(Constant.USERS_INFO, usersInfo);
        } catch (NumberFormatException e) {
            logger.error(e.toString());
        }
        return null;
    }

    private boolean checkUserLevel(UserInfo userInfo) throws PersistentException {
        UserService userService = factory.getService(UserService.class);
        User user = userService.findByIdentity(userInfo.getUser().getId());
        UtilValidation.updatePublicationListInUser(user, factory);
        List<Publication> publications = user.getPublications();
        if (publications.isEmpty()) {
            userInfo.setLevel(Level.READER);
            return true;
        }
        if (publications.size() < 15 && publications.size() > 6) {
            userInfo.setLevel(Level.AMATEUR);
            return true;
        } else if (publications.size() <= 5) {
            userInfo.setLevel(Level.BEGINNER);
            return true;
        } else if (publications.size() >= 15) {
            userInfo.setLevel(Level.PROFESSIONAL);
            return true;
        }
        return false;
    }

}
