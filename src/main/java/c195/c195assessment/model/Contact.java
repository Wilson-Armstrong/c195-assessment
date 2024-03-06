package c195.c195assessment.model;

/**
 * Represents a contact's information, encapsulating contact ID, name, and email.
 * This class is intended to model a record from the contacts table of the database.
 */
public class Contact {
    private int contactId; // INT(10), Primary Key
    private String contactName; // VARCHAR(50)
    private String email; // VARCHAR(50)

    /**
     * Constructs an empty {@code Contact} object with default values.
     * This constructor initializes a new contact with default ID, name, and email values.
     */
    public Contact () {
        this.setContactId(0);
        this.setContactName("");
        this.setEmail("");
    }

    /**
     * Constructs a {@code Contact} object with specified values for ID, name, and email.
     * This constructor allows for creating a new contact with known attribute values.
     *
     * @param contactId The unique identifier for the contact.
     * @param contactName The name of the contact.
     * @param email The email address of the contact.
     */
    public Contact (int contactId, String contactName, String email) {
        this.setContactId(contactId);
        this.setContactName(contactName);
        this.setEmail(email);
    }

    /**
     * Retrieves the contact ID.
     *
     * @return The unique identifier for this contact.
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Sets the contact ID.
     *
     * @param contactId The unique identifier to be set for this contact.
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * Retrieves the contact's name.
     *
     * @return The name of this contact.
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Sets the contact's name.
     *
     * @param contactName The name to be set for this contact.
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Sets the contact's email address.
     *
     * @param email The email address to be set for this contact.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns a string representation of the {@code Contact} object.
     * This implementation returns the contact's name.
     *
     * @return A string that represents the contact's name.
     */
    public String toString() { return this.getContactName(); }
}
