package by.bsuir.project.action.admin.user;

import by.bsuir.project.action.admin.AdminAction;
import by.bsuir.project.entity.User;
import by.bsuir.project.entity.UserInfo;
import by.bsuir.project.exception.PersistentException;
import by.bsuir.project.service.UserInfoService;
import by.bsuir.project.service.UserService;
import by.bsuir.project.util.Constant;
import by.bsuir.project.util.UtilValidation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class AdminSearchResultAction extends AdminAction {
    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        Forward forward = new Forward(Constant.SEARCH_ADMIN_RESULT_JSP, false);
        String search = request.getParameter(Constant.SEARCH);
        List<UserInfo> userInfos;
        List<User> users;
        if (search == null) {
            return null;
        }
        if (!search.equals("")) {
            UserInfoService userInfoService = factory.getService(UserInfoService.class);
            UserService userService = factory.getService(UserService.class);

            users = userService.findByLogin(search);
            if (!users.isEmpty()) {
                userInfos = getUserInfoList(users);
            } else {
                userInfos = userInfoService.findByName(search);
                for (UserInfo userInfo : userInfos) {
                    User user = userService.findByIdentity(userInfo.getUser().getId());
                    userInfo.setUser(user);
                }
            }
            UtilValidation.deleteAdminFromUserInfoList(userInfos);
            request.getSession(false).setAttribute(Constant.ADMIN_USERS_INFO, userInfos);
        } else {
            return new Forward(Constant.ADMIN_USER_LIST);
        }

        return forward;
    }

    private List<UserInfo> getUserInfoList(List<User> users) throws PersistentException {
        List<UserInfo> userInfos = new ArrayList<>();
        for (User user : users) {
            UserInfoService userInfoService = factory.getService(UserInfoService.class);
            UserInfo userInfo = userInfoService.findByUserId(user.getId());
            if (userInfo == null) {
                userInfo = new UserInfo();
            }
            userInfo.setUser(user);
            userInfos.add(userInfo);
        }
        return userInfos;
    }
}
