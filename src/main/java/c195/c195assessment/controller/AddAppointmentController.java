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

    public void cancelButtonHandler(ActionEvent actionEvent) {
        // DELETE ME
        System.out.println("Title: " + titleField.getText());
        System.out.println("Description: " + descriptionField.getText());
        System.out.println("Location: " + locationField.getText());
        System.out.println("Type: " + typeField.getText());
        System.out.println("Start Date: " + startDatePicker.getValue());
        System.out.println("Start Time: " + startTimeComboBox.getValue());
        System.out.println("End Date: " + endDatePicker.getValue());
        System.out.println("End Time: " + endTimeComboBox.getValue());
        System.out.println("Contact ID: " + contactChoiceBox.getSelectionModel().getSelectedItem().getContactId());
        System.out.println("Contact ID: " + customerChoiceBox.getSelectionModel().getSelectedItem().getCustomerID());

        SceneSwitcher.switchScene(actionEvent, "/c195/c195assessment/fxml/appointmentMain.fxml");
    }

    public void confirmButtonHandler(ActionEvent actionEvent) {
        // Get data from the Form's fields
        String title = titleField.getText();
        String description = descriptionField.getText();
        String location = locationField.getText();
        String type = typeField.getText();
        LocalDateTime start = FormHelper.getDateTimeFromForm(startDatePicker, startTimeComboBox);
        LocalDateTime end = FormHelper.getDateTimeFromForm(endDatePicker, endTimeComboBox);
        Contact contact = contactChoiceBox.getSelectionModel().getSelectedItem();
        Customer customer = customerChoiceBox.getSelectionModel().getSelectedItem();
        int contactID = contactChoiceBox.getSelectionModel().getSelectedItem().getContactId();
        int customerID = customerChoiceBox.getSelectionModel().getSelectedItem().getCustomerID();

        // Validate data
        if (isInputEmpty(title, "title")) { return; }
        if (isInputEmpty(description, "description")) { return; }
        if (isInputEmpty(location, "location")) { return; }
        if (isInputEmpty(type, "type")) { return; }


        // Insert entry into database
        User currentUser = AppContext.getUser();
        AppointmentsQuery.insert(title, description, location, type, start, end, currentUser.getUserName(),
                currentUser.getUserName(), customerID, currentUser.getUserId(), contactID);

        // Close addAppointment.fxml and open appointmentMain.fxml
        SceneSwitcher.switchScene(actionEvent, "/c195/c195assessment/fxml/appointmentMain.fxml");
    }

    private void emptyInputAlert(String inputName) {
        Alerts.showAlert("Empty input field", "Please provide a " + inputName + '.');
    }

    private boolean isInputEmpty(String string, String inputName) {
        if (string.isEmpty()) {
            emptyInputAlert(inputName);
            return true;
        }
        return false;
    }

    private boolean isInputEmpty (LocalDate localDate, String inputName) {
        if (localDate == null) {
            emptyInputAlert(inputName);
            return true;
        }
        return false;
    }
}
