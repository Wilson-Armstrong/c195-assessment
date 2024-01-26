package c195.c195assessment.dao;

import c195.c195assessment.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This class handles queries for the users table. */
public abstract class UsersQuery {

    /** This function reads an entry from the users table by the entry's User_Name value. */
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

    /** The function takes a username and a password and checks if the combination is in the users table. */
    public static boolean loginVerification(String username, String password) {
        User user = readByUsername(username);
        if (user == null) {
            return false;
        }
        return user.getPassword().equals(password);
    }
}
