package c195.c195assessment.helper;

import c195.c195assessment.model.User;

import java.util.Locale;
import java.util.TimeZone;

public class AppContext {
    private static AppContext instance = new AppContext();
    private Locale userLocale;
    private TimeZone userTimeZone;
    private User user;


    private AppContext() {
        userLocale = Locale.getDefault();
        // userLocale = Locale.FRANCE;
        userTimeZone = TimeZone.getDefault();
        // userTimeZone = TimeZone.getTimeZone("America/New York");
        // userTimeZone = TimeZone.getTimeZone("Europe/Paris");

        // User info is not set until the user successfully logs in.
        user = null;
    }

    public static AppContext getInstance() {
        return instance;
    }

    public Locale getUserLocale() {
        return userLocale;
    }

    public TimeZone getUserTimeZone() {
        return userTimeZone;
    }

    /** This function is called when the user successfully logs in. It allows the program to quickly reference the
     * user's information. */
    public static void setUser(User user) {
        instance.user = user;
        System.out.println("Logged user: " + instance.user.getUserName());
    }

    public User getUser() { return instance.user; }
}
