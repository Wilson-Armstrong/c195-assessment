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

import java.util.Collections;
import java.util.Comparator;

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

    /** Prepares the forms by retrieving the Country and FLD information from the database and setting a listener to
     * the country ChoiceBox that filters the options in the FLD ChoiceBox. */
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

        // Select customer's country and division
        countryChoiceBox.getSelectionModel().select(country);
        // The listener then populates the FLD choice box, so we delay the FLD selection to happen afterward.
        Platform.runLater(() -> {
            firstLevelDivisionChoiceBox.getItems().stream()
                    .filter(division -> division.getDivisionID() == getSelectedCustomer().getDivisionId())
                    .findFirst()
                    .ifPresent(division -> firstLevelDivisionChoiceBox.getSelectionModel().select(division));
        });

    }

    public void confirmButtonHandler(ActionEvent actionEvent) {
        // Ensure each element has an input
        if (FormHelper.isInputEmpty(customerNameField, "customer name")) { return; }
        if (FormHelper.isInputEmpty(addressField, "address")) { return; }
        if (FormHelper.isInputEmpty(postalCodeField, "postal code")) { return; }
        if (FormHelper.isInputEmpty(phoneField, "phone")) { return; }
        if (FormHelper.isInputEmpty(countryChoiceBox, "country")) { return; }
        if (FormHelper.isInputEmpty(firstLevelDivisionChoiceBox, "first level division")) { return; }

        // Create a temporary Customer object
        Customer tempCustomer = new Customer();
        tempCustomer.setCustomerID(Integer.parseInt(customerIDField.getText()));
        tempCustomer.setCustomerName(customerNameField.getText());
        tempCustomer.setAddress(addressField.getText());
        tempCustomer.setPostalCode(postalCodeField.getText());
        tempCustomer.setPhone(phoneField.getText());
        tempCustomer.setDivisionId(firstLevelDivisionChoiceBox.getSelectionModel().getSelectedItem().getDivisionID());

        // Update the customer's entry in the database
        if (CustomersQuery.update(tempCustomer)) {
            SceneSwitcher.switchScene(actionEvent, "/c195/c195assessment/fxml/customerMain.fxml");
        }
        else {
            System.out.println("ModifyCustomerController: Customer information was not updated.");
        }
    }

    public void cancelButtonHandler(ActionEvent actionEvent) {
        SceneSwitcher.switchScene(actionEvent, "/c195/c195assessment/fxml/customerMain.fxml");
    }


}
