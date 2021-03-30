package by.bsuir.project.action.user.publication.comments;

import by.bsuir.project.action.user.UserAction;
import by.bsuir.project.entity.PublicationComment;
import by.bsuir.project.entity.User;
import by.bsuir.project.entity.UserInfo;
import by.bsuir.project.exception.DateParseException;
import by.bsuir.project.exception.IncorrectFormDataException;
import by.bsuir.project.exception.PersistentException;
import by.bsuir.project.service.PublicationCommentService;
import by.bsuir.project.service.UserInfoService;
import by.bsuir.project.util.Constant;
import by.bsuir.project.validator.Validator;
import by.bsuir.project.validator.ValidatorFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SavePublicationCommentAction extends UserAction {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        Forward forward = new Forward(Constant.MENU_PUBLIC_COMMENT);
        try {
            Validator<PublicationComment> validator = ValidatorFactory.createValidator(PublicationComment.class);
            assert validator != null;
            PublicationComment publicationComment = validator.validate(request);
            setUserInfoAndPublication(publicationComment, request);

            PublicationCommentService publicationCommentService = factory.getService(PublicationCommentService.class);
            publicationCommentService.save(publicationComment);
            request.getSession(false).setAttribute(Constant.PUBLIC_ID, publicationComment.getPublication().getId());
            String strInfo = "publication comment set as attribute" + request.getRemoteAddr() +
                    " (" + request.getRemoteHost() + ":" + request.getRemotePort() + ")";
            logger.info(strInfo);
        } catch (NumberFormatException | IncorrectFormDataException | DateParseException e) {
            String strInfo = "publication not set as attribute" + request.getRemoteAddr() +
                    " (" + request.getRemoteHost() + ":" + request.getRemotePort() + ")";
            logger.info(strInfo);
            return new Forward(Constant.MENU_PUBLIC_COMMENT_EDIT);
        }
        return forward;
    }

    private void setUserInfoAndPublication(PublicationComment publicationComment, HttpServletRequest request) throws PersistentException {
        User user = (User) request.getSession().getAttribute(Constant.AUTHORIZED_USER);
        UserInfoService userInfoService = factory.getService(UserInfoService.class);
        UserInfo userInfo = userInfoService.findByUserId(user.getId());
        publicationComment.setUserInfo(userInfo);
    }
}
