package by.bsuir.project.action.admin.user;

import by.bsuir.project.action.admin.AdminAction;
import by.bsuir.project.entity.User;
import by.bsuir.project.entity.UserInfo;
import by.bsuir.project.exception.PersistentException;
import by.bsuir.project.service.UserService;
import by.bsuir.project.util.Constant;
import by.bsuir.project.util.UtilValidation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class AdminUserListAction extends AdminAction {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) {
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
            UserService userService = factory.getService(UserService.class);
            List<User> users = userService.findAllForPagination(pageSize, offset);
            int totalRecords = userService.getRowCountForPagination();
            int pages = totalRecords / pageSize;
            int lastPage = pages * pageSize < totalRecords ? pages + 1 : pages;
            request.setAttribute(Constant.PAGE_SIZE, pageSize);
            request.setAttribute(Constant.CURRENT_PAGE, currentPage);
            request.setAttribute(Constant.LAST_PAGE, lastPage);

//            UtilValidation.deleteAdminFromUserList(users);
//            request.getSession(false).setAttribute(Constant.ADMIN_USERS_INFO, getUserInfoList(users));
        } catch (NumberFormatException | PersistentException e) {
            logger.error(e.toString());
        }
        return null;
    }

//    private List<UserInfo> getUserInfoList(List<User> users) throws PersistentException {
//        List<UserInfo> userInfos = new ArrayList<>();
//        for (User user : users) {
//            UserInfoService userInfoService = factory.getService(UserInfoService.class);
//            UserInfo userInfo = userInfoService.findByUserId(user.getId());
//            if (userInfo == null) {
//                userInfo = new UserInfo();
//            }
//            userInfo.setUser(user);
//            userInfos.add(userInfo);
//        }
//        return userInfos;
//    }


}
