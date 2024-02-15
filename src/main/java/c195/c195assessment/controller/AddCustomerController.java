package c195.c195assessment.controller;

import c195.c195assessment.model.Country;
import c195.c195assessment.model.FirstLevelDivision;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddCustomerController {
    public Label customerMenuLabel;
    public TextField customerIDField;
    public TextField customerNameField;
    public TextField addressField;
    public TextField postalCodeField;
    public TextField phoneField;
    public ChoiceBox<Country> countryChoiceBox;
    public ChoiceBox<FirstLevelDivision> fldChoiceBox;

    public void confirmButtonHandler(ActionEvent actionEvent) {

    }

    public void cancelButtonHandler(ActionEvent actionEvent) {

    }
}
