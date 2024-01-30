package c195.c195assessment.helper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimeZoneConversion {

    /**
     * Converts a LocalDateTime object from one time zone to another.
     *
     * @param dateTime The LocalDateTime object to convert.
     * @param fromZone The source time zone.
     * @param toZone The target time zone.
     * @return The equivalent LocalDateTime in the target time zone.
     */
    public static LocalDateTime convertTimeZone(LocalDateTime dateTime, ZoneId fromZone, ZoneId toZone) {
        ZonedDateTime zonedDateTime = dateTime.atZone(fromZone);
        ZonedDateTime convertedDateTime = zonedDateTime.withZoneSameInstant(toZone);
        return convertedDateTime.toLocalDateTime();
    }
}
