package c195.c195assessment.model;

import java.time.Instant;
import java.time.LocalDateTime;

/**
 * Represents a country record from the 'countries' database table.
 * It encapsulates the country's ID, name, creation date, the user who created it, the last update timestamp, and the
 * user who last updated it.
 */
public class Country {
    private int countryID; // INT(10), Primary Key
    private String country; // VARCHAR(50)
    private LocalDateTime createDate; // DATETIME
    private String createdBy; // VARCHAR(50)
    private Instant lastUpdate; // TIMESTAMP
    private String lastUpdatedBy; // VARCHAR(50)

    /**
     * Default constructor for creating an empty Country object with default values.
     */
    public Country() {
        this.setCountryID(0);
        this.setCountry("");
        this.setCreateDate(null);
        this.setCreatedBy("");
        this.setLastUpdate(null);
        this.setLastUpdatedBy("");
    }

    /**
     * Constructor for creating a Country object with specified values.
     *
     * @param countryID The unique identifier of the country.
     * @param country The name of the country.
     * @param createDate The date and time when the record was created.
     * @param createdBy The username of the person who created the record.
     * @param lastUpdate The timestamp of the last update to the record.
     * @param lastUpdatedBy The username of the person who last updated the record.
     */
    public Country(int countryID, String country, LocalDateTime createDate, String createdBy, Instant lastUpdate,
                   String lastUpdatedBy) {
        this.setCountryID(countryID);
        this.setCountry(country);
        this.setCreateDate(createDate);
        this.setCreatedBy(createdBy);
        this.setLastUpdate(lastUpdate);
        this.setLastUpdatedBy(lastUpdatedBy);
    }

    /**
     * Returns the unique ID of the country.
     * @return the country ID.
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * Sets the unique ID of the country.
     * @param countryID the new ID of the country.
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * Returns the name of the country.
     * @return the country name.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the name of the country.
     * @param country the new name of the country.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Returns the creation date of the country's record.
     * @return the creation date.
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * Sets the creation date of the country's record.
     * @param createDate the new creation date.
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * Returns the username of the person who created the country's record.
     * @return the creator's username.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the username of the person who created the country's record.
     * @param createdBy the new creator's username.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Returns the timestamp of the last update to the country's record.
     * @return the last update timestamp.
     */
    public Instant getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Sets the timestamp of the last update to the country's record.
     * @param lastUpdate the new last update timestamp.
     */
    public void setLastUpdate(Instant lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Returns the username of the person who last updated the country's record.
     * @return the last updater's username.
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Sets the username of the person who last updated the country's record.
     * @param lastUpdatedBy the new last updater's username.
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Returns the country name as a representation of the country.
     * @return the country name.
     */
    public String toString() { return this.getCountry(); }

}
