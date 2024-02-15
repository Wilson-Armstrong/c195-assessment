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
        this.setContactId(0);
        this.setContactName("");
        this.setEmail("");
    }

    public Contact (int contactId, String contactName, String email) {
        this.setContactId(contactId);
        this.setContactName(contactName);
        this.setEmail(email);
    }

    public String toString() { return this.getContactName(); }
}
