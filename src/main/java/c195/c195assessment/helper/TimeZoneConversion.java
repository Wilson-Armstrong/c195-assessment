package c195.c195assessment.helper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Utility class for handling time zone conversions across different parts of the application.
 * It includes a general method to convert times between the time zones as well as methods for common conversions.
 */
public class TimeZoneConversion {

    /**
     * Converts a {@link LocalDateTime} from one time zone to another.
     * This generic method can be used to convert between any two specified time zones.
     *
     * @param dateTime The {@link LocalDateTime} to be converted.
     * @param fromZone The source {@link ZoneId} representing the time zone of the dateTime parameter.
     * @param toZone The target {@link ZoneId} representing the time zone to which the dateTime will be converted.
     * @return A {@link LocalDateTime} representing the equivalent time in the target time zone.
     */
    public static LocalDateTime convertTimeZone(LocalDateTime dateTime, ZoneId fromZone, ZoneId toZone) {
        ZonedDateTime zonedDateTime = dateTime.atZone(fromZone);
        ZonedDateTime convertedDateTime = zonedDateTime.withZoneSameInstant(toZone);
        return convertedDateTime.toLocalDateTime();
    }

    /**
     * Converts a {@link LocalDateTime} from UTC to the user's local time zone.
     * This method simplifies calls to {@code convertTimeZone} for the common case of converting from the database's UTC
     * time zone to the user's time zone.
     *
     * @param localDateTime The {@link LocalDateTime} in UTC to be converted to the user's local time zone.
     * @return A {@link LocalDateTime} representing the equivalent time in the user's local time zone.
     */
    public static LocalDateTime utcToLocal(LocalDateTime localDateTime) {
        ZoneId utcID = ZoneId.of("UTC");
        ZoneId localID = AppContext.getUserTimeZone().toZoneId();;
        return convertTimeZone(localDateTime, utcID, localID);
    }

    /**
     * Converts a {@link LocalDateTime} from the user's local time zone to UTC.
     * This method simplifies calls to {@code convertTimeZone} for the common case of converting from the user's time
     * zone to the database's UTC time zone.
     *
     * @param localDateTime The {@link LocalDateTime} in the user's local time zone to be converted to UTC.
     * @return A {@link LocalDateTime} representing the equivalent time in UTC.
     */
    public static LocalDateTime localToUtc(LocalDateTime localDateTime) {
        ZoneId utcID = ZoneId.of("UTC");
        ZoneId localID = AppContext.getUserTimeZone().toZoneId();;
        return convertTimeZone(localDateTime, localID, utcID);
    }
}
