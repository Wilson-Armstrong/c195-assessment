package c195.c195assessment.controller;

import c195.c195assessment.dao.CountriesQuery;
import c195.c195assessment.dao.CustomersQuery;
import c195.c195assessment.dao.FirstLevelDivisionsQuery;
import c195.c195assessment.helper.FormHelper;
import c195.c195assessment.helper.SceneSwitcher;
import c195.c195assessment.model.Country;
import c195.c195assessment.model.Customer;
import c195.c195assessment.model.FirstLevelDivision;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.Comparator;

/**
 * Controller for the Add Customer view, providing functionality to input and submit new customer records.
 */
public class AddCustomerController {
    public Label customerMenuLabel;
    public TextField customerIDField;
    public TextField customerNameField;
    public TextField addressField;
    public TextField postalCodeField;
    public TextField phoneField;
    public ChoiceBox<Country> countryChoiceBox;
    public ChoiceBox<FirstLevelDivision> firstLevelDivisionChoiceBox;
    private ObservableList<Country> countries;
    private ObservableList<FirstLevelDivision> firstLevelDivisions;

    /**
     * Initializes the controller class. This method is called after the FXML file has been loaded. It prepares the form
     * by populating the choice boxes for countries and first level divisions based on database entries. It also sets a
     * listener for the country choice box to filter divisions based on the selected country.
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
     * Retrieves the observable list of all countries that can be selected in the country choice box.
     * This list is populated from the database and used to allow the user to select a country for the new customer.
     *
     * @return An {@link ObservableList} of {@link Country} objects.
     */
    public ObservableList<Country> getCountries() { return countries; }

    /**
     * Sets the observable list of countries available for selection when adding a new customer.
     * This method updates the list of countries based on data retrieved from the database.
     *
     * @param countries An {@link ObservableList} of {@link Country} objects.
     */
    public void setCountries(ObservableList<Country> countries) { this.countries = countries; }

    /**
     * Retrieves the observable list of all first level divisions relevant to the selected country. This list is
     * filtered based on the country selected by the user and used to populate the first level division choice box.
     *
     * @return An {@link ObservableList} of {@link FirstLevelDivision} objects.
     */
    public ObservableList<FirstLevelDivision> getFirstLevelDivisions() { return firstLevelDivisions; }

    /**
     * Sets the observable list of first level divisions available for selection based on the selected country.
     * This method is used to update the list of divisions shown to the user after selecting a country.
     *
     * @param firstLevelDivisions An {@link ObservableList} of {@link FirstLevelDivision} objects.
     */
    public void setFirstLevelDivisions(ObservableList<FirstLevelDivision> firstLevelDivisions) {
        this.firstLevelDivisions = firstLevelDivisions;
    }

    /**
     * Filters the options of the First Level Division (FLD) ChoiceBox based on the selected Country's ID. This ensures
     * that only relevant divisions are selectable after a country is selected.
     *
     * @param countryID The ID of the selected country, used to filter first level divisions.
     */
    private void updateFLDChoiceBox(int countryID) {
        firstLevelDivisionChoiceBox.getSelectionModel().clearSelection();
        ObservableList<FirstLevelDivision> filteredFLDs = this.getFirstLevelDivisions().filtered(division ->
                division.getCountryID() == countryID);
        firstLevelDivisionChoiceBox.setItems(filteredFLDs);
    }

    /**
     * Handles the confirmation action for adding a new customer. Validates input fields, creates a new {@link Customer}
     * object from the input, and inserts it into the database. Upon successful insertion, the scene switches back to
     * the customer main view.
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

        Customer tempCustomer = new Customer();
        tempCustomer.setCustomerName(customerNameField.getText());
        tempCustomer.setAddress(addressField.getText());
        tempCustomer.setPostalCode(addressField.getText());
        tempCustomer.setPhone(addressField.getText());
        tempCustomer.setDivisionID(firstLevelDivisionChoiceBox.getSelectionModel().getSelectedItem().getDivisionID());

        CustomersQuery.insert(tempCustomer);
        SceneSwitcher.switchScene(actionEvent, "/c195/c195assessment/fxml/customerMain.fxml");
    }

    /**
     * Handles the cancellation action, discarding any input and returning to the customer main view.
     *
     * @param actionEvent The event that triggered this method.
     */
    public void cancelButtonHandler(ActionEvent actionEvent) {
        SceneSwitcher.switchScene(actionEvent, "/c195/c195assessment/fxml/customerMain.fxml");
    }
}
