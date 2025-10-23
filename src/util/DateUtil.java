package util;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Utility class for date operations
 */
public class DateUtil {
    
    public static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static final DateTimeFormatter DB_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    /**
     * Converts LocalDate to SQL Date
     */
    public static Date toSqlDate(LocalDate localDate) {
        return localDate != null ? Date.valueOf(localDate) : null;
    }
    
    /**
     * Converts SQL Date to LocalDate
     */
    public static LocalDate toLocalDate(Date sqlDate) {
        return sqlDate != null ? sqlDate.toLocalDate() : null;
    }
    
    /**
     * Formats a LocalDate for display (dd-MM-yyyy)
     */
    public static String formatForDisplay(LocalDate date) {
        return date != null ? date.format(DISPLAY_FORMAT) : "";
    }
    
    /**
     * Formats a LocalDate for database (yyyy-MM-dd)
     */
    public static String formatForDB(LocalDate date) {
        return date != null ? date.format(DB_FORMAT) : null;
    }
    
    /**
     * Parses a date string in dd-MM-yyyy format
     */
    public static LocalDate parseDisplayDate(String dateStr) {
        try {
            return LocalDate.parse(dateStr, DISPLAY_FORMAT);
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Checks if a date is within the specified number of days from today
     */
    public static boolean isWithinDays(LocalDate date, int days) {
        if (date == null) {
            return false;
        }
        LocalDate today = LocalDate.now();
        long daysBetween = ChronoUnit.DAYS.between(today, date);
        return daysBetween >= 0 && daysBetween <= days;
    }
    
    /**
     * Checks if a date has passed
     */
    public static boolean isPast(LocalDate date) {
        return date != null && date.isBefore(LocalDate.now());
    }
    
    /**
     * Gets the current date
     */
    public static LocalDate getCurrentDate() {
        return LocalDate.now();
    }
    
    /**
     * Gets days until expiry (negative if expired)
     */
    public static long daysUntil(LocalDate date) {
        if (date == null) {
            return Long.MAX_VALUE;
        }
        return ChronoUnit.DAYS.between(LocalDate.now(), date);
    }
}
