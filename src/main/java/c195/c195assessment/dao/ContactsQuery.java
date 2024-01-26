package c195.c195assessment.dao;

import c195.c195assessment.model.Appointment;
import c195.c195assessment.model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class ContactsQuery {
    /** Read all entries from the table. */
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

    /** Read the entry of a single entry by its ID. */
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
