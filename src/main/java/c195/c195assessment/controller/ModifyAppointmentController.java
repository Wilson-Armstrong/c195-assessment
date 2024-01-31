package c195.c195assessment.controller;

import c195.c195assessment.dao.AppointmentsQuery;
import c195.c195assessment.dao.ContactsQuery;
import c195.c195assessment.dao.CustomersQuery;
import c195.c195assessment.helper.*;
import c195.c195assessment.model.Appointment;
import c195.c195assessment.model.Contact;
import c195.c195assessment.model.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ModifyAppointmentController {
    private Appointment selectedAppointment;


    // FXML elements
    public Label apptMenuLabel;
    public Label apptIDLabel;
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
    public ChoiceBox<Customer> customerChoiceBox;
    public Label userLabel;
    public Label userIDValueField;
    public Button confirmButton;
    public Button cancelButton;
    public Label apptIDValue;

    @FXML
    public void initialize() {
        FormHelper.populateTimeComboBoxes(startTimeComboBox, 0, 23);
        FormHelper.populateTimeComboBoxes(endTimeComboBox, 0, 23);
    }

    /** Populates the form's fields with the information from the selected appointment. */
    public void setupFormWithAppointment() {
        // Setting the values of the form to represent those of the selected appointment
        apptIDValue.setText(String.valueOf(getSelectedAppointment().getAppointmentID()));
        titleField.setText(getSelectedAppointment().getTitle());
        descriptionField.setText(getSelectedAppointment().getDescription());
        locationField.setText(getSelectedAppointment().getLocation());
        typeField.setText(getSelectedAppointment().getType());

        // Start Date and Time fields
        LocalDateTime convertedStartTime = TimeZoneConversion.convertTimeZone(getSelectedAppointment().getStart(),
                ZoneId.of("UTC"), AppContext.getUserTimeZone().toZoneId());
        LocalDate startDate = convertedStartTime.toLocalDate();
        startDatePicker.setValue(startDate);
        String formattedStartTime = convertedStartTime.format(DateTimeFormatter.ofPattern("HH:mm"));
        startTimeComboBox.getSelectionModel().select(formattedStartTime);

        // End Date and Time fields
        LocalDateTime convertedEndTime = TimeZoneConversion.convertTimeZone(getSelectedAppointment().getEnd(),
                ZoneId.of("UTC"), AppContext.getUserTimeZone().toZoneId());
        LocalDate endDate = convertedEndTime.toLocalDate();
        endDatePicker.setValue(endDate);
        String formattedEndTime = convertedEndTime.format(DateTimeFormatter.ofPattern("HH:mm"));
        endTimeComboBox.getSelectionModel().select(formattedEndTime);

        // Foreign key fields
        contactChoiceBox.setItems(ContactsQuery.readAll());
        for (Contact contact : contactChoiceBox.getItems()) {
            if (contact.getContactId() == getSelectedAppointment().getContactID()) {
                contactChoiceBox.getSelectionModel().select(contact);
                break;
            }
        }
        customerChoiceBox.setItems(CustomersQuery.readAll());
        for (Customer customer : customerChoiceBox.getItems()) {
            if (customer.getCustomerID() == getSelectedAppointment().getCustomerID()) {
                customerChoiceBox.getSelectionModel().select(customer);
                break;
            }
        }
        userIDValueField.setText(String.valueOf(getSelectedAppointment().getUserID()));
    }

    /** Return to the appointment table view without making changes to the selected appointment. */
    public void cancelButtonHandler(ActionEvent actionEvent) {
        SceneSwitcher.switchScene(actionEvent, "/c195/c195assessment/fxml/appointmentMain.fxml");
    }

    /**
     * Ensures that any changes are valid. If so, the selected appointment is updated and the scene returns to the
     * appointment table view. If not, the user is shown an alert specifying the error.
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

        // Create a new Appointment object
        Appointment tempAppointment = getTempAppointment();

        // Update tempAppointment with the values in the form's fields
        tempAppointment.setTitle(titleField.getText());
        tempAppointment.setDescription(descriptionField.getText());
        tempAppointment.setLocation(locationField.getText());
        tempAppointment.setType(typeField.getText());
        LocalDateTime selectedStartTime = FormHelper.getDateTimeFromForm(startDatePicker, startTimeComboBox);
        LocalDateTime convertedStartTime = TimeZoneConversion.convertTimeZone(selectedStartTime,
                AppContext.getUserTimeZone().toZoneId(), ZoneId.of("UTC"));
        tempAppointment.setStart(convertedStartTime);
        LocalDateTime selectedEndTime = FormHelper.getDateTimeFromForm(endDatePicker, endTimeComboBox);
        LocalDateTime convertedEndTime = TimeZoneConversion.convertTimeZone(selectedEndTime,
                AppContext.getUserTimeZone().toZoneId(), ZoneId.of("UTC"));
        tempAppointment.setEnd(convertedEndTime);
        tempAppointment.setContactID(contactChoiceBox.getSelectionModel().getSelectedItem().getContactId());
        tempAppointment.setCustomerID(customerChoiceBox.getSelectionModel().getSelectedItem().getCustomerID());

        // Ensure start is before end
        if (tempAppointment.getStart().isAfter(tempAppointment.getEnd())) {
            Alerts.showAlert("Time Input error", "Please ensure that the start date and time is before the end date and time.");
            return;
        }

        // Update the Appointment object
        setSelectedAppointment(tempAppointment);

        // Update the entry in the database
        boolean updateWorked = AppointmentsQuery.update(getSelectedAppointment());
        if (!updateWorked) {
            System.out.println("Failed to update appointment " + getSelectedAppointment().getAppointmentID());
            return;
        }

        // Return to the appointment table view
        SceneSwitcher.switchScene(actionEvent, "/c195/c195assessment/fxml/appointmentMain.fxml");
    }

    /** Creates a new Appointment object that copies attributes from the selectedAppointment and takes values from the
     * editable fields in modifyAppointment.fxml. */
    private Appointment getTempAppointment() {
        LocalDate startDate = startDatePicker.getValue();
        LocalTime startTime = LocalTime.parse(startTimeComboBox.getValue(), DateTimeFormatter.ofPattern("HH:mm"));
        LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
        LocalDate endDate = endDatePicker.getValue();
        LocalTime endTime = LocalTime.parse(endTimeComboBox.getValue(), DateTimeFormatter.ofPattern("HH:mm"));
        LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);

        Appointment tempAppointment = new Appointment(selectedAppointment);
        tempAppointment.setTitle(titleField.getText());
        tempAppointment.setDescription(descriptionField.getText());
        tempAppointment.setLocation(locationField.getText());
        tempAppointment.setType(typeField.getText());
        tempAppointment.setStart(startDateTime);
        tempAppointment.setEnd(endDateTime);
        tempAppointment.setContactID(contactChoiceBox.getSelectionModel().getSelectedItem().getContactId());
        return tempAppointment;
    }

    public Appointment getSelectedAppointment() {
        return selectedAppointment;
    }

    public void setSelectedAppointment(Appointment selectedAppointment) {
        this.selectedAppointment = selectedAppointment;
    }
}
