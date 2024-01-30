package c195.c195assessment.controller;

import c195.c195assessment.dao.CustomersQuery;
import c195.c195assessment.model.Customer;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.Instant;
import java.time.LocalDateTime;

public class CustomerController {
    private ObservableList<Customer> allCustomers; // List of customers in the table

    // FXML elements
    public TableView<Customer> customerTableView;
    public TableColumn<Customer, Integer> customerIDColumn;
    public TableColumn<Customer, String> customerNameColumn;
    public TableColumn<Customer, String> addressColumn;
    public TableColumn<Customer, String> postalCodeColumn;
    public TableColumn<Customer, String> phoneColumn;
    public TableColumn<Customer, LocalDateTime> createDateColumn;
    public TableColumn<Customer, String> createdByColumn;
    public TableColumn<Customer, Instant> lastUpdateColumn;
    public TableColumn<Customer, String> lastUpdatedByColumn;
    public TableColumn<Customer, Integer> divisionIDColumn;
    public Button addCustomerButton;
    public Button modifyCustomerButton;
    public Button deleteCustomerButton;
    public Button returnToApptButton;

    // Getters and Setters
    public ObservableList<Customer> getAllCustomers() {
        return allCustomers;
    }

    public void setAllCustomers(ObservableList<Customer> allCustomers) {
        this.allCustomers = allCustomers;
    }

    @FXML
    public void initialize() {
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        createDateColumn.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        createdByColumn.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        lastUpdateColumn.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
        lastUpdatedByColumn.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
        divisionIDColumn.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
    }

    public void addCustomerButtonHandler() {

    }

    public void modifyCustomerButtonHandler() {

    }

    public void deleteCustomerButtonHandler() {

    }

    public void returnToApptButtonHandler() {

    }

    private void refreshCustomerTableView() {
        setAllCustomers(CustomersQuery.readAll());
        customerTableView.setItems(getAllCustomers());
        customerTableView.refresh();
    }
}
