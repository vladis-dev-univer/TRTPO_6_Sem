package by.bsuir.project.action.user.publication;

import by.bsuir.project.action.user.UserAction;
import by.bsuir.project.entity.Publication;
import by.bsuir.project.exception.PersistentException;
import by.bsuir.project.service.PublicationService;
import by.bsuir.project.util.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class PublicationEditAction extends UserAction {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        try {
            String newPublication = request.getParameter(Constant.NEW_PUBLICATION);
            if (newPublication != null && newPublication.equals("yes")) {
                request.getSession(false).removeAttribute(Constant.PUBLICATION);
            }
            Integer publicationId = (Integer) request.getAttribute(Constant.PUBLICATION_ID);
            if (publicationId == null) {
                publicationId = Integer.parseInt(request.getParameter(Constant.PUBLICATION_ID));
            }
            PublicationService publicationService = factory.getService(PublicationService.class);
            Publication publication = publicationService.findByIdentity(publicationId);
            if (publication != null) {
                //setGenre(publication);
                if (request.getSession(false).getAttribute(Constant.ERR_MESSAGE) != null) {
                    request.getSession(false).removeAttribute(Constant.ERR_MESSAGE);
                }
                request.getSession(false).setAttribute(Constant.PUBLICATION, publication);
            }
        } catch (NumberFormatException e) {
            Date date = new Date(System.currentTimeMillis());
            request.getSession(false).setAttribute(Constant.PUBLICATION_DATE, date);
            logger.error(e.toString());
        }
        return null;
    }

    //TODO: continue publication genre
//    private void setGenre(Publication publication) throws PersistentException {
//        GenreService genreService = factory.getService(GenreService.class);
//        Genre genre = genreService.findByIdentity(publication.getId());
//        publication.setGenre(genre);
//    }

}
