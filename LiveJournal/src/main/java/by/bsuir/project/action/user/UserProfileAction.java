package by.bsuir.project.action.user;


import by.bsuir.project.action.AuthorizedUserAction;
import by.bsuir.project.entity.User;
import by.bsuir.project.entity.UserInfo;
import by.bsuir.project.exception.PersistentException;
import by.bsuir.project.service.UserInfoService;
import by.bsuir.project.util.Constant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserProfileAction extends AuthorizedUserAction {

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
//        HttpSession session = request.getSession();
//        UserInfoService userInfoService = factory.getService(UserInfoService.class);
//        User user = (User) session.getAttribute(Constant.AUTHORIZED_USER);
//        UserInfo userInfo = userInfoService.findByUserId(user.getId());
//        request.setAttribute("userInfo", userInfo);
        return null;
    }
}
