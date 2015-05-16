package evgeniy.stolyarov.ist.address.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Helper functions for handling dates.
 *
 * @author Marco Jakob
 */
public class SDateUtilE {

    /** The date pattern that is used for conversion. Change as you wish. */
    private static final String sDATE_PATTERNe = "dd.MM.yyyy";

    /** The date formatter. */
    private static final DateTimeFormatter sDATE_FORMATTERe =
            DateTimeFormatter.ofPattern(sDATE_PATTERNe);

    /**
     * Returns the given date as a well formatted String. The above defined
     * {@link SDateUtilE#sDATE_PATTERNe} is used.
     *
     * @param date the date to be returned as a string
     * @return formatted string
     */
    public static String sFormate(LocalDate date) {
        if (date == null) {
            return null;
        }
        return sDATE_FORMATTERe.format(date);
    }

    /**
     * Converts a String in the format of the defined {@link SDateUtilE#sDATE_PATTERNe}
     * to a {@link LocalDate} object.
     *
     * Returns null if the String could not be converted.
     *
     * @param dateString the date as String
     * @return the date object or null if it could not be converted
     */
    public static LocalDate sParsee(String dateString) {
        try {
            return sDATE_FORMATTERe.parse(dateString, LocalDate::from);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Checks the String whether it is a valid date.
     *
     * @param dateString
     * @return true if the String is a valid date
     */
    public static boolean sValidDateE(String dateString) {
        // Try to parse the String.
        return SDateUtilE.sParsee(dateString) != null;
    }
}