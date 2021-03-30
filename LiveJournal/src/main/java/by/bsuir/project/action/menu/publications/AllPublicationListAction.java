package by.bsuir.project.action.menu.publications;

import by.bsuir.project.action.Action;
import by.bsuir.project.entity.Genre;
import by.bsuir.project.entity.Publication;
import by.bsuir.project.entity.Role;
import by.bsuir.project.entity.UserInfo;
import by.bsuir.project.exception.PersistentException;
import by.bsuir.project.service.GenreService;
import by.bsuir.project.service.PublicationService;
import by.bsuir.project.service.UserInfoService;
import by.bsuir.project.util.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class AllPublicationListAction extends Action {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public Set<Role> getAllowRoles() {
        return Collections.emptySet();
    }

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
        try {
            int offset = pageSize * (currentPage - 1);
            PublicationService publicationService = factory.getService(PublicationService.class);
            List<Publication> publications = publicationService.findAllForPagination(pageSize, offset);
            int totalRecords = publicationService.getRowCountForPagination();
            int pages = totalRecords / pageSize;
            int lastPage = pages * pageSize < totalRecords ? pages + 1 : pages;
            request.setAttribute(Constant.PAGE_SIZE, pageSize);
            request.setAttribute(Constant.CURRENT_PAGE, currentPage);
            request.setAttribute(Constant.LAST_PAGE, lastPage);

            UserInfoService userInfoService = factory.getService(UserInfoService.class);
            List<UserInfo> usersInfo = userInfoService.findAll();
            setPublicationIntoUser(usersInfo, publications);
            usersInfo.removeIf(userInfo -> userInfo.getUser().getPublications().isEmpty());
            request.setAttribute(Constant.USERS_INFO, usersInfo);
        } catch (NumberFormatException e) {
            logger.error(e.toString());
        }
        return null;
    }

    private void setPublicationIntoUser(List<UserInfo> usersInfo, List<Publication> publications) throws PersistentException {
        setGenre(publications);
        for (Publication publication : publications) {
            for (UserInfo userInfo : usersInfo) {
                if (userInfo.getUser().getId().equals(publication.getUser().getId())) {
                    userInfo.getUser().getPublications().add(publication);
                }
            }
        }
    }

    private void setGenre(List<Publication> publications) throws PersistentException {
        GenreService genreService = factory.getService(GenreService.class);
        for (Publication publication : publications) {
            Genre genre = genreService.findByIdentity(publication.getId());
            publication.setGenre(genre);
        }
    }
}
