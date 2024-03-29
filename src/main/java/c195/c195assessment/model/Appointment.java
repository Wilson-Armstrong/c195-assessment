package c195.c195assessment.model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents an appointment, including details such as title, type, start and end times, and associated customer and
 * user IDs. This class mirrors a record in the "Appointments" table of the database.
 */
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

    /**
     * Constructs an empty {@code Appointment} object with default values.
     */
    public Appointment() {
        this.setAppointmentID(0);
        this.setTitle("");
        this.setDescription("");
        this.setLocation("");
        this.setType("");
        this.setStart(null);
        this.setEnd(null);
        this.setCreateDate(null);
        this.setCreatedBy("");
        this.setLastUpdate(null);
        this.setLastUpdatedBy("");
        this.setCustomerID(0);
        this.setUserID(0);
        this.setContactID(0);
    }

    /**
     * Constructs a partially filled {@code Appointment} object, primarily used when creating a new appointment before
     * creating the entry in the database. The {@code appointmentID}, {@code createDate}, and {@code lastUpdate} are
     * then generated by the database.
     *
     * @param title The appointment's title.
     * @param description The appointment's description.
     * @param location The appointment's location.
     * @param type The type of appointment.
     * @param start The start time and date of the appointment.
     * @param end The end time and date of the appointment.
     * @param createdBy The username of the user who created the appointment.
     * @param lastUpdatedBy The username of the user who last updated the appointment.
     * @param customerID The ID of the customer associated with the appointment.
     * @param userID The ID of the user associated with the appointment.
     * @param contactID The ID of the contact associated with the appointment.
     */
    public Appointment(String title, String description, String location, String type, LocalDateTime start,
                       LocalDateTime end, String createdBy, String lastUpdatedBy, int customerID, int userID,
                       int contactID) {
        this.setAppointmentID(0);
        this.setTitle(title);
        this.setDescription(description);
        this.setLocation(location);
        this.setType(type);
        this.setStart(start);
        this.setEnd(end);
        this.setCreateDate(null);
        this.setCreatedBy(createdBy);
        this.setLastUpdate(null);
        this.setLastUpdatedBy(lastUpdatedBy);
        this.setCustomerID(customerID);
        this.setUserID(userID);
        this.setContactID(contactID);
    }

    /**
     * Constructs an {@code Appointment} object with all fields specified. This constructor is typically used
     * when retrieving an existing appointment from the database.
     *
     * @param appointmentID The primary key ID of the appointment.
     * @param title The appointment's title.
     * @param description The appointment's description.
     * @param location The appointment's location.
     * @param type The type of appointment.
     * @param start The start time and date of the appointment.
     * @param end The end time and date of the appointment.
     * @param createDate The date and time the appointment was created.
     * @param createdBy The username of the user who created the appointment.
     * @param lastUpdate The last time and date the appointment was updated.
     * @param lastUpdatedBy The username of the user who last updated the appointment.
     * @param customerID The ID of the customer associated with the appointment.
     * @param userID The ID of the user associated with the appointment.
     * @param contactID The ID of the contact associated with the appointment.
     */
    public Appointment(int appointmentID, String title, String description, String location, String type,
                       LocalDateTime start, LocalDateTime end, LocalDateTime createDate, String createdBy,
                       Instant lastUpdate, String lastUpdatedBy, int customerID, int userID, int contactID) {
        this.setAppointmentID(appointmentID);
        this.setTitle(title);
        this.setDescription(description);
        this.setLocation(location);
        this.setType(type);
        this.setStart(start);
        this.setEnd(end);
        this.setCreateDate(createDate);
        this.setCreatedBy(createdBy);
        this.setLastUpdate(lastUpdate);
        this.setLastUpdatedBy(lastUpdatedBy);
        this.setCustomerID(customerID);
        this.setUserID(userID);
        this.setContactID(contactID);
    }

    /**
     * Copy constructor. Creates a deep copy of another {@code Appointment} object.
     *
     * @param other The {@code Appointment} object to copy.
     */
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

    /**
     * Returns the appointment ID.
     *
     * @return The unique ID of the appointment.
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * Sets the appointment ID.
     *
     * @param appointmentID The unique ID to be set for the appointment.
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**
     * Returns the title.
     *
     * @return The title of the appointment.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title.
     *
     * @param title The title to be set for the appointment.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the description.
     *
     * @return The description of the appointment.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     *
     * @param description The description to be set for the appointment.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the location.
     *
     * @return The location of the appointment.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location.
     *
     * @param location The location to be set for the appointment.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Returns the type.
     *
     * @return The type of the appointment.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type.
     *
     * @param type The type to be set for the appointment.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns start.
     *
     * @return The starting date and time of the appointment.
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Sets start.
     *
     * @param start The starting date and time of the appointment.
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
     * Returns end.
     *
     * @return The ending date and time of the appointment.
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * Sets end.
     *
     * @param end The ending date and time of the appointment.
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /**
     * Returns createDate.
     *
     * @return The appointment's time and date of creation.
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * Sets createDate.
     *
     * @param createDate The appointment's time and date of creation.
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * Returns createdBy.
     *
     * @return The name of the appointment's creator.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets createdBy.
     *
     * @param createdBy The name of the appointment's creator.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Returns lastUpdate.
     *
     * @return The date and time of the appointment's latest update.
     */
    public Instant getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Sets lastUpdate.
     *
     * @param lastUpdate The date and time of the appointment's latest update.
     */
    public void setLastUpdate(Instant lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Returns lastUpdatedBy.
     *
     * @return The name of the source of the appointment's latest update.
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Sets lastUpdatedBy.
     *
     * @param lastUpdatedBy The name of the source of the appointment's latest update.
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Returns customerID.
     *
     * @return The ID of the customer related to this appointment.
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Sets customerID.
     *
     * @param customerID The ID of the customer related to this appointment.
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * Returns userID.
     *
     * @return The ID of the user related to this appointment.
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Sets userID.
     *
     * @param userID The ID of the user related to this appointment.
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Returns contactID.
     *
     * @return The ID of the contact related to this appointment.
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * Sets the title.
     *
     * @param contactID The ID of the contact related to this appointment.
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * Indicates whether some other object is "equal to" this one, based on the {@code appointmentID}.
     *
     * @param otherObject The reference object with which to compare.
     * @return {@code true} if this object is the same as the {@code otherObject}; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;
        if (otherObject == null || getClass() != otherObject.getClass()) return false;
        Appointment that = (Appointment) otherObject;
        return appointmentID == that.appointmentID;
    }

    /**
     * Generates a hash code for an {@code Appointment} object. The hash code is based on the appointment ID, ensuring
     * that each appointment has a distinct hash code consistent with equals.
     *
     * @return An integer representing the hash code of the {@code Appointment} object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(appointmentID);
    }

    /**
     * Returns a string representation of the {@code Appointment} object. This method provides a readable form of the
     * appointment, including all its fields.
     *
     * @return A string representation of the {@code Appointment} object including all of its fields.
     */
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
