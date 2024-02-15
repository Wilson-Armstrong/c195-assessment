package c195.c195assessment.model;

import java.time.Instant;
import java.time.LocalDateTime;

/**
 * Represents a first level division record in the 'first-level-divisions' table.
 * <p>
 *     This class is a POJO where an object corresponds to a record in the 'first-level-divisions' table. Its attributes
 *     represent the columns 'Division_ID', 'Division', 'Create_Date', 'Created_By', 'Last_Update', 'Last_Updated_By',
 *     and 'Country_ID'.
 * </p>
 *
 * @author Wilson Armstrong
 * @version 1.0
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
     * Retrieves the first level division's ID.
     * <p>
     *     This method returns the ID of the first level division as stored in the 'Division_ID' field.
     * </p>
     *
     * @return The ID of the first level division.
     */
    public int getDivisionID() {
        return divisionID;
    }

    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * Retrieves the first level division's name.
     * <p>
     *     This method returns the name of the first level division as stored in the 'Division' field.
     * </p>
     *
     * @return The name of the first level division.
     */
    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Retrieves the creation date of the first level division's record.
     * <p>
     *     This method returns the creation date of the first level division as stored in the 'Create_Date' field.
     * </p>
     *
     * @return The creation date of the first level division's record.
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * Retrieves the name of the creator of the first level division's creator.
     * <p>
     *     This method returns the creation date of the first level division as stored in the 'Create_Date' field.
     * </p>
     *
     * @return The creation date of the first level division's record.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Instant lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * Constructor for an empty FirstLevelDivision object.
     *      * <p>
     *      *     This constructor creates a FirstLevelDivision object with all attributes set to default values.
     *      * </p>
     * */
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
     * Constructor for a FirstLevelDivision object with specified attribute values.
     *      * <p>
     *      *     This constructor creates a FirstLevelDivision object with each attribute being set to a specified value.
     *      * </p>
     * */
    public FirstLevelDivision(int divisionID, String division, LocalDateTime createDate, String createdBy,
                              Instant lastUpdate, String lastUpdatedBy, int countryId) {
        this.setDivisionID(divisionID);
        this.setDivision(division);
        this.setCreateDate(createDate);
        this.setCreatedBy(createdBy);
        this.setLastUpdate(lastUpdate);
        this.setLastUpdatedBy(lastUpdatedBy);
        this.setCountryID(countryId);
    }

    public String toString() { return this.getDivision(); }
}
