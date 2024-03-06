package c195.c195assessment.model;

import java.time.Instant;
import java.time.LocalDateTime;

/**
 * Represents a customer in the system. Each customer object corresponds to a record in the 'customers' table of the
 * database, encapsulating customer details such as name, address, contact info, and related metadata like creation and
 * update timestamps.
 */
public class Customer {
    private int customerID; // INT(10), Primary Key
    private String customerName; // VARCHAR(50)
    private String address; // VARCHAR(100)
    private String postalCode; // VARCHAR(50)
    private String phone; // VARCHAR(50)
    private LocalDateTime createDate; // DATETIME
    private String createdBy; // VARCHAR(50)
    private Instant lastUpdate; // TIMESTAMP
    private String lastUpdatedBy; // VARCHAR(50)
    private int divisionID; // INT(10), Foreign Key (FirstLevelDivision)

    /**
     * Constructs an empty Customer object with default values for all fields.
     */
    public Customer() {
        this.setCustomerID(0);
        this.setCustomerName("");
        this.setAddress("");
        this.setPostalCode("");
        this.setPhone("");
        this.setCreateDate(null);
        this.setCreatedBy("");
        this.setLastUpdate(null);
        this.setLastUpdatedBy("");
        this.setDivisionID(0);
    }

    /**
     * Constructs a Customer object with specified values for each attribute. This constructor
     * is used when creating a new customer record with known details.
     *
     * @param customerID The unique identifier for the customer.
     * @param customerName The name of the customer.
     * @param address The address of the customer.
     * @param postalCode The postal code of the customer's address.
     * @param phone The contact phone number of the customer.
     * @param createDate The date and time the customer record was created.
     * @param createdBy The username of the user who created the customer record.
     * @param lastUpdate The timestamp of the last update to the customer record.
     * @param lastUpdatedBy The username of the user who last updated the customer record.
     * @param divisionID The identifier of the FirstLevelDivision associated with the customer.
     */
    public Customer(int customerID, String customerName, String address, String postalCode, String phone,
                    LocalDateTime createDate, String createdBy, Instant lastUpdate, String lastUpdatedBy,
                    int divisionID) {
        this.setCustomerID(customerID);
        this.setCustomerName(customerName);
        this.setAddress(address);
        this.setPostalCode(postalCode);
        this.setPhone(phone);
        this.setCreateDate(createDate);
        this.setCreatedBy(createdBy);
        this.setLastUpdate(lastUpdate);
        this.setLastUpdatedBy(lastUpdatedBy);
        this.setDivisionID(divisionID);
    }

    /**
     * Returns the customer ID.
     * @return The unique identifier for this customer.
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Sets the customer ID.
     * @param customerID The new unique identifier for this customer.
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * Returns the customer's name.
     * @return The name of the customer.
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Sets the customer's name.
     * @param customerName The new name of the customer.
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Returns the customer's address.
     * @return The address of the customer.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the customer's address.
     * @param address The address of the customer.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Returns the customer's postal code.
     * @return The postal code of the customer.
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets the customer's postal code.
     * @param postalCode The postal code of the customer.
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Returns the customer's phone number.
     * @return The phone number of the customer.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the customer's phone number.
     * @param phone The new phone number of the customer.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Returns the creation date of the customer's database entry.
     * @return The creation date of the entry.
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * Sets the creation date of the customer's database entry.
     * @param createDate The new creation date of the entry.
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * Returns the name of the source of the customer's database entry.
     * @return The name of the database entry's creator.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the name of the source of the customer's database entry.
     * @param createdBy The name of the database entry's creator.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Returns the date of the last update to the database entry.
     * @return The date of the last update to the database entry.
     */
    public Instant getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Sets the date of the last update to the database entry.
     * @param lastUpdate The date of the last update to the database entry.
     */
    public void setLastUpdate(Instant lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Returns the name of the latest updater of the customer's database entry.
     * @return The name of the latest updater.
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Sets the name of the latest updater of the customer's database entry.
     * @param lastUpdatedBy The name of the latest updater.
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Returns the ID of the division where the customer is located.
     * @return The ID of the division.
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * Sets the ID of the division where the customer is located.
     * @param divisionID The ID of the division.
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * Returns a string representation of the Customer object, typically the customer's name.
     * This can be useful for displaying the customer in user interfaces.
     *
     * @return The name of the customer.
     */
    @Override
    public String toString() {
        return getCustomerName();
    }
}
