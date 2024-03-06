package c195.c195assessment.dao;

import c195.c195assessment.model.Appointment;
import c195.c195assessment.model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Provides static methods for querying the {@code contacts} table in the database.
 * This class enables the retrieval of either all contacts or a single contact by its ID.
 */
public abstract class ContactsQuery {

    /**
     * Retrieves all contact records from the {@code contacts} table and returns them as an observable list.
     *
     * @return An {@link ObservableList} of {@link Contact} objects representing all contacts in the database.
     */
    public static ObservableList<Contact> readAll() {
        ObservableList<Contact> contacts = FXCollections.observableArrayList();
        String sql = "SELECT * FROM contacts";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Contact contact = new Contact(
                        rs.getInt("Contact_ID"),
                        rs.getString("Contact_Name"),
                        rs.getString("Email")
                );
                contacts.add(contact);
            }
        }
        catch (SQLException e) {
            System.out.println("Error retrieving contacts: " + e.getMessage());
        }
        return contacts;
    }

    /**
     * Retrieves a single contact record from the database by its ID.
     *
     * @param contactID The unique ID of the contact to retrieve.
     * @return A {@link Contact} object representing the retrieved contact, or {@code null} if no contact with the specified ID was found.
     */
    public static Contact readByID(int contactID) {
        String sql = "SELECT * FROM contacts WHERE Contact_ID = ?";

        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, contactID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Contact(
                        rs.getInt("Contact_ID"),
                        rs.getString("Contact_Name"),
                        rs.getString("Email"));
            }
        }
        catch (SQLException e) {
            System.out.println("Error retrieving contact: " + e.getMessage());
        }

        return null;
    }
}
