package c195.c195assessment.controller;

import c195.c195assessment.dao.CustomersQuery;
import c195.c195assessment.helper.Alerts;
import c195.c195assessment.helper.SceneSwitcher;
import c195.c195assessment.model.Customer;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

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
        // Setting up cellValueFactory and formats
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        divisionIDColumn.setCellValueFactory(new PropertyValueFactory<>("divisionId"));

        // Reading customers from database
        refreshCustomerTableView();

        // Adding a listener that will refresh the table view whenever the window is focused
        Platform.runLater(() -> {
            Stage stage = (Stage) customerTableView.getScene().getWindow();
            stage.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    refreshCustomerTableView();
                }
            });
        });
    }

    public void addCustomerButtonHandler(ActionEvent actionEvent) {
        SceneSwitcher.switchScene(actionEvent, "/c195/c195assessment/fxml/addAppointment.fxml");
    }

    public void modifyCustomerButtonHandler(ActionEvent actionEvent) {
        // Ensure that a customer from the TableView is selected
        Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            Alerts.showAlert("No Selected Customer", "Please select a customer from the table.");
            return;
        }

        // Switch to modifyCustomer.fxml while passing the selected customer to its controller
        SceneSwitcher.switchSceneWithInfo(actionEvent, "/c195/c195assessment/fxml/modifyCustomer.fxml",
                (ModifyCustomerController controller) -> {
                    controller.setSelectedCustomer(selectedCustomer);
                    controller.setupFormWithCustomer();
                });
    }

    public void deleteCustomerButtonHandler() {

    }

    public void returnToApptButtonHandler() {

    }

    /** Query the database for an updated list of all customers and display tha list in the TableView. */
    private void refreshCustomerTableView() {
        setAllCustomers(CustomersQuery.readAll());
        customerTableView.setItems(getAllCustomers());
        customerTableView.refresh();
    }
}
