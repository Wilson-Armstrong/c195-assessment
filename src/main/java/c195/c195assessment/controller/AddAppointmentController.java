package c195.c195assessment.controller;

import c195.c195assessment.dao.AppointmentsQuery;
import c195.c195assessment.dao.ContactsQuery;
import c195.c195assessment.dao.CustomersQuery;
import c195.c195assessment.helper.Alerts;
import c195.c195assessment.helper.AppContext;
import c195.c195assessment.helper.FormHelper;
import c195.c195assessment.helper.SceneSwitcher;
import c195.c195assessment.model.Contact;
import c195.c195assessment.model.Customer;
import c195.c195assessment.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    public Button confirmButton;
    public Button cancelButton;

    @FXML
    public void initialize() {
        // Populating the options for the ComboBox elements
        FormHelper.populateTimeComboBoxes(startTimeComboBox, 0, 23);
        FormHelper.populateTimeComboBoxes(endTimeComboBox, 0, 23);
        contactChoiceBox.setItems(ContactsQuery.readAll());
        customerChoiceBox.setItems(CustomersQuery.readAll());
    }

    /**
     * Changes the scene back to appointmentMain.fxml.
     */
    public void cancelButtonHandler(ActionEvent actionEvent) {
        SceneSwitcher.switchScene(actionEvent, "/c195/c195assessment/fxml/appointmentMain.fxml");
    }

    /**
     * Ensures that the user has specified valid values for each field of the form, displaying an alert for an invalid
     * inputs. If all inputs are valid, AppointmentsQuery inserts a new entry using the inputs and the scene switches
     * back to appointmentMain.fxml.
     */
    public void confirmButtonHandler(ActionEvent actionEvent) {
        // Ensure each element has an input
        if (FormHelper.isInputEmpty(titleField, "title")) { return; }
        if (FormHelper.isInputEmpty(descriptionField, "description")) { return; }
        if (FormHelper.isInputEmpty(locationField, "location")) { return; }
        if (FormHelper.isInputEmpty(typeField, "type")) { return; }
        if (FormHelper.isInputEmpty(startDatePicker, "start date")) { return; }
        if (FormHelper.isInputEmpty(startTimeComboBox, "start time")) { return; }
        if (FormHelper.isInputEmpty(endDatePicker, "end date")) { return; }
        if (FormHelper.isInputEmpty(endTimeComboBox, "end time")) { return; }
        if (FormHelper.isInputEmpty(contactChoiceBox, "contact")) { return; }
        if (FormHelper.isInputEmpty(customerChoiceBox, "customer")) { return; }

        // Get data from the Form's fields
        String title = titleField.getText();
        String description = descriptionField.getText();
        String location = locationField.getText();
        String type = typeField.getText();
        LocalDateTime start = FormHelper.getDateTimeFromForm(startDatePicker, startTimeComboBox);
        LocalDateTime end = FormHelper.getDateTimeFromForm(endDatePicker, endTimeComboBox);
        Contact contact = contactChoiceBox.getValue();
        Customer customer = customerChoiceBox.getValue();
        int contactID = contactChoiceBox.getValue().getContactId();
        int customerID = customerChoiceBox.getValue().getCustomerID();

        // Ensure start is before end
        if (start.isAfter(end)) {
            Alerts.showAlert("Time Input error", "Please ensure that the start date and time is before the end date and time.");
            return;
        }

        // Insert entry into database
        User currentUser = AppContext.getUser();
        AppointmentsQuery.insert(title, description, location, type, start, end, currentUser.getUserName(),
                currentUser.getUserName(), customerID, currentUser.getUserId(), contactID);

        // Close addAppointment.fxml and open appointmentMain.fxml
        SceneSwitcher.switchScene(actionEvent, "/c195/c195assessment/fxml/appointmentMain.fxml");
    }
}
