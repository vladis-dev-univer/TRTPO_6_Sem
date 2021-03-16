package by.bsuir.project.validator;

import by.bsuir.project.entity.Genre;
import by.bsuir.project.entity.Publication;
import by.bsuir.project.entity.User;
import by.bsuir.project.exception.DateParseException;
import by.bsuir.project.exception.IncorrectFormDataException;
import by.bsuir.project.util.UtilDate;

import javax.servlet.http.HttpServletRequest;

/**
 * Validator for publication entity
 *
 * @author Laikov Vlad
 * @version 1.0
 */
public class PublicationValidator implements Validator<Publication> {

    /**
     * Method for checking the entered data for a specific entity (in this case - Publication)
     *
     * @param request to get data from the user
     * @return A certain entity (in this case - Publication)
     * @throws IncorrectFormDataException if something went wrong
     */
    @Override
    public Publication validate(HttpServletRequest request) throws IncorrectFormDataException {
        Publication publication = new Publication();
        String parameter = request.getParameter("name");
        if (parameter != null && !parameter.isEmpty()) {
            publication.setName(parameter);
        } else {
            throw new IncorrectFormDataException("name", parameter);
        }

        parameter = request.getParameter("content");
        if (parameter != null && !parameter.isEmpty()) {
            publication.setContent(parameter);
        } else {
            throw new IncorrectFormDataException("content", parameter);
        }

        parameter = request.getParameter("publicationId");
        if (parameter != null && !parameter.isEmpty()) {
            publication.setId(Integer.parseInt(parameter));
        }

        parameter = request.getParameter("genre_title");
        setGenre(publication, parameter, request);

        setUser(publication, request);

        parameter = request.getParameter("public_date");
        if (parameter != null && !parameter.isEmpty()) {
            try {
                publication.setPublicDate(UtilDate.fromString(parameter));
            } catch (DateParseException e) {
                e.printStackTrace();
            }
        } else {
            throw new IncorrectFormDataException("date_of_public", parameter);
        }


        return publication;
    }

    /**
     * Released method to improve code minification in one method
     *
     * @param publication for user installation
     * @param request     for receive data from the user
     * @throws IncorrectFormDataException if something went wrong
     */
    private void setUser(Publication publication, HttpServletRequest request) throws IncorrectFormDataException {
        User user = (User) request.getSession().getAttribute("authorizedUser");
        if (user != null) {
            try {
                publication.setUser(user);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("authorizedUserPublicationId", user.getLogin());
            }
        }
    }

    /**
     * Released method to improve code minification in one method
     *
     * @param publication for user installation
     * @param parameter   received parameter
     * @param request     for more data from the user
     * @throws IncorrectFormDataException if something went wrong
     */
    private void setGenre(Publication publication, String parameter, HttpServletRequest request) throws IncorrectFormDataException {
        if (parameter != null && !parameter.isEmpty()) {
            Genre genre = new Genre();
            try {
                genre.setTitle(parameter);
                parameter = request.getParameter("genre_id");
                if (parameter != null && !parameter.isEmpty()) {
                    genre.setId(Integer.parseInt(parameter));
                }
                publication.setGenre(genre);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("genre", parameter);
            }
        }
    }
}