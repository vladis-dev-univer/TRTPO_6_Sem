package by.bsuir.project.action.user.publication;

import by.bsuir.project.action.user.UserAction;
import by.bsuir.project.entity.Genre;
import by.bsuir.project.entity.Publication;
import by.bsuir.project.entity.User;
import by.bsuir.project.exception.PersistentException;
import by.bsuir.project.service.GenreService;
import by.bsuir.project.service.PublicationService;
import by.bsuir.project.util.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class PublicationListAction extends UserAction {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        String currentPageParameter = request.getParameter(Constant.CURRENT_PAGE);
        Integer pageSize = (Integer) request.getAttribute(Constant.PAGE_SIZE);

        int currentPage = 1;
        if (currentPageParameter != null) {
            currentPage = Integer.parseInt(currentPageParameter);
        }
        if (pageSize == null) {
            pageSize = 2;
        }
        HttpSession session = request.getSession();
        try {
            int offset = pageSize * (currentPage - 1);
            User user = (User) session.getAttribute(Constant.AUTHORIZED_USER);
            PublicationService publicationService = factory.getService(PublicationService.class);
            List<Publication> publications = publicationService.findAllForPaginationByUserId(pageSize, offset, user.getId());
            int totalRecords = publicationService.getRowCountForPaginationByUserId(user.getId());
            int pages = totalRecords / pageSize;
            int lastPage = pages * pageSize < totalRecords ? pages + 1 : pages;
            request.setAttribute(Constant.PAGE_SIZE, pageSize);
            request.setAttribute(Constant.CURRENT_PAGE, currentPage);
            request.setAttribute(Constant.LAST_PAGE, lastPage);

            setGenre(publications);
            request.setAttribute(Constant.PUBLICATIONS, publications);
        } catch (NumberFormatException e) {
            logger.error(e.toString());
        }
        return null;
    }

    private void setGenre(List<Publication> publications) throws PersistentException {
        GenreService genreService = factory.getService(GenreService.class);
        for (Publication publication : publications) {
            Genre genre = genreService.findByIdentity(publication.getId());
            publication.setGenre(genre);
        }
    }
}
