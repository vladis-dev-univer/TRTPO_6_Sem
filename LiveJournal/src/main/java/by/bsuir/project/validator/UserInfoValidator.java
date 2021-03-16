package by.bsuir.project.validator;

import by.bsuir.project.entity.Level;
import by.bsuir.project.entity.User;
import by.bsuir.project.entity.UserInfo;
import by.bsuir.project.exception.DateParseException;
import by.bsuir.project.exception.IncorrectFormDataException;
import by.bsuir.project.util.UtilDate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Validator for user info entity
 *
 * @author Laikov Vlad
 * @version 1.0
 */
public class UserInfoValidator implements Validator<UserInfo> {

    /**
     * Method for checking the entered data for a specific entity (in this case - UserInfo)
     *
     * @param request to get data from the user
     * @return A certain entity (in this case - UserInfo)
     * @throws IncorrectFormDataException if something went wrong
     */
    @Override
    public UserInfo validate(HttpServletRequest request) throws IncorrectFormDataException {
        UserInfo userInfo = new UserInfo();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("authorizedUser");
        if (user != null) {
            try {
                userInfo.setUser(user);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("identity", user.getId().toString());
            }
        }
        UserInfo userInfoId = (UserInfo) session.getAttribute("userInfo");
        if(userInfoId != null){
            try {
                userInfo.setId(userInfoId.getId());
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("identity", userInfoId.getId().toString());
            }
        }
        String parameter = request.getParameter("name");
        if (parameter != null && !parameter.isEmpty()) {
            userInfo.setName(parameter);
        } else {
            throw new IncorrectFormDataException("name", parameter);
        }
        parameter = request.getParameter("surname");
        try {
            userInfo.setSurname(parameter);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new IncorrectFormDataException("surname", parameter);
        }
        parameter = request.getParameter("pseudonym");
        try {
            userInfo.setPseudonym(parameter);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new IncorrectFormDataException("pseudonym", parameter);
        }

        parameter = request.getParameter("levelName");
        parameter = convertLevelFromLocale(parameter);
        try {
            userInfo.setLevel(Level.fromString(parameter));
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new IncorrectFormDataException("level", parameter);
        }
        parameter = request.getParameter("date_of_birth");
        try {
            userInfo.setDateOfBirth(UtilDate.fromString(parameter));
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException | DateParseException e) {
            throw new IncorrectFormDataException("date_of_birth", parameter);
        }
        return userInfo;
    }

    /**
     * Method for convert level in Russian or English formats
     *
     * @param parameter received parameter
     * @return string user level
     */
    private String convertLevelFromLocale(String parameter) {
        switch (parameter) {
            case "Professional":
            case "Профессионал":
                return Level.PROFESSIONAL.getName();
            case "Beginner":
            case "Новичок":
                return Level.BEGINNER.getName();
            case "Amateur":
            case "Любитель":
                return Level.AMATEUR.getName();
            default:
                return Level.READER.getName();
        }
    }
}
