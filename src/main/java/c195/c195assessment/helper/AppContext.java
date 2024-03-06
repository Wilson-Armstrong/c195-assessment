package c195.c195assessment.helper;

import c195.c195assessment.model.User;

import java.util.Locale;
import java.util.TimeZone;

/**
 * This class provides a global context for the application, holding information about the current user's locale,
 * time zone, and login information.
 */
public class AppContext {
    private static final AppContext INSTANCE = new AppContext();
    private Locale userLocale;
    private TimeZone userTimeZone;
    private User user;


    /**
     * Initializes the user's locale and time zone to the system defaults.
     * User information is not set until successful login.
     */
    private AppContext() {
        userLocale = Locale.getDefault();
        // userLocale = Locale.FRANCE;

        userTimeZone = TimeZone.getDefault();
        // userTimeZone = TimeZone.getTimeZone("America/New York");
        // userTimeZone = TimeZone.getTimeZone("Europe/Paris");

        // User info is not set until the user successfully logs in.
        user = null;
    }

    /**
     * Returns the single instance of {@code AppContext}, creating it if it does not yet exist.
     *
     * @return The single, static instance of {@code AppContext}.
     */
    public static AppContext getInstance() {
        return INSTANCE;
    }

    /**
     * Retrieves the locale of the current user.
     *
     * @return The {@code Locale} object representing the user's locale.
     */
    public static Locale getUserLocale() {
        return getInstance().userLocale;
    }

    /**
     * Retrieves the time zone of the current user.
     *
     * @return The {@code TimeZone} object representing the user's time zone.
     */
    public static TimeZone getUserTimeZone() {
        return getInstance().userTimeZone;
    }

    /**
     * Updates the current user information. This method is only called upon user login.
     *
     * @param user The {@code User} object containing the user's login information. Passing {@code null}
     *             clears the current user information.
     */
    public static void setUser(User user) {
        getInstance().user = user;
        if (user != null) { System.out.println("Logged user: " + user.getUserName()); }
    }

    /**
     * Retrieves the current user information.
     *
     * @return The {@code User} object representing the current user, or {@code null} if no user is logged in.
     */
    public static User getUser() { return getInstance().user; }
}
