package c195.c195assessment.model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;

/** This class is a POJO that represents a record in the table Appointments. */
public class Appointment {
    private int appointmentID; // INT(10), Primary Key
    private String title; // VARCHAR(50)
    private String description; // VARCHAR(50)
    private String location; // VARCHAR(50)
    private String type; // VARCHAR(50)
    private LocalDateTime start; // DATETIME
    private LocalDateTime end; // DATETIME
    private LocalDateTime createDate; // DATETIME
    private String createdBy; // VARCHAR(50)
    private Instant lastUpdate; // TIMESTAMP
    private String lastUpdatedBy; // VARCHAR(50)
    private int customerID; // INT(10), Foreign Key (Customer)
    private int userID; // INT(10), Foreign Key (User)
    private int contactID; // INT(10), Foreign Key (Contact)

    /** A constructor that allows for creating an empty Appointment object. */
    public Appointment() {
        this.appointmentID = 0;
        this.title = "";
        this.description = "";
        this.location = "";
        this.type = "";
        this.start = null;
        this.end = null;
        this.createDate = null;
        this.createdBy = "";
        this.lastUpdate = null;
        this.lastUpdatedBy = "";
        this.customerID = 0;
        this.userID = 0;
        this.contactID = 0;
    }

    /** This constructor function allows the attributes to be specified. The appointmentId, createdDate, and lastUpdate
     * attributes are determined by the database when the new entry is added to the appointments table. */
    public Appointment(String title, String description, String location, String type, LocalDateTime start,
                       LocalDateTime end, String createdBy, String lastUpdatedBy, int customerID, int userID,
                       int contactID) {
        this.appointmentID = 0;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.createDate = null;
        this.createdBy = createdBy;
        this.lastUpdate = null;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    /** This constructor overload is used when all information is already known, like when dealing with an entry in the
     * appointments table. */
    public Appointment(int appointmentID, String title, String description, String location, String type,
                       LocalDateTime start, LocalDateTime end, LocalDateTime createDate, String createdBy,
                       Instant lastUpdate, String lastUpdatedBy, int customerID, int userID, int contactID) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    /** This is a copy constructor. It accesses the attributes of the new object through setters and those of the old
     * object through getters. */
    public Appointment(Appointment other) {
        this.setAppointmentID(other.getAppointmentID());
        this.setTitle(other.getTitle());
        this.setDescription(other.getDescription());
        this.setLocation(other.getLocation());
        this.setType(other.getType());
        this.setStart(other.getStart());
        this.setEnd(other.getEnd());
        this.setCreateDate(other.getCreateDate());
        this.setCreatedBy(other.getCreatedBy());
        this.setLastUpdate(other.getLastUpdate());
        this.setLastUpdatedBy(other.getLastUpdatedBy());
        this.setCustomerID(other.getCustomerID());
        this.setUserID(other.getUserID());
        this.setContactID(other.getCustomerID());
    }

    /**  */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**  */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**  */
    public String getTitle() {
        return title;
    }

    /**  */
    public void setTitle(String title) {
        this.title = title;
    }

    /**  */
    public String getDescription() {
        return description;
    }

    /**  */
    public void setDescription(String description) {
        this.description = description;
    }

    /**  */
    public String getLocation() {
        return location;
    }

    /**  */
    public void setLocation(String location) {
        this.location = location;
    }

    /**  */
    public String getType() {
        return type;
    }

    /**  */
    public void setType(String type) {
        this.type = type;
    }

    /**  */
    public LocalDateTime getStart() {
        return start;
    }

    /**  */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**  */
    public LocalDateTime getEnd() {
        return end;
    }

    /**  */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /**  */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**  */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**  */
    public String getCreatedBy() {
        return createdBy;
    }

    /**  */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**  */
    public Instant getLastUpdate() {
        return lastUpdate;
    }

    /**  */
    public void setLastUpdate(Instant lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**  */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**  */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**  */
    public int getCustomerID() {
        return customerID;
    }

    /**  */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**  */
    public int getUserID() {
        return userID;
    }

    /**  */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**  */
    public int getContactID() {
        return contactID;
    }

    /**  */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**  */
    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;
        if (otherObject == null || getClass() != otherObject.getClass()) return false;
        Appointment that = (Appointment) otherObject;
        return appointmentID == that.appointmentID;
    }

    /**  */
    @Override
    public int hashCode() {
        return Objects.hash(appointmentID);
    }

    /**  */
    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentID=" + appointmentID +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", type='" + type + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", createDate=" + createDate +
                ", createdBy='" + createdBy + '\'' +
                ", lastUpdate=" + lastUpdate +
                ", lastUpdatedBy='" + lastUpdatedBy + '\'' +
                ", customerID=" + customerID +
                ", userID=" + userID +
                ", contactID=" + contactID +
                '}';
    }
}
