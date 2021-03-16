package by.bsuir.project.action.user.userinfo;

import by.bsuir.project.action.user.UserAction;
import by.bsuir.project.entity.UserInfo;
import by.bsuir.project.exception.DateParseException;
import by.bsuir.project.exception.IncorrectFormDataException;
import by.bsuir.project.exception.PersistentException;
import by.bsuir.project.service.UserInfoService;
import by.bsuir.project.util.Constant;
import by.bsuir.project.validator.Validator;
import by.bsuir.project.validator.ValidatorFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserInfoSaveAction extends UserAction {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        Forward forward = new Forward(Constant.USER_PROFILE);
        HttpSession session = request.getSession();
        UserInfoService userInfoService = factory.getService(UserInfoService.class);
        try {
            Validator<UserInfo> validator = ValidatorFactory.createValidator(UserInfo.class);
            assert validator != null;
            UserInfo userInfo = validator.validate(request);

            userInfoService.save(userInfo);
            session.setAttribute(Constant.USER_INFO, userInfo);
            String strInfo = "userInfo set as attribute" + request.getRemoteAddr() +
                    " (" + request.getRemoteHost() + ":" + request.getRemotePort() + ")";
            logger.info(strInfo);
        } catch (NumberFormatException | IncorrectFormDataException | DateParseException e) {
            request.setAttribute(Constant.ERR_MESSAGE, "Please make sure that you have entered your <b>all the fields</b> correctly.");
            String strInfo = "userInfo not set as attribute" + request.getRemoteAddr() +
                    " (" + request.getRemoteHost() + ":" + request.getRemotePort() + ")";
            logger.info(strInfo);
        }
        return forward;
    }
}
