package by.bsuir.project.action.menu.poets;

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

public class MoreInfoPoetAction extends Action {
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

        UserInfo userInfo = new UserInfo();
        Integer userInfoId = (Integer) request.getAttribute(Constant.USER_INFO_ID);
        if (userInfoId == null) {
            try {
                userInfoId = Integer.parseInt(request.getParameter(Constant.USER_INFO_ID));
                UserInfoService userInfoService = factory.getService(UserInfoService.class);
                userInfo = userInfoService.findByIdentity(userInfoId);
                request.getSession(false).setAttribute(Constant.USER_INFO, userInfo);
            } catch (NumberFormatException e) {
                logger.error(e.toString());
            }
        }
        if (userInfoId == null) {
            userInfo = (UserInfo) request.getSession(false).getAttribute(Constant.USER_INFO);
        }
        int offset = pageSize * (currentPage - 1);
        PublicationService publicationService = factory.getService(PublicationService.class);
        List<Publication> publications = publicationService.findAllForPaginationByUserId(pageSize, offset, userInfo.getUser().getId());
        int totalRecords = publicationService.getRowCountForPaginationByUserId(userInfo.getUser().getId());
        int pages = totalRecords / pageSize;
        int lastPage = pages * pageSize < totalRecords ? pages + 1 : pages;
        request.setAttribute(Constant.PAGE_SIZE, pageSize);
        request.setAttribute(Constant.CURRENT_PAGE, currentPage);
        request.setAttribute(Constant.LAST_PAGE, lastPage);
        if (publications != null) {
            setGenre(publications);
            request.getSession(false).setAttribute(Constant.PUBLICATIONS, publications);
        }
        return null;
    }

    private void setGenre(List<Publication> publications) throws PersistentException {
        for (Publication publication : publications) {
            GenreService genreService = factory.getService(GenreService.class);
            Genre genre = genreService.findByIdentity(publication.getId());
            publication.setGenre(genre);
        }
    }
}
