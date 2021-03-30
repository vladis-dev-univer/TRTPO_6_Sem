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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class SearchPublicationResultAction extends Action {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public Set<Role> getAllowRoles() {
        return Collections.emptySet();
    }

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) {
        Forward forward = new Forward(Constant.SEARCH_PUBLICATION_RESULT_JSP, false);
        String search = request.getParameter(Constant.SEARCH);
        List<Publication> publications;
        List<UserInfo> usersInfo = new ArrayList<>();
        try {
            if (!search.equals("")) {
                PublicationService publicationService = factory.getService(PublicationService.class);
                UserInfoService userInfoService = factory.getService(UserInfoService.class);
                publications = publicationService.findByName(search);
                if (publications.isEmpty()) {
                    publications = checkUserPseudonym(search, publicationService, userInfoService);
                }

                for (Publication publication : publications) {
                    UserInfo userInfo = userInfoService.findByUserId(publication.getUser().getId());
                    if (!usersInfo.contains(userInfo)) {
                        usersInfo.add(userInfo);
                    }
                }
                setPublicationIntoUser(usersInfo, publications);

                request.getSession(false).setAttribute(Constant.SEARCH_USER_INFO, usersInfo);
            } else {
                return new Forward(Constant.MENU_PUBLICATION_LIST);
            }
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return forward;
    }

    private List<Publication> checkUserPseudonym(String search, PublicationService publicationService,
                                                 UserInfoService userInfoService) throws PersistentException {
        List<Publication> publications = new ArrayList<>();
        List<UserInfo> usersInfo = userInfoService.findByPseudonym(search);
        for (UserInfo userInfo : usersInfo) {
            publications = publicationService.findByUser(userInfo.getUser().getId());
        }
        return publications;
    }

    private void setPublicationIntoUser(List<UserInfo> usersInfo, List<Publication> publications) throws PersistentException {
        setGenre(publications);
        for (Publication publication : publications) {
            for (UserInfo userInfo : usersInfo) {
                if (userInfo.getUser().getId().equals(publication.getUser().getId()) &&
                        !userInfo.getUser().getPublications().contains(publication)) {
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
