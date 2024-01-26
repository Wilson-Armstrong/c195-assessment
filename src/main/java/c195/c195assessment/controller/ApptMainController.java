package c195.c195assessment.controller;

import c195.c195assessment.dao.AppointmentsQuery;
import c195.c195assessment.helper.Alerts;
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
        // Reading appointments from database
        allAppointments = AppointmentsQuery.readAll();

        // Populating the table with existing appointments
        apptIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        setupDateTimeColumn(startColumn);  // Formatting datetime to yyyy-MM-dd HH:mm
        endColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        setupDateTimeColumn(endColumn);  // Formatting datetime to yyyy-MM-dd HH:mm
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));
        apptTableView.setItems(allAppointments);

        // Alert if there is an upcoming appointment
        checkForUpcomingAppointments();

        // Adding a listener that will refresh the table view whenever the window is focused
        Platform.runLater(() -> {
            Stage stage = (Stage) apptTableView.getScene().getWindow();
            stage.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    ObservableList<Appointment> appointments = AppointmentsQuery.readAll();
                    apptTableView.setItems(appointments);
                    apptTableView.refresh();
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

    public void viewAlertButtonHandler(ActionEvent actionEvent) {
        showUpcomingAppointmentAlert();
    }

    public void openCustomerButtonHandler(ActionEvent actionEvent) {
        try {
            // Load next FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/c195/c195assessment/fxml/customerMain.fxml"));
            Parent root = loader.load();

            // Get the current stage from the event source
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Set the new scene
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("FXML Loading error: " + e.getMessage());
        }
    }

    public void addApptButtonHandler(ActionEvent actionEvent) {
        try {
            // Load next FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/c195/c195assessment/fxml/addAppointment.fxml"));
            Parent root = loader.load();

            // Get the current stage from the event source
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Set the new scene
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("FXML Loading error: " + e.getMessage());
        }
    }

    public void modApptButtonHandler(ActionEvent actionEvent) {
        Appointment selectedAppointment = apptTableView.getSelectionModel().getSelectedItem();
        if (selectedAppointment == null) {
            Alerts.showAlert("No Selected Appointment", "Please select an appointment from the table.");
            return;
        }

        try {
            // Load next FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/c195/c195assessment/fxml/modifyAppointment.fxml"));
            Parent root = loader.load();

            // Passing the selected appointment to the new scene's controller and populating the form's fields
            ModifyAppointmentController modifyAppointmentController = loader.getController();
            modifyAppointmentController.setSelectedAppointment(selectedAppointment);
            modifyAppointmentController.setupFormWithAppointment();

            // Create the new stage and show it
            Stage loginStage = new Stage();
            loginStage.setScene(new Scene(root));
            loginStage.show();
        } catch (IOException e) {
            System.out.println("FXML Loading error: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public void delApptButtonHandler(ActionEvent actionEvent) {
        Appointment selectedAppointment = apptTableView.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null) {
            int deleteIndex = selectedAppointment.getAppointmentID();
            boolean deleteWorked = AppointmentsQuery.deleteByID(deleteIndex);
            if (!deleteWorked) { System.out.println("Failed to delete appointment " + deleteIndex); }
            allAppointments.remove(selectedAppointment);
            apptTableView.refresh();
        } else {
            Alerts.showAlert("No Selected Appointment", "Please select an appointment from the table.");
        }
    }

    public void logoutButtonHandler(ActionEvent actionEvent) {
        // Close current window
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.close();

        // Open login.fxml
        try {
            // Load next FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/c195/c195assessment/fxml/login.fxml"));
            Parent root = loader.load();

            // Create the new stage and show it
            Stage loginStage = new Stage();
            loginStage.setScene(new Scene(root));
            loginStage.show();
        } catch (IOException e) {
            System.out.println("FXML Loading error: " + e.getMessage());
        }
    }

    /** Adds a format to a TableView's column that displays a LocalDateTime value. */
    private void setupDateTimeColumn(TableColumn<Appointment, LocalDateTime> column) {
        column.setCellFactory(col -> new TableCell<>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(formatter.format(item));
                }
            }
        });
    }
}
