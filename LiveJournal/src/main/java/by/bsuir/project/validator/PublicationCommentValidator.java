package by.bsuir.project.validator;

import by.bsuir.project.entity.Publication;
import by.bsuir.project.entity.PublicationComment;
import by.bsuir.project.exception.DateParseException;
import by.bsuir.project.exception.IncorrectFormDataException;
import by.bsuir.project.util.Constant;
import by.bsuir.project.util.UtilDate;

import javax.servlet.http.HttpServletRequest;

/**
 * Validator for publication comment entity
 *
 * @author Laikov Vlad
 * @version 1.0
 */
public class PublicationCommentValidator implements Validator<PublicationComment> {

    /**
     * Method for checking the entered data for a specific entity (in this case - PublicationComment)
     *
     * @param request to get data from the user
     * @return A certain entity (in this case - PublicationComment)
     * @throws IncorrectFormDataException if something went wrong
     */
    @Override
    public PublicationComment validate(HttpServletRequest request) throws IncorrectFormDataException {
        PublicationComment publicationComment = new PublicationComment();
        String parameter = request.getParameter(Constant.PUBLIC_COMMENT_DATE);
        if (parameter != null && !parameter.isEmpty()) {
            try {
                publicationComment.setCommentDate(UtilDate.fromString(parameter));
            } catch (DateParseException e) {
                e.printStackTrace();
            }
        } else {
            throw new IncorrectFormDataException("date_of_public", parameter);
        }

        parameter = request.getParameter(Constant.PUBLICATION_COMMENT_ID);
        if (parameter != null && !parameter.isEmpty()) {
            publicationComment.setId(Integer.parseInt(parameter));
        }

        parameter = request.getParameter(Constant.PUBLICATION_COMMENT_TEXT);
        if (parameter != null && !parameter.isEmpty()) {
            publicationComment.setText(parameter);
        } else {
            throw new IncorrectFormDataException("name", parameter);
        }

        parameter = request.getParameter(Constant.PUBLIC_ID);
        if (parameter != null && !parameter.isEmpty()) {
            Publication publication = new Publication();
            try {
                publication.setId(Integer.parseInt(parameter));
                publicationComment.setPublication(publication);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("genre", parameter);
            }
        }

        return publicationComment;
    }
}
