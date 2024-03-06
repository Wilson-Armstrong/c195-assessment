package c195.c195assessment.dao;

import c195.c195assessment.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Handles database queries related to the {@code users} table.
 * Supports operations to retrieve user details by username and to verify login credentials.
 */
public abstract class UsersQuery {

    /**
     * Retrieves a user record from the database based on the specified username.
     *
     * @param username The username of the user to retrieve.
     * @return A {@link User} object containing the details of the requested user if found, or {@code null} otherwise.
     */
    public static User readByUsername (String username) {
        User user = null;
        String sql = "SELECT * FROM users WHERE User_Name = ?";

        try (PreparedStatement ps = JDBC.connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt("User_ID"));
                user.setUserName(rs.getString("User_Name"));
                user.setPassword(rs.getString("Password"));
                user.setCreateDate(rs.getTimestamp("Create_Date").toLocalDateTime());
                user.setCreatedBy(rs.getString("Created_By"));
                user.setLastUpdate(rs.getTimestamp("Last_Update").toInstant());
                user.setLastUpdatedBy(rs.getString("Last_Updated_By"));
            }
        }
        catch (SQLException e) {
            System.out.println("Error retrieving user: " + e.getMessage());
        }

        return user;
    }

    /**
     * Verifies the login credentials by comparing the provided username and password with those in the database.
     *
     * @param username The username provided for login.
     * @param password The password provided for login.
     * @return {@code true} if the username and password match a record in the database, {@code false} otherwise.
     */
    public static boolean loginVerification(String username, String password) {
        User user = readByUsername(username);
        if (user == null) {
            return false;
        }
        return user.getPassword().equals(password);
    }
}
