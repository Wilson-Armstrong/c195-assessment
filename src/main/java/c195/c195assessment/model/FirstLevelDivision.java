package c195.c195assessment.model;

import java.time.Instant;
import java.time.LocalDateTime;

/**
 * Represents a first-level division, such as a state or province, as defined in the 'first_level_divisions' database
 * table.
 * This class encapsulates the details of a first-level division including its ID, name, creation date, creator,
 * last update timestamp, latest updater, and associated country ID.
 */
public class FirstLevelDivision {
    private int divisionID; // INT(10), Primary Key
    private String division; // VARCHAR(50)
    private LocalDateTime createDate; // DATETIME
    private String createdBy; // VARCHAR(50)
    private Instant lastUpdate; // TIMESTAMP
    private String lastUpdatedBy; // VARCHAR(50)
    private int countryID; // INT(10), Foreign Key (Country)

    /**
     * Constructs an empty {@code FirstLevelDivision} object with default values for all fields.
     */
    public FirstLevelDivision() {
        this.setDivisionID(0);
        this.setDivision("");
        this.setCreateDate(null);
        this.setCreatedBy("");
        this.setLastUpdate(null);
        this.setLastUpdatedBy("");
        this.setCountryID(0);
    }

    /**
     * Constructs a {@code FirstLevelDivision} object with specified values for each attribute,
     * facilitating the creation of a detailed first-level division record.
     *
     * @param divisionID The unique identifier of the first-level division.
     * @param division The name of the first-level division.
     * @param createDate The date and time when the division record was created.
     * @param createdBy The username of the user who created the division record.
     * @param lastUpdate The timestamp of the last update to the division record.
     * @param lastUpdatedBy The username of the user who last updated the division record.
     * @param countryID The identifier of the country to which the division belongs.
     */
    public FirstLevelDivision(int divisionID, String division, LocalDateTime createDate, String createdBy,
                              Instant lastUpdate, String lastUpdatedBy, int countryID) {
        this.setDivisionID(divisionID);
        this.setDivision(division);
        this.setCreateDate(createDate);
        this.setCreatedBy(createdBy);
        this.setLastUpdate(lastUpdate);
        this.setLastUpdatedBy(lastUpdatedBy);
        this.setCountryID(countryID);
    }

    /**
     * Retrieves the division ID.
     *
     * @return The unique identifier for this first-level division.
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * Sets the division ID.
     *
     * @param divisionID The unique identifier to set for this first-level division.
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * Retrieves the name of the first-level division.
     *
     * @return The name of the division.
     */
    public String getDivision() {
        return division;
    }

    /**
     * Sets the name of the first-level division.
     *
     * @param division The name to set for the division.
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Retrieves the creation date and time of the division's record.
     *
     * @return The LocalDateTime representing when the division was created.
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * Sets the creation date and time of the division's record.
     *
     * @param createDate The LocalDateTime to set as the creation date and time.
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * Retrieves the username of the user who created the division's record.
     *
     * @return The name of the user who created the record.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the username of the user who created the division's record.
     *
     * @param createdBy The username to set as the creator of the record.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Retrieves the timestamp of the last update to the division's record.
     *
     * @return The Instant representing the last update time.
     */
    public Instant getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Sets the timestamp of the last update to the division's record.
     *
     * @param lastUpdate The Instant to set as the last update time.
     */
    public void setLastUpdate(Instant lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Retrieves the username of the user who last updated the division's record.
     *
     * @return The name of the user who last updated the record.
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Sets the username of the user who last updated the division's record.
     *
     * @param lastUpdatedBy The username to set as the last updater of the record.
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Retrieves the country ID associated with the first-level division.
     *
     * @return The ID of the country to which the division belongs.
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * Sets the country ID to associate with the first-level division.
     *
     * @param countryID The country ID to associate with the division.
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * Returns the division's name as a string representation of the {@code FirstLevelDivision} object.
     *
     * @return The name of the first-level division.
     */
    public String toString() { return this.getDivision(); }
}
