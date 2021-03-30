package by.bsuir.project.action.menu.poets;


import by.bsuir.project.action.Action;
import by.bsuir.project.entity.*;
import by.bsuir.project.exception.PersistentException;
import by.bsuir.project.service.UserInfoService;
import by.bsuir.project.service.UserService;
import by.bsuir.project.util.Constant;
import by.bsuir.project.util.UtilValidation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class SearchPoetResultAction extends Action {

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
        Forward forward = new Forward(Constant.SEARCH_POET_RESULT_JSP, false);
        String search = request.getParameter(Constant.SEARCH);
        if (search == null) {
            search = (String) request.getSession(false).getAttribute(Constant.SEARCH_POET);
        }
        request.getSession(false).setAttribute(Constant.SEARCH_POET, search);
        List<UserInfo> poets;
        int paginationChoice = 0; //1-level
        if (!search.equals("")) {
            int offset = pageSize * (currentPage - 1);
            UserInfoService userInfoService = factory.getService(UserInfoService.class);
            poets = userInfoService.findByName(search);
            if (poets.isEmpty()) {
                poets = userInfoService.findAllForPaginationByLevel(pageSize, offset, getLevelName(search, request));
                setPaginationAttributes(userInfoService, search, pageSize, currentPage, request);
                if (!poets.isEmpty()) {
                    for (UserInfo poet : poets) {
                        setLevelIntoPoets(poet);
                    }
                    paginationChoice = 1;
                } else {
                    poets = userInfoService.findByPseudonym(search);
                }
            }
            request.getSession(false).setAttribute(Constant.POETS, poets);
            request.setAttribute(Constant.PAGINATION_CHOICE, paginationChoice);
        } else {
            return new Forward(Constant.MENU_POET_LIST);
        }

        return forward;
    }

    private void setPaginationAttributes(UserInfoService userInfoService, String search, Integer pageSize,
                                         int currentPage, HttpServletRequest request) throws PersistentException {
        int totalRecords = userInfoService.getRowCountForPaginationByLevel(getLevelName(search, request));
        int pages = totalRecords / pageSize;
        int lastPage = pages * pageSize < totalRecords ? pages + 1 : pages;
        request.setAttribute(Constant.PAGE_SIZE, pageSize);
        request.setAttribute(Constant.CURRENT_PAGE, currentPage);
        request.setAttribute(Constant.LAST_PAGE, lastPage);
    }

    private String getLevelName(String search, HttpServletRequest request) {
        if (search.equalsIgnoreCase(getStringFromResourceBundle(request.getSession(), "levelBeginner.name"))
                || search.equalsIgnoreCase("Beginner")) {
            return String.valueOf(Level.BEGINNER.getLevelName());
        }
        if (search.equalsIgnoreCase(getStringFromResourceBundle(request.getSession(), "levelAmateur.name"))
                || search.equalsIgnoreCase("Amateur")) {
            return String.valueOf(Level.AMATEUR.getLevelName());
        }
        if (search.equalsIgnoreCase(getStringFromResourceBundle(request.getSession(), "levelProfessional.name"))
                || search.equalsIgnoreCase("Professional")) {
            return String.valueOf(Level.PROFESSIONAL.getLevelName());
        }
        if (search.equalsIgnoreCase(getStringFromResourceBundle(request.getSession(), "levelReader.name"))
                || search.equalsIgnoreCase("Reader")) {
            return String.valueOf(Level.READER.getLevelName());
        }
        return Constant.ERR_GET_LEVEL;
    }

    private void setLevelIntoPoets(UserInfo poet) throws PersistentException {
        UserService userService = factory.getService(UserService.class);
        User user = userService.findByIdentity(poet.getUser().getId());
        UtilValidation.updatePublicationListInUser(user, factory);
        List<Publication> publications = user.getPublications();
        if (publications.isEmpty()) {
            poet.setLevel(Level.READER);
            return;
        }
        if (publications.size() < 15 && publications.size() > 6) {
            poet.setLevel(Level.AMATEUR);
        } else if (publications.size() <= 5) {
            poet.setLevel(Level.BEGINNER);
        } else if (publications.size() >= 15) {
            poet.setLevel(Level.PROFESSIONAL);
        }
    }
}
