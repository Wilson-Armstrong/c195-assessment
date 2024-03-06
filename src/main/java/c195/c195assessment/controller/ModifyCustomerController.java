package c195.c195assessment.controller;

import c195.c195assessment.dao.CountriesQuery;
import c195.c195assessment.dao.CustomersQuery;
import c195.c195assessment.dao.FirstLevelDivisionsQuery;
import c195.c195assessment.helper.FormHelper;
import c195.c195assessment.helper.SceneSwitcher;
import c195.c195assessment.model.Country;
import c195.c195assessment.model.Customer;
import c195.c195assessment.model.FirstLevelDivision;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.Comparator;

/**
 * Controller for the Modify Customer view, allowing users to update details of an existing customer.
 */
public class ModifyCustomerController {
    public Label customerMenuLabel;
    public TextField customerIDField;
    public TextField customerNameField;
    public TextField addressField;
    public TextField postalCodeField;
    public TextField phoneField;
    public ChoiceBox<Country> countryChoiceBox;
    public ChoiceBox<FirstLevelDivision> firstLevelDivisionChoiceBox;
    public Button confirmButton;
    public Button cancelButton;
    private Customer selectedCustomer;
    private ObservableList<Country> countries;
    private ObservableList<FirstLevelDivision> firstLevelDivisions;

