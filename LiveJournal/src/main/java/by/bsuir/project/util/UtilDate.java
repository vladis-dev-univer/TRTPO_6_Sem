package by.bsuir.project.util;

import by.bsuir.project.exception.DateParseException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class UtilDate {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * private default constructor (help SonarLint)
     */
    private UtilDate() {
    }

    /**
     * Method to convert string to date
     *
     * @param date is the string to format the date
     * @return the formatted date
     * @throws DateParseException in case of error - an exception
     */
    public static Date fromString(String date) throws DateParseException {
        try {
            return DATE_FORMAT.parse(date);
        } catch (ParseException e) {
            throw new DateParseException(e.toString(), e.getErrorOffset());
        }
    }

    /**
     * Method to convert date to string
     *
     * @param date is the type of the Date to format the string
     * @return the formatted string
     */
    public static String toString(Date date) {
        return DATE_FORMAT.format(date);
    }
}
