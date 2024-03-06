package c195.c195assessment.controller;

import c195.c195assessment.dao.AppointmentsQuery;
import c195.c195assessment.dao.ContactsQuery;
import c195.c195assessment.dao.CustomersQuery;
import c195.c195assessment.helper.*;
import c195.c195assessment.model.Contact;
import c195.c195assessment.model.Customer;
import c195.c195assessment.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDateTime;

/**
 * Controller for the Add Appointment view, enabling users to input and submit new appointments.
 */
public class AddAppointmentController {

    public TextField titleField;
    public TextField descriptionField;
    public TextField locationField;
    public TextField typeField;
    public DatePicker startDatePicker;
    public ComboBox<String> startTimeComboBox;
    public DatePicker endDatePicker;
    public ComboBox<String> endTimeComboBox;
    public ChoiceBox<Contact> contactChoiceBox;
    public ComboBox<Customer> customerChoiceBox;

    /**
     * Initializes the controller class. This method is automatically called after the FXML file has been loaded. It
     * sets up the form fields, including populating the choice and combo boxes with data from the database.
     */
    @FXML
    public void initialize() {
        // Populating the options for the ComboBox elements
        FormHelper.populateTimeComboBoxes(startTimeComboBox,  15);
        FormHelper.populateTimeComboBoxes(endTimeComboBox, 15 );
        contactChoiceBox.setItems(ContactsQuery.readAll());
        customerChoiceBox.setItems(CustomersQuery.readAll());
    }

    /**
     * Handles the action of clicking the Cancel button. This method will switch the scene back to the main appointment
     * view without saving any input data.
     *
     * @param actionEvent The event that triggered this method.
     */
    public void cancelButtonHandler(ActionEvent actionEvent) {
        SceneSwitcher.switchScene(actionEvent, "/c195/c195assessment/fxml/appointmentMain.fxml");
    }

    /**
     * Validates the form inputs and, if valid, creates a new appointment in the database. After successfully creating
     * the appointment, it switches the scene back to the main appointment view. Displays alerts for any validation
     * failures.
     *
     * @param actionEvent The event that triggered this method.
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
        int contactID = contactChoiceBox.getValue().getContactId();
        int customerID = customerChoiceBox.getValue().getCustomerID();

        // Ensure start is before end
        if (start.isAfter(end)) {
            Alerts.showAlert("Time Input error", "Please ensure that the start date and time is before the end date and time.");
            return;
        }

        // Convert times to UTC
        LocalDateTime utcStart = TimeZoneConversion.localToUtc(start);
        LocalDateTime utcEnd = TimeZoneConversion.localToUtc(end);

        // Insert entry into database
        User currentUser = AppContext.getUser();
        AppointmentsQuery.insert(title, description, location, type, utcStart, utcEnd, currentUser.getUserName(),
                currentUser.getUserName(), customerID, currentUser.getUserId(), contactID);

        // Close addAppointment.fxml and open appointmentMain.fxml
        SceneSwitcher.switchScene(actionEvent, "/c195/c195assessment/fxml/appointmentMain.fxml");
    }
}
