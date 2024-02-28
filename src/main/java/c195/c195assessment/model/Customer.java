package c195.c195assessment.model;

import java.time.Instant;
import java.time.LocalDateTime;

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
    private int divisionId; // INT(10), Foreign Key (FirstLevelDivision)

    /** This constructor is used to create an empty Customer object. */
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
        this.setDivisionId(0);
    }

    /** This constructor is used to specify all attributes of the Customer. */
    public Customer(int customerId, String customerName, String address, String postalCode, String phone, LocalDateTime createDate, String createdBy, Instant lastUpdate, String lastUpdatedBy, int divisionId) {
        this.customerID = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionId = divisionId;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

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

    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    @Override
    public String toString() {
        return getCustomerName();
    }
}
