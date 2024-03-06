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

/**
 * Controller for the customer management view. Manages the display of customers in a table view, and handles navigation
 * to add, modify, and delete customer operations.
 */
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

    /**
     * Initializes the controller. Sets up the table columns and loads all customers from the database into the table
     * view. Also sets up a listener to refresh the table view whenever the window gains focus.
     */

    @FXML
    public void initialize() {
        // Setting up cellValueFactory and formats
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        divisionIDColumn.setCellValueFactory(new PropertyValueFactory<>("divisionID"));

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

    /**
     * Retrieves the observable list containing all customers currently loaded into the table view. This list is used to
     * populate the customer table view in the UI.
     *
     * @return An {@link ObservableList} of {@link Customer} objects representing all customers.
     */
    public ObservableList<Customer> getAllCustomers() {
        return allCustomers;
    }

    /**
     * Sets the observable list containing all customers to be displayed in the table view. This method allows updating
     * the list of customers shown in the UI, typically after retrieving updated data from the database.
     *
     * @param allCustomers An {@link ObservableList} of {@link Customer} objects to be displayed in the table view.
     */
    public void setAllCustomers(ObservableList<Customer> allCustomers) {
        this.allCustomers = allCustomers;
    }

    /**
     * Handles action on the "Add Customer" button. Switches the scene to the form for adding
     * a new customer.
     *
     * @param actionEvent The event that triggered this method.
     */
    public void addCustomerButtonHandler(ActionEvent actionEvent) {
        SceneSwitcher.switchScene(actionEvent, "/c195/c195assessment/fxml/addCustomer.fxml");
    }

    /**
     * Handles action on the "Modify Customer" button. Requires a customer to be selected from the table view.
     * If a customer is selected, switches the scene to the form for modifying the selected customer.
     *
     * @param actionEvent The event that triggered this method.
     */
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

    /**
     * Handles action on the "Delete Customer" button. Requires a customer to be selected from the table view.
     * If a customer is selected, prompts for confirmation and then attempts to delete the customer from the database.
     */
    public void deleteCustomerButtonHandler() {
        Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            String customerName = selectedCustomer.getCustomerName();
            int deleteIndex = selectedCustomer.getCustomerID();
            boolean deleteWorked = CustomersQuery.deleteByID(deleteIndex);
            if (!deleteWorked) { System.out.println("Failed to delete customer " + deleteIndex); }
            else { Alerts.showAlert("Customer Successfully Deleted", customerName +
                    " was removed from the database."); }
            refreshCustomerTableView();
        }
        else {
            Alerts.showAlert("No Selected Customer", "Please select a customer from the table.");
        }
    }

    /**
     * Handles action on the "Return to Appointments" button. Switches the scene back to the main appointment view.
     *
     * @param actionEvent The event that triggered this method.
     */
    public void returnToApptButtonHandler(ActionEvent actionEvent) {
        SceneSwitcher.switchScene(actionEvent, "/c195/c195assessment/fxml/appointmentMain.fxml");
    }

    /**
     * Queries the database for an updated list of all customers and refreshes the table view to display the updated
     * list.
     */
    private void refreshCustomerTableView() {
        setAllCustomers(CustomersQuery.readAll());
        customerTableView.setItems(getAllCustomers());
        customerTableView.refresh();
    }
}
