package c195.c195assessment.controller;

import c195.c195assessment.dao.AppointmentsQuery;
import c195.c195assessment.helper.*;
import c195.c195assessment.model.Appointment;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.stream.Collectors;

/**
 * Controller for the main appointment view, handling the display and manipulation of appointments, filtering views,
 * and navigating to appointment-related actions.
 */
public class ApptMainController {
    private ObservableList<Appointment> allAppointments;  // List of appointments in the table

    // FXML elements
    public ToggleGroup appointmentView;
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
    public Button logoutButton;

    /**
     * Initializes the controller. This method sets up the table columns, loads and displays all appointments from the
     * database, sets up any necessary listeners, and checks for upcoming appointments.
     */
    @FXML
    public void initialize() {
        // Setting up cellValueFactory and formats
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

    /**
     * Filters and displays appointments occurring within the current month.
     */
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

    /**
     * Filters and displays appointments occurring within the current week.
     */
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

    /**
     * Checks for any appointments starting within the next 15 minutes and alerts the user if any are found.
     */
    private void checkForUpcomingAppointments() {
        LocalDateTime now = LocalDateTime.now();
        boolean upcomingAppointment = allAppointments.stream().anyMatch(a -> {
            LocalDateTime startTime = TimeZoneConversion.utcToLocal(a.getStart());
            return (startTime.isAfter(now) && startTime.isBefore(now.plusMinutes(15))  // Appointment begins within 15 minutes
                    && a.getUserID() == AppContext.getUser().getUserId());  // The appointment is with the logged-in user
        });

        if (upcomingAppointment) {
            // Enable "View Active Alert" button and show alert
            viewAlertButton.setDisable(false);
            showUpcomingAppointmentAlert();
        }
    }

    /**
     * Displays an alert to the user about an upcoming appointment.
     */
    private void showUpcomingAppointmentAlert() {
        Alerts.showAlert("Upcoming Appointment", "You have an appointment within the next 15 minutes.");
    }

    /**
     * Handles action on the "View All" radio button to display all appointments.
     *
     */
    public void viewAllRadioHandler() {
        apptTableView.setItems(allAppointments);
    }

    /**
     * Handles action on the "View Monthly" radio button to filter and display appointments for the current month.
     *
     */
    public void viewMonthlyRadioHandler() {
        filterAppointmentByMonth();
    }

    /**
     * Handles action on the "View Weekly" radio button to filter and display appointments for the current week.
     *
     */
    public void viewWeeklyRadioHandler() {
        filterAppointmentsByWeek();
    }

    /**
     * Displays existing alerts to the user, such as upcoming appointments.
     *
     */
    public void viewAlertButtonHandler() {
        showUpcomingAppointmentAlert();
    }

    /**
     * Handles action on the "Open Customer View" button. Switches the scene to display the customer management view
     * where users can view, add, modify, and delete customer records.
     *
     * @param actionEvent The event that triggered the method.
     */
    public void openCustomerButtonHandler(ActionEvent actionEvent) {
        SceneSwitcher.switchScene(actionEvent, "/c195/c195assessment/fxml/customerMain.fxml");
    }

    /**
     * Handles action on the "Add Appointment" button. Switches the scene to the form for adding a new appointment to
     * the schedule.
     *
     * @param actionEvent The event that triggered the method.
     */
    public void addApptButtonHandler(ActionEvent actionEvent) {
        SceneSwitcher.switchScene(actionEvent, "/c195/c195assessment/fxml/addAppointment.fxml");
    }

    /**
     * Handles action on the "Modify Appointment" button. Switches the scene to the form for modifying an existing
     * appointment. The user must select an appointment from the table view before modifying.
     *
     * @param actionEvent The event that triggered the method.
     */
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

    /**
     * Handles action on the "Delete Appointment" button. Deletes the selected appointment from the database after
     * confirming the action with the user. The user must select an appointment from the table view before deletion.
     *
     */
    public void delApptButtonHandler() {
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

    /**
     * Handles action on the "Logout" button. Logs out the current user, clears any session data, and returns to the
     * login screen.
     *
     * @param actionEvent The event that triggered the method.
     */
    public void logoutButtonHandler(ActionEvent actionEvent) {
        if (!logoutButton.isDisabled()) { logoutButton.setDisable(false); }  // Disable the alert button between logins
        SceneSwitcher.switchScene(actionEvent, "/c195/c195assessment/fxml/login.fxml");
        AppContext.setUser(null);  // Removing the logged-in user from AppContext
    }

    /**
     * Queries the database for an updated list of all appointments and refreshes the TableView to display the updated
     * list.
     */
    private void refreshAppointmentTableView() {
        allAppointments = AppointmentsQuery.readAll();
        apptTableView.setItems(allAppointments);
        apptTableView.refresh();
    }
}
