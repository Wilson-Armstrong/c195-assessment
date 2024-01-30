package c195.c195assessment.helper;

import c195.c195assessment.controller.ModifyAppointmentController;
import c195.c195assessment.model.Appointment;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/** This class's methods help with various aspects of using the add/modify forms. */
public abstract class FormHelper {

    /** Get the LocalDateTime represented by the Date in a DatePicker and the Time in a ComboBox. */
    public static LocalDateTime getDateTimeFromForm(DatePicker datePicker, ComboBox<String> timeComboBox) {
        LocalDate selectedDate = datePicker.getValue();
        String selectedTimeStr = timeComboBox.getValue();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime selectedTime = LocalTime.parse(selectedTimeStr, timeFormatter);
        return LocalDateTime.of(selectedDate, selectedTime);
    }

    /** Fill a ComboBox with the hourly options starting from the startHour and ending with the endHour. */
    public static void populateTimeComboBoxes(ComboBox<String> comboBox, int startHour, int endHour) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        for (int hour = startHour; hour <= endHour; hour++) {
            LocalTime time = LocalTime.of(hour, 0);
            String formattedTime = time.format(formatter);
            comboBox.getItems().add(formattedTime);
        }
    }

    /** Adds a format of "yyyy-MM-dd HH:mm" to a TableView's LocalDateTime column. */
    public static <T> void setupDateTimeColumn(TableColumn<T, LocalDateTime> column) {
        column.setCellFactory(col -> new TableCell<>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    // Convert the database's UTC time to the user's timezone
                    LocalDateTime localDateTime = TimeZoneConversion.convertTimeZone(item, ZoneId.of("UTC"),
                            AppContext.getUserTimeZone().toZoneId());

                    setText(formatter.format(localDateTime));  // Add the formatting
                }
            }
        });
    }

    public void switchFXMLFile(ActionEvent actionEvent, String filepath) {
        try {
            // Load next FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource(filepath));
            Parent root = loader.load();

            // Create the new stage and show it
            Stage loginStage = new Stage();
            loginStage.setScene(new Scene(root));
            loginStage.show();
        } catch (IOException e) {
            System.out.println("FXML Loading error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
