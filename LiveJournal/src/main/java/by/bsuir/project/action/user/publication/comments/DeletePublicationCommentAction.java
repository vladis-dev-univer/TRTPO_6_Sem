package by.bsuir.project.action.user.publication.comments;

import by.bsuir.project.action.user.UserAction;
import by.bsuir.project.entity.PublicationComment;
import by.bsuir.project.exception.PersistentException;
import by.bsuir.project.service.PublicationCommentService;
import by.bsuir.project.util.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeletePublicationCommentAction extends UserAction {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        Forward forward = new Forward(Constant.MENU_PUBLIC_COMMENT);
        try {
            Integer publicCommentId = (Integer) request.getAttribute(Constant.PUBLIC_COMMENT_ID);
            if (publicCommentId == null) {
                publicCommentId = Integer.parseInt(request.getParameter(Constant.PUBLIC_COMMENT_ID));
            }
            PublicationCommentService publicationCommentService = factory.getService(PublicationCommentService.class);
            PublicationComment publicationComment = publicationCommentService.findByIdentity(publicCommentId);
            request.getSession().removeAttribute(Constant.PUBLIC_ID);
            request.getSession(false).setAttribute(Constant.PUBLIC_ID, publicationComment.getPublication().getId());
            publicationCommentService.delete(publicCommentId);
        } catch (NumberFormatException e) {
            logger.error(e.toString());
        }
        return forward;
    }
}
