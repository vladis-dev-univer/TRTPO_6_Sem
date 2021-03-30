package by.bsuir.project.action.user.publication;

import by.bsuir.project.action.user.UserAction;
import by.bsuir.project.exception.PersistentException;
import by.bsuir.project.service.GenreService;
import by.bsuir.project.service.PublicationService;
import by.bsuir.project.util.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PublicationDeleteAction extends UserAction {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        Forward forward = new Forward(Constant.USER_PUBLIC_LIST);
        try {
            Integer publicationId = (Integer) request.getAttribute(Constant.PUBLICATION_ID);
            if (publicationId == null) {
                publicationId = Integer.parseInt(request.getParameter(Constant.PUBLICATION_ID));
            }
            PublicationService publicationService = factory.getService(PublicationService.class);
            publicationService.delete(publicationId);
            deleteGenre(publicationId);
        } catch (NumberFormatException e) {
            logger.error(e.toString());
        }
        return forward;
    }

    private void deleteGenre(Integer publicationId) throws PersistentException {
        GenreService genreService = factory.getService(GenreService.class);
        genreService.delete(publicationId);
    }
}
