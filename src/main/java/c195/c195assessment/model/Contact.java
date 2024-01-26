package c195.c195assessment.model;

public class Contact {
    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private int contactId; // INT(10), Primary Key
    private String contactName; // VARCHAR(50)
    private String email; // VARCHAR(50)

    public Contact () {
        this.contactId = 0;
        this.contactName = "";
        this.email = "";
    }

    public Contact (int contactId, String contactName, String email) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.email = email;
    }

    public String toString() {
        return getContactName();
    }
}
