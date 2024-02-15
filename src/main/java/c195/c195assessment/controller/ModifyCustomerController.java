package c195.c195assessment.controller;

import c195.c195assessment.dao.CountriesQuery;
import c195.c195assessment.dao.FirstLevelDivisionsQuery;
import c195.c195assessment.model.Country;
import c195.c195assessment.model.Customer;
import c195.c195assessment.model.FirstLevelDivision;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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

    public Customer getSelectedCustomer() { return selectedCustomer; }

    public void setSelectedCustomer(Customer selectedCustomer) { this.selectedCustomer = selectedCustomer; }

    public ObservableList<Country> getCountries() { return countries; }

    public void setCountries(ObservableList<Country> countries) { this.countries = countries; }

    public ObservableList<FirstLevelDivision> getFirstLevelDivisions() { return firstLevelDivisions; }

    public void setFirstLevelDivisions(ObservableList<FirstLevelDivision> firstLevelDivisions) {
        this.firstLevelDivisions = firstLevelDivisions;
    }

    /**  */
    public void initialize() {
        // Getting information from the database
        this.setCountries(CountriesQuery.readAll());
        this.setFirstLevelDivisions(FirstLevelDivisionsQuery.readAll());

        // Populating the options for the Country ChoiceBox
        countryChoiceBox.setItems(countries);

        // Setting listener for countryChoiceBox that will update the options in the FLD ChoiceBox.
        countryChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                updateFLDChoiceBox(newSelection.getCountryId());
            }
        });

    }

    /** This method populates the options of the FLD ChoiceBox with the FLDs of the selected Country. */
    private void updateFLDChoiceBox(int countryID) {
        firstLevelDivisionChoiceBox.getSelectionModel().clearSelection();
        ObservableList<FirstLevelDivision> filteredFLDs = this.getFirstLevelDivisions().filtered(division ->
                division.getCountryID() == countryID);
        firstLevelDivisionChoiceBox.setItems(filteredFLDs);
    }

    /** Populates the form's fields with the information from the selected customer. */
    public void setupFormWithCustomer() {
        // Handling the basic information
        customerIDField.setText(String.valueOf(getSelectedCustomer().getCustomerID()));
        customerNameField.setText(getSelectedCustomer().getCustomerName());
        addressField.setText(getSelectedCustomer().getAddress());
        postalCodeField.setText(getSelectedCustomer().getPostalCode());
        phoneField.setText(getSelectedCustomer().getPhone());

        // Handling the foreign keys Country and First Level Division
        FirstLevelDivision firstLevelDivision = FirstLevelDivisionsQuery.readByID(getSelectedCustomer().getDivisionId());
        Country country = CountriesQuery.readByID(firstLevelDivision.getCountryID());
    }

    public void confirmButtonHandler(ActionEvent actionEvent) {

    }

    public void cancelButtonHandler(ActionEvent actionEvent) {
    }


}
