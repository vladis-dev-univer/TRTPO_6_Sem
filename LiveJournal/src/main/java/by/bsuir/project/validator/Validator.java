package by.bsuir.project.validator;



import by.bsuir.project.entity.Entity;
import by.bsuir.project.exception.DateParseException;

import javax.servlet.http.HttpServletRequest;

public interface Validator <Type extends Entity> {

    Type validate(HttpServletRequest request) throws DateParseException;
}
