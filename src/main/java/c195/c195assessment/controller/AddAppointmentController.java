package c195.c195assessment.controller;

import c195.c195assessment.dao.ContactsQuery;
import c195.c195assessment.helper.AppContext;
import c195.c195assessment.model.Contact;
import c195.c195assessment.model.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ResourceBundle;

public class AddAppointmentController {


    public Label apptMenuLabel;
    public Label titleLabel;
    public TextField titleField;
    public TextField descriptionField;
    public Label descriptionLabel;
    public Label locationLabel;
    public TextField locationField;
    public Label typeLabel;
    public TextField typeField;
    public Label startLabel;
    public DatePicker startDatePicker;
    public ComboBox<String> startTimeComboBox;
    public Label endLabel;
    public DatePicker endDatePicker;
    public ComboBox<String> endTimeComboBox;
    public Label contactLabel;
    public ChoiceBox<Contact> contactChoiceBox;
    public Label customerIDLabel;
    public ComboBox<Customer> customerChoiceBox;
    public Label userLabel;
    public Label userIDValueField;
    public Button confirmButton;
    public Button cancelButton;

    @FXML
    public void initialize() {
        // Populating the options for the ComboBox elements

        contactChoiceBox.setItems(ContactsQuery.readAll());

    }

    public void cancelButtonHandler(ActionEvent actionEvent) {
    }

    public void confirmButtonHandler(ActionEvent actionEvent) {
    }
}
