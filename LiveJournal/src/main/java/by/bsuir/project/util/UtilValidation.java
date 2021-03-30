package by.bsuir.project.util;

import by.bsuir.project.entity.Publication;
import by.bsuir.project.entity.Role;
import by.bsuir.project.entity.User;
import by.bsuir.project.entity.UserInfo;
import by.bsuir.project.exception.PersistentException;
import by.bsuir.project.service.PublicationService;
import by.bsuir.project.service.UserService;
import by.bsuir.project.service.factory.ServiceFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Locale;


public class UtilValidation {

    /**
     * private default constructor (help SonarLint)
     */
    private UtilValidation() {
        throw new IllegalStateException("UtilValidation class");
    }

    private static final String FILLED_REGEX = "^[а-яА-ЯёЁa-zA-Z][а-яА-ЯёЁa-zA-Z0-9-_\\.]{1,20}$";
    private static final String EMAIL_REGEX = "^[-\\w.]+@([A-z0-9][-A-z0-9]+\\.)+[A-z]{2,4}$";

    /**
     * Method to check all field
     *
     * @param values is the variable length arguments to be checked
     * @return true or false
     */
    public static boolean isFilled(String... values) {
        for (String value : values) {
            if (value.matches(FILLED_REGEX)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method to check email
     *
     * @param email string to be checked
     * @return true or false
     */
    public static boolean isCorrectEmail(String email) {
        return email.matches(EMAIL_REGEX);
    }

    /**
     * Method to get current Locale
     *
     * @param session  to get locale from an attribute if it is there
     * @param response to setting cookies
     * @return current language
     */
    public static Locale getCurrantLocale(HttpSession session, HttpServletResponse response) {
        Object localParameter = session.getAttribute("locale");
        Locale currentLang;
        if (session.getAttribute("locale") != null) {
            String string = String.valueOf(localParameter);
            String[] langParameters = string.split("_");
            currentLang = new Locale(langParameters[0], langParameters[1]);
        } else {
            currentLang = new Locale("en", "US");
        }
        session.setAttribute("locale", currentLang);
        Cookie localeCookie = new Cookie("locale", currentLang.getLanguage() + "_" + currentLang.getCountry());
        response.addCookie(localeCookie);
        return currentLang;
    }

    /**
     * MFirst method to delete admin user from UserInfo list
     *
     * @param usersInfo is the list
     * @param factory   to receive service service
     * @throws PersistentException if something went wrong
     */
    public static void deleteAdminFromUserInfoList(List<UserInfo> usersInfo, ServiceFactory factory) throws PersistentException {
        for (UserInfo userInfo : usersInfo) {
            UserService userService = factory.getService(UserService.class);
            User user = userService.findByIdentity(userInfo.getUser().getId());
            userInfo.setUser(user);
        }
        usersInfo.removeIf(userInfo -> userInfo.getUser().getRole().equals(Role.ADMINISTRATOR));
    }

    /**
     * Second method to delete admin user from UserInfo list, without factory
     * (if there is already a user inside the object)
     *
     * @param userInfos is the list
     */
    public static void deleteAdminFromUserInfoList(List<UserInfo> userInfos) {
        userInfos.removeIf(userInfo -> userInfo.getUser().getRole().equals(Role.ADMINISTRATOR));
    }

    /**
     * Method to delete admin from User list
     *
     * @param users is the list
     */
    public static void deleteAdminFromUserList(List<User> users) {
        users.removeIf(user -> user.getRole().equals(Role.ADMINISTRATOR));
    }

    /**
     * Method to save/update publication list into user
     *
     * @param user    who has a publication list
     * @param factory to receive service
     * @throws PersistentException if something went wrong
     */
    public static void updatePublicationListInUser(User user, ServiceFactory factory) throws PersistentException {
        user.getPublications().clear();
        PublicationService publicationService = factory.getService(PublicationService.class);
        List<Publication> publications = publicationService.findByUser(user.getId());
        for (Publication publication : publications) {
            user.getPublications().add(publication);
        }
    }

}
