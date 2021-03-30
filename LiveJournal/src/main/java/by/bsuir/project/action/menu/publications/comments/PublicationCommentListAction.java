package by.bsuir.project.action.menu.publications.comments;

import by.bsuir.project.action.Action;
import by.bsuir.project.entity.Publication;
import by.bsuir.project.entity.PublicationComment;
import by.bsuir.project.entity.Role;
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
import java.util.List;

public class PublicationCommentListAction extends Action {
    private final Logger logger = LogManager.getLogger(this.getClass());

    public PublicationCommentListAction() {
        getAllowRoles().add(Role.USER);
        getAllowRoles().add(Role.ADMINISTRATOR);
    }

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        try {
            Integer publicationId = (Integer) request.getAttribute(Constant.PUBLICATION_ID);
            if (publicationId == null) {
                if (request.getParameter(Constant.PUBLICATION_ID) == null) {
                    publicationId = (Integer) request.getSession(false).getAttribute(Constant.PUBLIC_ID);
                } else {
                    publicationId = Integer.parseInt(request.getParameter(Constant.PUBLICATION_ID));
                }
            }
            request.getSession(false).setAttribute(Constant.PUBLIC_ID, publicationId);
            PublicationCommentService publicationCommentService = factory.getService(PublicationCommentService.class);
            List<PublicationComment> publicationComments = publicationCommentService.findByPublication(publicationId);
            setUserInfo(publicationComments);
            request.setAttribute(Constant.PUBLICATION_COMMENTS, publicationComments);
            PublicationService publicationService = factory.getService(PublicationService.class);
            Publication publication = publicationService.findByIdentity(publicationId);
            request.setAttribute(Constant.PUBLICATION, publication);
        } catch (NumberFormatException e) {
            logger.error(e.toString());
        }
        return null;
    }

    private void setUserInfo(List<PublicationComment> publicationComments) throws PersistentException {
        for (PublicationComment publicationComment : publicationComments) {
            UserInfoService userInfoService = factory.getService(UserInfoService.class);
            UserInfo userInfo = userInfoService.findByUserId(publicationComment.getUserInfo().getUser().getId());
            publicationComment.setUserInfo(userInfo);
        }
    }
}
