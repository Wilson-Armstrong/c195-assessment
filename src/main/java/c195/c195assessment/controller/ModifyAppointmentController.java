package c195.c195assessment.controller;

import c195.c195assessment.dao.AppointmentsQuery;
import c195.c195assessment.dao.ContactsQuery;
import c195.c195assessment.helper.Alerts;
import c195.c195assessment.model.Appointment;
import c195.c195assessment.model.Contact;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    public Label customerIdValueField;
    public Label userLabel;
    public Label userIDValueField;
    public Button confirmButton;
    public Button cancelButton;
    public Label apptIDValue;

    @FXML
    public void initialize() {
        populateTimeComboBoxes(startTimeComboBox, 0, 23);
        populateTimeComboBoxes(endTimeComboBox, 0, 23);
    }

    public void setupFormWithAppointment() {
        // Setting the values of the form to represent those of the selected appointment
        apptIDValue.setText(String.valueOf(selectedAppointment.getAppointmentID()));
        titleField.setText(selectedAppointment.getTitle());
        descriptionField.setText(selectedAppointment.getDescription());
        locationField.setText(selectedAppointment.getLocation());
        typeField.setText(selectedAppointment.getType());

        // Start Date and Time fields
        LocalDate startDate = selectedAppointment.getStart().toLocalDate();
        startDatePicker.setValue(startDate);
        String formattedStartTime = selectedAppointment.getStart().format(DateTimeFormatter.ofPattern("HH:mm"));
        startTimeComboBox.getSelectionModel().select(formattedStartTime);

        // End Date and Time fields
        LocalDate endDate = selectedAppointment.getEnd().toLocalDate();
        endDatePicker.setValue(endDate);
        String formattedEndTime = selectedAppointment.getEnd().format(DateTimeFormatter.ofPattern("HH:mm"));
        endTimeComboBox.getSelectionModel().select(formattedEndTime);

        // Foreign key fields
        contactChoiceBox.setItems(ContactsQuery.readAll());
        for (Contact contact : contactChoiceBox.getItems()) {
            if (contact.getContactId() == selectedAppointment.getContactID()) {
                contactChoiceBox.getSelectionModel().select(contact);
                break;
            }
        }
        customerIdValueField.setText(String.valueOf(selectedAppointment.getCustomerID()));
        userIDValueField.setText(String.valueOf(selectedAppointment.getUserID()));
    }

    public void cancelButtonHandler(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void confirmButtonHandler(ActionEvent actionEvent) {
        // Create a new Appointment object
        Appointment tempAppointment = getTempAppointment();

        // Update tempAppointment with the values in the form's fields
        tempAppointment.setTitle(titleField.getText());
        tempAppointment.setDescription(descriptionField.getText());
        tempAppointment.setLocation(locationField.getText());
        tempAppointment.setType(typeField.getText());
        tempAppointment.setStart(getDateTimeFromForm(startDatePicker, startTimeComboBox));
        tempAppointment.setEnd(getDateTimeFromForm(endDatePicker, endTimeComboBox));
        tempAppointment.setContactID(contactChoiceBox.getSelectionModel().getSelectedItem().getContactId());
        // TO DO: Update tempAppointment's CustomerID from a selection.

        // Check values for validity
        if (tempAppointment.getStart().isAfter(tempAppointment.getEnd())) {
            Alerts.showAlert("Date-Time Error",
                    "The starting date and time must be before the ending date and time.");
        }

        // Update the Appointment object
        selectedAppointment = tempAppointment;

        // Update the entry in the database
        boolean updateWorked = AppointmentsQuery.update(selectedAppointment);
        if (!updateWorked) {
            System.out.println("Failed to update appointment " + selectedAppointment.getAppointmentID());
            return;
        }

        // Close the modification form
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();

        // Refresh the TableView
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

    private void populateTimeComboBoxes(ComboBox<String> comboBox, int startHour, int endHour) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        for (int hour = startHour; hour <= endHour; hour++) {
            LocalTime time = LocalTime.of(hour, 0);
            String formattedTime = time.format(formatter);
            comboBox.getItems().add(formattedTime);
        }
    }

    private LocalDateTime getDateTimeFromForm(DatePicker datePicker, ComboBox<String> timeComboBox) {
        LocalDate selectedDate = datePicker.getValue();
        String selectedTimeStr = timeComboBox.getValue();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime selectedTime = LocalTime.parse(selectedTimeStr, timeFormatter);
        return LocalDateTime.of(selectedDate, selectedTime);
    }
}
