package c195.c195assessment.dao;

import c195.c195assessment.helper.AppContext;
import c195.c195assessment.model.Contact;
import c195.c195assessment.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This class handles the queries made to the table customers. */
public abstract class CustomersQuery {

    /** Gets all entries from the table. */
    public static ObservableList<Customer> readAll() {
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        String sql = "SELECT * FROM customers";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Customer customer = new Customer(
                        rs.getInt("Customer_ID"),
                        rs.getString("Customer_Name"),
                        rs.getString("Address"),
                        rs.getString("Postal_Code"),
                        rs.getString("Phone"),
                        rs.getTimestamp("Create_Date").toLocalDateTime(),
                        rs.getString("Created_By"),
                        rs.getTimestamp("Last_Update").toInstant(),
                        rs.getString("Last_Updated_By"),
                        rs.getInt("Division_ID")
                );
                customers.add(customer);
            }
        }
        catch (SQLException e) {
            System.out.println("Error retrieving customers: " + e.getMessage());
        }
        return customers;
    }

    /** Updates the information of the supplied Customer in the table. */
    public static boolean update(Customer customer) {
        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = NOW(), Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);

            // Set attributes from Customer object
            ps.setString(1, customer.getCustomerName());
            ps.setString(2, customer.getAddress());
            ps.setString(3, customer.getPostalCode());
            ps.setString(4, customer.getPhone());
            ps.setString(5, AppContext.getUser().getUserName());
            ps.setInt(6, customer.getDivisionId());
            ps.setInt(7, customer.getCustomerID());

            // Execute update and return the number of affected rows
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        }
        catch (SQLException e) {
            System.out.println("Error updating customer: " + e.getMessage());
            return false;
        }
    }
}
