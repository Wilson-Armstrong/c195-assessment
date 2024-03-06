package c195.c195assessment.helper;

import java.time.LocalTime;
import java.time.ZoneId;

/**
 * Contains application-wide constants for easy access.
 * The constants relate to either the organization's hours of operation or the preferred formatting of time values in
 * the application's GUI.
 */
public class Constants {
    // The times and time zone describing the organization's hours of operation.
    public static final LocalTime OPEN_HOUR = LocalTime.of(8,0,0);
    public static final LocalTime CLOSE_HOUR = LocalTime.of(22,0,0);
    public static final ZoneId BUSINESS_TIME_ZONE = ZoneId.of("America/New_York");
    public static final String TIME_FORMAT = "hh:mm a";  // The formatting used in displaying times to the user.
}
