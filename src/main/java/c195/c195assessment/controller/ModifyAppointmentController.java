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
import java.time.format.DateTimeFormatter;

/**
 * Controller for the Modify Appointment view, enabling users to update existing appointments.
 */
public class ModifyAppointmentController {
    private Appointment selectedAppointment;


    // FXML elements
    public TextField titleField;
    public TextField descriptionField;
    public TextField locationField;
    public TextField typeField;
    public DatePicker startDatePicker;
    public ComboBox<String> startTimeComboBox;
    public DatePicker endDatePicker;
    public ComboBox<String> endTimeComboBox;
    public ChoiceBox<Contact> contactChoiceBox;
    public ChoiceBox<Customer> customerChoiceBox;
    public Label userIDValueField;
    public Label apptIDValue;

    /**
     * Initializes the controller. This method is called after the FXML file has been loaded. It sets up the form fields
     * with existing appointment data and populates the choice boxes.
     */
    @FXML
    public void initialize() {
        FormHelper.populateTimeComboBoxes(startTimeComboBox,  15);
        FormHelper.populateTimeComboBoxes(endTimeComboBox,  15);
    }

    /**
     * Populates the form's fields with the information from the selected appointment. This includes setting values for
     * the appointment's title, description, location, type, start and end times, contact, and customer.
     */
    public void setupFormWithAppointment() {
        // Setting the values of the form to represent those of the selected appointment
        apptIDValue.setText(String.valueOf(getSelectedAppointment().getAppointmentID()));
        titleField.setText(getSelectedAppointment().getTitle());
        descriptionField.setText(getSelectedAppointment().getDescription());
        locationField.setText(getSelectedAppointment().getLocation());
        typeField.setText(getSelectedAppointment().getType());

        // Start Date and Time fields
        LocalDateTime convertedStartTime = TimeZoneConversion.utcToLocal(getSelectedAppointment().getStart());
        LocalDate startDate = convertedStartTime.toLocalDate();
        startDatePicker.setValue(startDate);
        String formattedStartTime = convertedStartTime.format(DateTimeFormatter.ofPattern("HH:mm"));
        startTimeComboBox.getSelectionModel().select(formattedStartTime);

        // End Date and Time fields
        LocalDateTime convertedEndTime = TimeZoneConversion.utcToLocal(getSelectedAppointment().getEnd());
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

    /**
     * Handles the action of clicking the Cancel button. Returns the user to the main appointment view without applying
     * any changes to the selected appointment.
     *
     * @param actionEvent The event that triggered this method.
     */
    public void cancelButtonHandler(ActionEvent actionEvent) {
        SceneSwitcher.switchScene(actionEvent, "/c195/c195assessment/fxml/appointmentMain.fxml");
    }

    /**
     * Validates the modified appointment data and, if valid, updates the appointment in the database. After
     * successfully updating the appointment, it switches the scene back to the main appointment view. Displays alerts
     * for any validation failures.
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

        // Create a new Appointment object
        Appointment tempAppointment = getTempAppointment();

        // Update tempAppointment with the values in the form's fields
        tempAppointment.setTitle(titleField.getText());
        tempAppointment.setDescription(descriptionField.getText());
        tempAppointment.setLocation(locationField.getText());
        tempAppointment.setType(typeField.getText());
        LocalDateTime selectedStartTime = FormHelper.getDateTimeFromForm(startDatePicker, startTimeComboBox);
        LocalDateTime convertedStartTime = TimeZoneConversion.localToUtc(selectedStartTime);
        tempAppointment.setStart(convertedStartTime);
        LocalDateTime selectedEndTime = FormHelper.getDateTimeFromForm(endDatePicker, endTimeComboBox);
        LocalDateTime convertedEndTime = TimeZoneConversion.localToUtc(selectedEndTime);
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

    /**
     * Creates a temporary Appointment object based on the form's fields to facilitate validation and database update
     * operations. This method ensures that modifications are coherent before being committed to the database.
     *
     * @return A new Appointment object with updated values from the form.
     */
    private Appointment getTempAppointment() {
        LocalDate startDate = startDatePicker.getValue();
        LocalTime startTime = LocalTime.parse(startTimeComboBox.getValue(), DateTimeFormatter.ofPattern("HH:mm"));
        LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
        LocalDate endDate = endDatePicker.getValue();
        LocalTime endTime = LocalTime.parse(endTimeComboBox.getValue(), DateTimeFormatter.ofPattern("HH:mm"));
        return getAppointment(endDate, endTime, startDateTime);
    }

    /**
     * Creates and returns a {@link Appointment} object populated with the data entered in the form fields. This method
     * extracts the values entered by the user into the appointment form and uses them to populate a new Appointment
     * object.
     *
     * @return A newly created {@link Appointment} object populated with the form data.
     */
    private Appointment getAppointment(LocalDate endDate, LocalTime endTime, LocalDateTime startDateTime) {
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

    /**
     * Gets the currently selected appointment being modified. This appointment is loaded into the form fields for
     * editing by the user.
     *
     * @return The {@link Appointment} object currently selected for modification.
     */
    public Appointment getSelectedAppointment() {
        return selectedAppointment;
    }

    /**
     * Sets the currently selected appointment to be modified. This method updates the controller's reference to the
     * selected appointment, which is used to pre-populate the form fields and to identify which appointment record is
     * being updated in the database.
     *
     * @param selectedAppointment The {@link Appointment} object selected by the user for modification.
     */
    public void setSelectedAppointment(Appointment selectedAppointment) {
        this.selectedAppointment = selectedAppointment;
    }
}
