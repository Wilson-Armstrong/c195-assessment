package c195.c195assessment.model;

import java.time.Instant;
import java.time.LocalDateTime;

/**
 * Represents a user of the application, corresponding to a record in the 'users' database table.
 * Includes information about the user's ID, username, password, and audit fields such as creation and last update details.
 */
public class User {
    private int userId; // INT(10), Primary Key
    private String userName; // VARCHAR(50), UNIQUE
    private String password; // TEXT
    private LocalDateTime createDate; // DATETIME
    private String createdBy; // VARCHAR(50)
    private Instant lastUpdate; // TIMESTAMP
    private String lastUpdatedBy; // VARCHAR(50)

    /**
     * Retrieves the user's ID.
     *
     * @return The unique identifier for the user.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user's ID. This is typically assigned by the database and used internally.
     *
     * @param userId The unique identifier for the user.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Retrieves the username. Usernames are unique within the application.
     *
     * @return The username of the user.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the username. This must be unique across all users in the application.
     *
     * @param userName The new username for the user.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Retrieves the user's password. Note that in a real application, passwords should be stored securely, not in plain text.
     *
     * @return The password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password. Ensure that passwords are handled securely within the application.
     *
     * @param password The new password for the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Retrieves the date and time when the user's record was created in the database.
     *
     * @return The creation date and time of the user's record.
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * Sets the date and time when the user's record was created. This is typically handled by the database.
     *
     * @param createDate The creation date and time to be set for the user's record.
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * Retrieves the name of the user who created this user's record.
     *
     * @return The username of the creator.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the name of the user who created this user's record.
     *
     * @param createdBy The username of the creator.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Retrieves the timestamp of the last update made to the user's record.
     *
     * @return The timestamp of the last update.
     */
    public Instant getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Sets the timestamp of the last update made to the user's record. This is typically handled by the database.
     *
     * @param lastUpdate The timestamp of the last update to be set.
     */
    public void setLastUpdate(Instant lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Retrieves the name of the user who last updated this user's record.
     *
     * @return The username of the last updater.
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Sets the name of the user who last updated this user's record.
     *
     * @param lastUpdatedBy The username of the last updater.
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
}
