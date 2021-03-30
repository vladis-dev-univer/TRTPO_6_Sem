package by.bsuir.project.action.user.publication.comments;

import by.bsuir.project.action.user.UserAction;
import by.bsuir.project.entity.Publication;
import by.bsuir.project.entity.PublicationComment;
import by.bsuir.project.entity.UserInfo;
import by.bsuir.project.exception.PersistentException;
import by.bsuir.project.service.PublicationCommentService;
import by.bsuir.project.service.PublicationService;
import by.bsuir.project.service.UserInfoService;
import by.bsuir.project.util.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class EditPublicationCommentAction extends UserAction {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        try {
            String newComment = request.getParameter(Constant.NEW_COMMENT);
            if (newComment != null && newComment.equals("yes")) {
                request.getSession(false).removeAttribute(Constant.PUBLICATION_COMMENT);
            }
           Integer publicCommentId = (Integer) request.getAttribute(Constant.PUBLIC_COMMENT_ID);
            if (publicCommentId == null) {
                publicCommentId = Integer.parseInt(request.getParameter(Constant.PUBLIC_COMMENT_ID));
            }
            PublicationCommentService publicationCommentService = factory.getService(PublicationCommentService.class);
            PublicationComment publicationComment = publicationCommentService.findByIdentity(publicCommentId);
            if (publicationComment != null) {
                setPublicationAndUserInfo(publicationComment);
                request.getSession(false).setAttribute(Constant.PUBLICATION_COMMENT, publicationComment);
                request.setAttribute(Constant.PUBLIC_ID, publicationComment.getPublication().getId());
            }
        } catch (NumberFormatException e) {
            Date date = new Date(System.currentTimeMillis());
            request.setAttribute(Constant.PUBLICATION_COMMENT_DATE, date);
            request.setAttribute(Constant.PUBLIC_ID, request.getParameter(Constant.PUBLICATION_ID));
            logger.error(e.toString());
        }
        return null;
    }

    private void setPublicationAndUserInfo(PublicationComment publicationComment) throws PersistentException {
        UserInfoService userInfoService = factory.getService(UserInfoService.class);
        UserInfo userInfo = userInfoService.findByUserId(publicationComment.getUserInfo().getUser().getId());
        publicationComment.setUserInfo(userInfo);
        PublicationService publicationService = factory.getService(PublicationService.class);
        Publication publication = publicationService.findByIdentity(publicationComment.getPublication().getId());
        publicationComment.setPublication(publication);
    }

}
