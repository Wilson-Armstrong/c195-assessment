package c195.c195assessment.model;

import java.time.Instant;
import java.time.LocalDateTime;

/**
 * Represents a country record in the 'countries' table.
 * <p>
 *     This class is a POJO where an object corresponds to a record in the 'countries' table. It includes the details
 *     country ID, country name, record creation date, record creator, and information of the latest update of the
 *     record.
 * </p>
 *
 * @author Wilson Armstrong
 * @version 1.0
 */
public class Country {
    private int countryId; // INT(10), Primary Key
    private String country; // VARCHAR(50)
    private LocalDateTime createDate; // DATETIME
    private String createdBy; // VARCHAR(50)
    private Instant lastUpdate; // TIMESTAMP
    private String lastUpdatedBy; // VARCHAR(50)

    /**
     * Retrieves the country's ID.
     * <p>
     *     This method returns the ID of the country as stored in the 'Country_ID' field.
     * </p>
     *
     * @return The ID of the country.
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Sets the ID of the country.
     * <p>
     * This method updates the ID of the country. It sets the 'Country_ID' field to the provided value.
     * </p>
     *
     * @param countryId The new ID of the country.
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * Retrieves the country's name.
     * <p>
     *     This method returns the name of the country as stored in the 'Country' field.
     * </p>
     *
     * @return The name of the country.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the name of the country.
     * <p>
     * This method updates the name of the country. It sets the 'Country' field to the provided value.
     * </p>
     *
     * @param country The new name of the country.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Retrieves the creation date of the country's record.
     * <p>
     *     This method returns the date when this country's record in the table was created as stored in the
     *     'Create_Date' field.
     * </p>
     *
     * @return The creation date of the record.
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * Sets the creation date of the record.
     * <p>
     * This method updates the date of the creation of the country's record. It sets the 'Create_Date' field to the
     * provided value.
     * </p>
     *
     * @param createDate The new date of the record's creation.
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * Retrieves the name of the creator.
     * <p>
     *     This method returns the name of the creator of the country's record as stored in the 'Created_By' field.
     * </p>
     *
     * @return The name of the record's creator.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the name of the creator.
     * <p>
     * This method updates the name of the creator of the country's record. It sets the 'Created_By' field to the
     * provided value.
     * </p>
     *
     * @param createdBy The new name of creator.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Retrieves the date of the latest update.
     * <p>
     *     This method returns the date when this country's record was last updated as stored in the
     *     'Last_Update' field.
     * </p>
     *
     * @return The date of the record's last update.
     */
    public Instant getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Sets the date of the latest update.
     * <p>
     * This method updates the date of the last update to the country's record. It sets the 'Last_Update' field to the
     * provided value.
     * </p>
     *
     * @param lastUpdate The new date of the latest update.
     */
    public void setLastUpdate(Instant lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Retrieves the name of the one who made the latest update to the country's record.
     * <p>
     *     This method returns the name of the person who made the last update to the country's record as stored in the
     *     'Last_Updated_By' field.
     * </p>
     *
     * @return The name of the latest updater.
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Sets the name of the latest updater.
     * <p>
     * This method updates the name of the last person to make an update to the country's record. It sets the
     * 'Last_Updated_By' field to the provided value.
     * </p>
     *
     * @param lastUpdatedBy The new name of the latest updater.
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Constructor for an empty Country object.
     * <p>
     *     This constructor creates a Country object with all attributes set to default values.
     * </p>
     */
    public Country() {
        this.setCountryId(0);
        this.setCountry("");
        this.setCreateDate(null);
        this.setCreatedBy("");
        this.setLastUpdate(null);
        this.setLastUpdatedBy("");
    }

    /**
     * Constructor for a Country object with specified attribute values.
     * <p>
     *     This constructor creates a Country object with each attribute being set to a specified value.
     * </p>
     * */
    public Country(int countryID, String country, LocalDateTime createDate, String createdBy, Instant lastUpdate,
                   String lastUpdatedBy) {
        this.setCountryId(countryID);
        this.setCountry(country);
        this.setCreateDate(createDate);
        this.setCreatedBy(createdBy);
        this.setLastUpdate(lastUpdate);
        this.setLastUpdatedBy(lastUpdatedBy);
    }

    public String toString() { return this.getCountry(); }

}
