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
import javafx.stage.Stage;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

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
    public ChoiceBox<Customer> customerIDChoiceBox;
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
        apptIDValue.setText(String.valueOf(selectedAppointment.getAppointmentID()));
        titleField.setText(selectedAppointment.getTitle());
        descriptionField.setText(selectedAppointment.getDescription());
        locationField.setText(selectedAppointment.getLocation());
        typeField.setText(selectedAppointment.getType());

        // Start Date and Time fields
        LocalDateTime convertedStartTime = TimeZoneConversion.convertTimeZone(selectedAppointment.getStart(),
                ZoneId.of("UTC"), AppContext.getUserTimeZone().toZoneId());
        LocalDate startDate = convertedStartTime.toLocalDate();
        startDatePicker.setValue(startDate);
        String formattedStartTime = convertedStartTime.format(DateTimeFormatter.ofPattern("HH:mm"));
        startTimeComboBox.getSelectionModel().select(formattedStartTime);

        // End Date and Time fields
        LocalDateTime convertedEndTime = TimeZoneConversion.convertTimeZone(selectedAppointment.getEnd(),
                ZoneId.of("UTC"), AppContext.getUserTimeZone().toZoneId());
        LocalDate endDate = convertedEndTime.toLocalDate();
        endDatePicker.setValue(endDate);
        String formattedEndTime = convertedEndTime.format(DateTimeFormatter.ofPattern("HH:mm"));
        endTimeComboBox.getSelectionModel().select(formattedEndTime);

        // Foreign key fields
        contactChoiceBox.setItems(ContactsQuery.readAll());
        for (Contact contact : contactChoiceBox.getItems()) {
            if (contact.getContactId() == selectedAppointment.getContactID()) {
                contactChoiceBox.getSelectionModel().select(contact);
                break;
            }
        }
        customerIDChoiceBox.setItems(CustomersQuery.readAll());
        for (Customer customer : customerIDChoiceBox.getItems()) {
            if (customer.getCustomerID() == selectedAppointment.getCustomerID()) {
                customerIDChoiceBox.getSelectionModel().select(customer);
                break;
            }
        }
        userIDValueField.setText(String.valueOf(selectedAppointment.getUserID()));
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
        tempAppointment.setCustomerID(customerIDChoiceBox.getSelectionModel().getSelectedItem().getCustomerID());

        // Check values for validity
        if (tempAppointment.getStart().isAfter(tempAppointment.getEnd())) {
            Alerts.showAlert("Date-Time Error",
                    "The starting date and time must be before the ending date and time.");
            return;
        }

        // Update the Appointment object
        selectedAppointment = tempAppointment;

        // Update the entry in the database
        boolean updateWorked = AppointmentsQuery.update(selectedAppointment);
        if (!updateWorked) {
            System.out.println("Failed to update appointment " + selectedAppointment.getAppointmentID());
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
