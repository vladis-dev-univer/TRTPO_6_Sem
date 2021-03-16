package by.bsuir.project.action.user.publication;

import by.bsuir.project.action.user.UserAction;
import by.bsuir.project.entity.Genre;
import by.bsuir.project.entity.Publication;
import by.bsuir.project.exception.DateParseException;
import by.bsuir.project.exception.IncorrectFormDataException;
import by.bsuir.project.exception.PersistentException;
import by.bsuir.project.service.PublicationService;
import by.bsuir.project.util.Constant;
import by.bsuir.project.validator.Validator;
import by.bsuir.project.validator.ValidatorFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PublicationSaveAction extends UserAction {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        Forward forward = new Forward(Constant.USER_PUBLIC_LIST);
        try {
            if (request.getSession(false).getAttribute(Constant.ERR_MESSAGE) != null) {
                request.getSession(false).removeAttribute(Constant.ERR_MESSAGE);
            }
            Validator<Publication> validator = ValidatorFactory.createValidator(Publication.class);
            assert validator != null;
            Publication publication = validator.validate(request);
            //saveGenre(publication);

            PublicationService publicationService = factory.getService(PublicationService.class);
            publicationService.save(publication);
            request.setAttribute(Constant.PUBLICATION, publication);
            request.setAttribute(Constant.AUTHORIZED_USER_ID, publication.getUser().getId());
            String strInfo = "publication set as attribute" + request.getRemoteAddr() +
                    " (" + request.getRemoteHost() + ":" + request.getRemotePort() + ")";
            logger.info(strInfo);

        } catch (NumberFormatException | IncorrectFormDataException | DateParseException e) {
            if (request.getSession(false).getAttribute(Constant.ERR_MESSAGE) != null) {
                request.getSession(false).removeAttribute(Constant.ERR_MESSAGE);
            }
            String str = getStringFromResourceBundle(request.getSession(), "correctAllFields");
            request.getSession(false).setAttribute(Constant.ERR_MESSAGE, str);
            String strInfo = "publication not set as attribute" + request.getRemoteAddr() +
                    " (" + request.getRemoteHost() + ":" + request.getRemotePort() + ")";
            logger.info(strInfo);
            forward = new Forward(Constant.USER_PUBLIC_EDIT);
        }
        return forward;
    }

    //TODO: continue publication genre
//    private void saveGenre(Publication publication) throws PersistentException {
//        GenreService genreService = factory.getService(GenreService.class);
//        Genre genre = new Genre();
//        genre.setTitle(publication.getGenre().getTitle());
//        genre.setId(publication.getGenre().getId());
//        genreService.save(genre);
//        publication.setGenre(genre);
//    }

}
