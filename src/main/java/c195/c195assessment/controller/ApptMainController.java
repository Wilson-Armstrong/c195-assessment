package c195.c195assessment.controller;

import c195.c195assessment.dao.AppointmentsQuery;
import c195.c195assessment.helper.*;
import c195.c195assessment.model.Appointment;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.stream.Collectors;

public class ApptMainController {
    private ObservableList<Appointment> allAppointments;  // List of appointments in the table

    // FXML elements
    public Label apptFormLabel;
    public Label filterViewLabel;
    public ToggleGroup appointmentView;
    public RadioButton viewMonthlyRadio;
    public RadioButton viewWeeklyRadio;
    public RadioButton viewAllRadio;
    public TableView<Appointment> apptTableView;
    public TableColumn<Appointment, Integer> apptIDColumn;
    public TableColumn<Appointment, String> titleColumn;
    public TableColumn<Appointment, String> descriptionColumn;
    public TableColumn<Appointment, String> locationColumn;
    public TableColumn<Appointment, Integer> contactColumn;
    public TableColumn<Appointment, String> typeColumn;
    public TableColumn<Appointment, LocalDateTime> startColumn;
    public TableColumn<Appointment, LocalDateTime> endColumn;
    public TableColumn<Appointment, Integer> customerIDColumn;
    public TableColumn<Appointment, Integer> userColumn;
    public Button viewAlertButton;
    public Button openCustomerButton;
    public Button addApptButton;
    public Button modApptButton;
    public Button delApptButton;
    public Button logoutButton;



    @FXML
    public void initialize() {
        // Setting cellValueFactory and formats
        apptIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        FormHelper.setupDateTimeColumn(startColumn);  // Formatting datetime to yyyy-MM-dd HH:mm
        endColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        FormHelper.setupDateTimeColumn(endColumn);  // Formatting datetime to yyyy-MM-dd HH:mm
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));

        // Reading appointments from database
        refreshAppointmentTableView();

        // Alert if there is an upcoming appointment
        checkForUpcomingAppointments();

        // Adding a listener that will refresh the table view whenever the window is focused
        Platform.runLater(() -> {
            Stage stage = (Stage) apptTableView.getScene().getWindow();
            stage.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    refreshAppointmentTableView();
                }
            });
        });
    }

    private void filterAppointmentByMonth() {
        // Get midnight of the month's first day and just before midnight on the last day
        LocalDateTime startOfMonth = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay();
        LocalDateTime endOfMonth = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()).atTime(23, 59, 59);

        // Filter appointments that are not between startOfMonth and endOfMonth
        ObservableList<Appointment> filteredAppointments = allAppointments.stream()
                .filter(a -> !a.getStart().isBefore(startOfMonth) && !a.getStart().isAfter(endOfMonth))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        apptTableView.setItems(filteredAppointments);  // Populate table with filtered items
    }

    private void filterAppointmentsByWeek() {
        // Get midnight of the week's first day and just before midnight on the last day
        LocalDateTime startOfWeek = LocalDate.now().with(DayOfWeek.MONDAY).atStartOfDay();
        LocalDateTime endOfWeek = LocalDate.now().with(DayOfWeek.SUNDAY).atTime(23, 59, 59);

        // Filter appointments that are not between startOfWeek and endOfWeek
        ObservableList<Appointment> filteredAppointments = allAppointments.stream()
                .filter(a -> !a.getStart().isBefore(startOfWeek) && !a.getStart().isAfter(endOfWeek))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        apptTableView.setItems(filteredAppointments);  // Populate table with filtered items
    }

    private void checkForUpcomingAppointments() {
        LocalDateTime now = LocalDateTime.now();
        boolean upcomingAppointment = allAppointments.stream()
                .anyMatch((a -> a.getStart().isAfter(now) && a.getStart().isBefore(now.plusMinutes(15))));

        if (upcomingAppointment) {
            // Enable "View Active Alert" button and show alert
            viewAlertButton.setDisable(false);
            showUpcomingAppointmentAlert();
        }
    }

    private void showUpcomingAppointmentAlert() {
        Alerts.showAlert("Upcoming Appointment", "You have an appointment within the next 15 minutes.");
    }

    public void viewAllRadioHandler(ActionEvent actionEvent) {
        apptTableView.setItems(allAppointments);
    }

    public void viewMonthlyRadioHandler(ActionEvent actionEvent) {
        filterAppointmentByMonth();
    }

    public void viewWeeklyRadioHandler(ActionEvent actionEvent) {
        filterAppointmentsByWeek();
    }

    /** Display existing alerts */
    public void viewAlertButtonHandler(ActionEvent actionEvent) {
        showUpcomingAppointmentAlert();
    }

    /** Open the scene that displays the existing customer information. */
    public void openCustomerButtonHandler(ActionEvent actionEvent) {
        SceneSwitcher.switchScene(actionEvent, "/c195/c195assessment/fxml/customerMain.fxml");
    }

    /** Open the form for creating a new appointment. */
    public void addApptButtonHandler(ActionEvent actionEvent) {
        SceneSwitcher.switchScene(actionEvent, "/c195/c195assessment/fxml/addAppointment.fxml");
    }

    /** Open the form for modifying an existing appointment that is selected from the TableView. */
    public void modApptButtonHandler(ActionEvent actionEvent) {
        // Ensure that an appointment from the TableView is selected
        Appointment selectedAppointment = apptTableView.getSelectionModel().getSelectedItem();
        if (selectedAppointment == null) {
            Alerts.showAlert("No Selected Appointment", "Please select an appointment from the table.");
            return;
        }

        // Switch to modifyAppointment.fxml while passing the selected appointment to its controller
        SceneSwitcher.switchSceneWithInfo(actionEvent, "/c195/c195assessment/fxml/modifyAppointment.fxml",
                (ModifyAppointmentController controller) -> {
                    controller.setSelectedAppointment(selectedAppointment);
                    controller.setupFormWithAppointment();
                });
    }

    /** Delete the selected appointment from the database. */
    public void delApptButtonHandler(ActionEvent actionEvent) {
        Appointment selectedAppointment = apptTableView.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null) {
            int deleteIndex = selectedAppointment.getAppointmentID();
            boolean deleteWorked = AppointmentsQuery.deleteByID(deleteIndex);
            if (!deleteWorked) { System.out.println("Failed to delete appointment " + deleteIndex); }
            refreshAppointmentTableView();
        }
        else {
            Alerts.showAlert("No Selected Appointment", "Please select an appointment from the table.");
        }
    }

    /** Return to the login screen and log the current user out. */
    public void logoutButtonHandler(ActionEvent actionEvent) {
        if (!logoutButton.isDisabled()) { logoutButton.setDisable(false); }  // Disable the alert button between logins
        SceneSwitcher.switchScene(actionEvent, "/c195/c195assessment/fxml/login.fxml");
        AppContext.setUser(null);  // Removing the logged-in user from AppContext
    }

    /** Query the database for an updated list of all appointments and display the updated list in the TableView. */
    private void refreshAppointmentTableView() {
        allAppointments = AppointmentsQuery.readAll();
        apptTableView.setItems(allAppointments);
        apptTableView.refresh();
    }
}
