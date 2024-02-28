package c195.c195assessment.dao;

import c195.c195assessment.helper.AppContext;
import c195.c195assessment.model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

/** This class handles queries for the appointments table. */
public abstract class AppointmentsQuery {

    /** Create a new entry in the Appointments table. Returns the new entry's Appointment_ID. */
    public static int insert (String title, String description, String location, String type, LocalDateTime start,
                              LocalDateTime end, String createdBy, String lastUpdatedBy, int customerID, int userID,
                              int contactID) {
        String sql = "INSERT INTO APPOINTMENTS (Title, Description, Location, Type, Start, End, Create_Date, " +
                "Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                "VALUES(?, ?, ?, ?, ?, ?, NOW(), ?, NOW(), ?, ?, ?, ?)";
        int generatedID = -1;  // Will hold the ID value produced by the database for this appointment.

        try (PreparedStatement ps = JDBC.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // ps.setInt(, appointmentID);  // ID value is generated by the database
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setObject(5, start);
            ps.setObject(6, end);
            // ps.setObject(, createDate);  // Time of creation is determined by the database
            ps.setString(7, createdBy);
            // ps.setObject(, lastUpdate);  // Time of update is determined by the database
            ps.setString(8, lastUpdatedBy);
            ps.setInt(9, customerID);
            ps.setInt(10, userID);
            ps.setInt(11, contactID);

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating appointment failed, no row affected");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    generatedID = generatedKeys.getInt(1);
                }
                else {
                    throw new SQLException("Creating appointment failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return generatedID;
    }

    /** A wrapper for the other insert statement that will update the POJO's appointmentID after the insert. */
    public static int insert (Appointment newAppointment) {
        int newApptID = insert(newAppointment.getTitle(), newAppointment.getDescription(), newAppointment.getLocation(), newAppointment.getType(), newAppointment.getStart(), newAppointment.getEnd(), newAppointment.getCreatedBy(), newAppointment.getLastUpdatedBy(), newAppointment.getCustomerID(), newAppointment.getUserID(), newAppointment.getContactID());
        newAppointment.setAppointmentID(newApptID);
        return newApptID;
    }

    /** Read all entries from the table. */
    public static ObservableList<Appointment> readAll() {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Appointment appointment = new Appointment(
                        rs.getInt("Appointment_ID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getString("Type"),
                        rs.getTimestamp("Start").toLocalDateTime(),
                        rs.getTimestamp("End").toLocalDateTime(),
                        rs.getTimestamp("Create_Date").toLocalDateTime(),
                        rs.getString("Created_By"),
                        rs.getTimestamp("Last_Update").toInstant(),
                        rs.getString("Last_Updated_By"),
                        rs.getInt("Customer_ID"),
                        rs.getInt("User_ID"),
                        rs.getInt("Contact_ID")
                );
                appointments.add(appointment);
            }
        }
        catch (SQLException e) {
            System.out.println("Error retrieving appointments: " + e.getMessage());
        }
        return appointments;
    }

    /** Read an entry from the table by its Appointment_ID. */
    public static Appointment readByID(int appointmentID) {
        Appointment appointment = null;
        String sql = "Select * FROM appointments WHERE Appointment_ID = ?";

        try (PreparedStatement ps = JDBC.connection.prepareStatement(sql)) {
            ps.setInt(1, appointmentID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                appointment = new Appointment();
                appointment.setAppointmentID(rs.getInt("Appointment_ID"));
                appointment.setTitle(rs.getString("Title"));
                appointment.setDescription(rs.getString("Description"));
                appointment.setLocation(rs.getString("Location"));
                appointment.setType(rs.getString("Type"));
                appointment.setStart(rs.getTimestamp("Start").toLocalDateTime());
                appointment.setEnd(rs.getTimestamp("End").toLocalDateTime());
                appointment.setCreateDate(rs.getTimestamp("Create_Date").toLocalDateTime());
                appointment.setCreatedBy(rs.getString("Created_By"));
                appointment.setLastUpdate(rs.getTimestamp("Last_Update").toInstant());
                appointment.setLastUpdatedBy(rs.getString("Last_Updated_By"));
            }
        }
        catch (SQLException e) {
            System.out.println("Error retrieving appointment: " + e.getMessage());
        }

        return appointment;
    }

    /** Attempts to delete an entry from the table appointments by the entry's ID value. */
    public static boolean deleteByID(int appointmentID) {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";

        try (PreparedStatement ps = JDBC.connection.prepareStatement(sql)) {
            ps.setInt(1, appointmentID);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        }
        catch (SQLException e) {
            System.out.println("Error deleting appointment: " + e.getMessage());
            return false;
        }
    }

    public static boolean update(Appointment appointment) {
        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, "
                + "Last_Update = NOW(), Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? "
                + "WHERE Appointment_ID = ?";

        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);

            // Set the appointment attributes from the passed object
            ps.setString(1, appointment.getTitle());
            ps.setString(2, appointment.getDescription());
            ps.setString(3, appointment.getLocation());
            ps.setString(4, appointment.getType());
            ps.setObject(5, appointment.getStart());
            ps.setObject(6, appointment.getEnd());

            // Set the username of the user who is currently logged in
            String lastUpdatedBy = AppContext.getUser().getUserName();
            ps.setString(7, lastUpdatedBy);

            // Set the rest of the attributes
            ps.setInt(8, appointment.getCustomerID());
            ps.setInt(9, appointment.getUserID());
            ps.setInt(10, appointment.getContactID());

            // Where clause to specify which appointment to update
            ps.setInt(11, appointment.getAppointmentID());

            // Execute the update and check if it was successful
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error updating appointment: " + e.getMessage());
            return false;
        }
    }
}