    /**
     * Initializes the controller. Sets up choice boxes for countries and first level divisions based on database
     * entries, and sets a listener for the country choice box to filter divisions accordingly.
     */
    public void initialize() {
        // Getting information from the database
        this.setCountries(CountriesQuery.readAll());
        this.setFirstLevelDivisions(FirstLevelDivisionsQuery.readAll());

        // Alphabetizing the Countries and FLDs
        this.getCountries().sort(Comparator.comparing(Country::toString));
        this.getFirstLevelDivisions().sort(Comparator.comparing(FirstLevelDivision::toString));

        // Populating the options for the Country ChoiceBox
        countryChoiceBox.setItems(this.getCountries());

        // Setting listener for countryChoiceBox that will update the options in the FLD ChoiceBox.
        countryChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                updateFLDChoiceBox(newSelection.getCountryID());
            }
        });

    }

    /**
     * Gets the customer currently selected for modification.
     *
     * @return The {@link Customer} object selected for modification.
     */
    public Customer getSelectedCustomer() { return selectedCustomer; }

    /**
     * Sets the currently selected customer for modification. This customer's information is used to pre-populate
     * the form fields for editing.
     *
     * @param selectedCustomer The {@link Customer} object to be modified.
     */
    public void setSelectedCustomer(Customer selectedCustomer) { this.selectedCustomer = selectedCustomer; }

    /**
     * Retrieves the observable list of countries available for selection. This list is used
     * to populate the country choice box in the UI, allowing the user to select a country for the customer.
     *
     * @return An {@link ObservableList} of {@link Country} objects representing all available countries.
     */
    public ObservableList<Country> getCountries() { return countries; }

    /**
     * Sets the observable list of countries to be available for selection in the country choice box.
     * This method updates the list of countries based on data retrieved from the database.
     *
     * @param countries An {@link ObservableList} of {@link Country} objects.
     */
    public void setCountries(ObservableList<Country> countries) { this.countries = countries; }

    /**
     * Retrieves the observable list of first level divisions associated with the selected country.
     * This list is used to populate the first level division choice box in the UI, allowing the user to
     * select a division for the customer.
     *
     * @return An {@link ObservableList} of {@link FirstLevelDivision} objects representing the divisions associated with the selected country.
     */
    public ObservableList<FirstLevelDivision> getFirstLevelDivisions() { return firstLevelDivisions; }

    /**
     * Sets the observable list of first level divisions to be available for selection in the first level division choice box.
     * This method is typically called after selecting a country to update the divisions shown to the user accordingly.
     *
     * @param firstLevelDivisions An {@link ObservableList} of {@link FirstLevelDivision} objects.
     */
    public void setFirstLevelDivisions(ObservableList<FirstLevelDivision> firstLevelDivisions) {
        this.firstLevelDivisions = firstLevelDivisions;
    }

    /**
     * Updates the options in the First Level Division (FLD) ChoiceBox based on the selected country.
     * This method filters the first level divisions to only show those that belong to the selected country.
     *
     * @param countryID The ID of the country selected by the user. This ID is used to filter the first level divisions
     *                  that are displayed in the {@code firstLevelDivisionChoiceBox}.
     */
    private void updateFLDChoiceBox(int countryID) {
        firstLevelDivisionChoiceBox.getSelectionModel().clearSelection();
        ObservableList<FirstLevelDivision> filteredFLDs = this.getFirstLevelDivisions().filtered(division ->
                division.getCountryID() == countryID);
        firstLevelDivisionChoiceBox.setItems(filteredFLDs);
    }

    /**
     * Populates the form fields with information from the selected customer, including the customer's
     * country and first level division based on their division ID.
     */
    public void setupFormWithCustomer() {
        // Handling the basic information
        customerIDField.setText(String.valueOf(getSelectedCustomer().getCustomerID()));
        customerNameField.setText(getSelectedCustomer().getCustomerName());
        addressField.setText(getSelectedCustomer().getAddress());
        postalCodeField.setText(getSelectedCustomer().getPostalCode());
        phoneField.setText(getSelectedCustomer().getPhone());

        // Handling the foreign keys Country and First Level Division
        FirstLevelDivision firstLevelDivision = FirstLevelDivisionsQuery.readByID(getSelectedCustomer().getDivisionID());
        Country country = CountriesQuery.readByID(firstLevelDivision.getCountryID());

        // Select customer's country and division
        countryChoiceBox.getSelectionModel().select(country);
        // The listener then populates the FLD choice box, so we delay the FLD selection to happen afterward.
        Platform.runLater(() -> firstLevelDivisionChoiceBox.getItems().stream()
                .filter(division -> division.getDivisionID() == getSelectedCustomer().getDivisionID())
                .findFirst()
                .ifPresent(division -> firstLevelDivisionChoiceBox.getSelectionModel().select(division)));

    }

    /**
     * Validates the modified customer data and, if valid, updates the customer in the database. After successfully
     * updating the customer, switches the scene back to the customer main view. Displays alerts for any validation
     * failures.
     *
     * @param actionEvent The event that triggered this method.
     */
    public void confirmButtonHandler(ActionEvent actionEvent) {
        // Ensure each element has an input
        if (FormHelper.isInputEmpty(customerNameField, "customer name")) { return; }
        if (FormHelper.isInputEmpty(addressField, "address")) { return; }
        if (FormHelper.isInputEmpty(postalCodeField, "postal code")) { return; }
        if (FormHelper.isInputEmpty(phoneField, "phone")) { return; }
        if (FormHelper.isInputEmpty(countryChoiceBox, "country")) { return; }
        if (FormHelper.isInputEmpty(firstLevelDivisionChoiceBox, "first level division")) { return; }

        // Create a temporary Customer object
        Customer tempCustomer = getCustomer();

        // Update the customer's entry in the database
        if (CustomersQuery.update(tempCustomer)) {
            SceneSwitcher.switchScene(actionEvent, "/c195/c195assessment/fxml/customerMain.fxml");
        }
        else {
            System.out.println("ModifyCustomerController: Customer information was not updated.");
        }
    }

    /**
     * Creates and returns a {@link Customer} object populated with the data entered in the form fields. This method
     * extracts the values entered by the user into the customer form and uses them to populate a new Customer object.
     * The division ID is obtained from the currently selected item in the {@code firstLevelDivisionChoiceBox}.
     *
     * @return A newly created {@link Customer} object populated with the form data. If no first level division is
     * selected, the division ID of the customer will not be set, potentially leading to an incomplete or invalid
     * customer object.
     */
    private Customer getCustomer() {
        Customer tempCustomer = new Customer();
        tempCustomer.setCustomerID(Integer.parseInt(customerIDField.getText()));
        tempCustomer.setCustomerName(customerNameField.getText());
        tempCustomer.setAddress(addressField.getText());
        tempCustomer.setPostalCode(postalCodeField.getText());
        tempCustomer.setPhone(phoneField.getText());
        tempCustomer.setDivisionID(firstLevelDivisionChoiceBox.getSelectionModel().getSelectedItem().getDivisionID());
        return tempCustomer;
    }

    /**
     * Handles the action of clicking the Cancel button, discarding any changes and returning to the customer main view.
     *
     * @param actionEvent The event that triggered this method.
     */
    public void cancelButtonHandler(ActionEvent actionEvent) {
        SceneSwitcher.switchScene(actionEvent, "/c195/c195assessment/fxml/customerMain.fxml");
    }
}
