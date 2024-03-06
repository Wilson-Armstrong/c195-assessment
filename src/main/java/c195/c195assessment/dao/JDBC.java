package c195.c195assessment.dao;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Handles the database connection by utilizing JDBC (Java Database Connectivity).
 * This class is responsible for establishing and closing the connection to the MySQL database used by the application.
 */
public abstract class JDBC {
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    private static final String userName = "sqlUser"; // Username
    private static final String password = "Passw0rd!"; // Password
    public static Connection connection;  // Connection Interface

    /**
     * Opens a connection to the database. This method initializes the JDBC driver, constructs the JDBC URL from the
     * provided details, and attempts to establish a connection to the database. It prints a message to the console
     * indicating the success or failure of the connection attempt.
     */
    public static void openConnection()
    {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            System.out.println("Connection successful!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }

    /**
     * Closes the current database connection. This method attempts to close the connection if it is not null and prints
     * a message to the console indicating whether the connection was successfully closed or if an error occurred.
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }
}
