package c195.c195assessment.helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * Contains utility methods for manipulating and retrieving data from form elements within the application.
 * This includes converting date and time selections into {@link LocalDateTime} objects, populating {@link ComboBox}
 * with time options, formatting date and time for display in {@link TableColumn}, and validating form inputs.
 */
public abstract class FormHelper {

    /**
     * Combines the selected date from a {@link DatePicker} and the selected time from a {@link ComboBox} into a
     * {@link LocalDateTime} object.
     *
     * @param datePicker The {@link DatePicker} from which to retrieve the selected date.
     * @param timeComboBox The {@link ComboBox} from which to retrieve the selected time as a string.
     * @return The combined {@link LocalDateTime} representation of the selected date and time.
     */
    public static LocalDateTime getDateTimeFromForm(DatePicker datePicker, ComboBox<String> timeComboBox) {
        LocalDate selectedDate = datePicker.getValue();
        String selectedTimeStr = timeComboBox.getValue();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(Constants.TIME_FORMAT);
        LocalTime selectedTime = LocalTime.parse(selectedTimeStr, timeFormatter);
        return LocalDateTime.of(selectedDate, selectedTime);
    }

    /**
     * Populates a {@link ComboBox} with time options at a specified interval within business hours, converted to the
     * user's local time zone.
     *
     * @param comboBox The {@link ComboBox} to populate.
     * @param intervalInMinutes The interval, in minutes, at which to generate time options.
     */
    public static void populateTimeComboBoxes(ComboBox<String> comboBox, int intervalInMinutes) {

        // Define the hours of operation
        ZonedDateTime openTime = ZonedDateTime.of(LocalDate.now(), Constants.OPEN_HOUR, Constants.BUSINESS_TIME_ZONE);
        ZonedDateTime closeTime = ZonedDateTime.of(LocalDate.now(), Constants.CLOSE_HOUR, Constants.BUSINESS_TIME_ZONE);

        // Convert the hours of operation to the user's time zone
        ZonedDateTime openTimeUser = openTime.withZoneSameInstant(AppContext.getUserTimeZone().toZoneId());
        ZonedDateTime closeTimeUser = closeTime.withZoneSameInstant(AppContext.getUserTimeZone().toZoneId());

        // Prepare items for formatted time creation
        ObservableList<String> formattedTimes = FXCollections.observableArrayList();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.TIME_FORMAT);
        ZonedDateTime currentTime = openTimeUser;

        while (!currentTime.isAfter(closeTimeUser)) {
            formattedTimes.add(currentTime.format(formatter));
            currentTime = currentTime.plusMinutes(intervalInMinutes);
        }

        comboBox.setItems(formattedTimes);
    }

    /**
     * Configures a {@link TableColumn} to display {@link LocalDateTime} values formatted according to a specific
     * pattern.
     *
     * @param column The {@link TableColumn} to configure.
     * @param <T> The type of the items contained within the table view.
     */
    public static <T> void setupDateTimeColumn(TableColumn<T, LocalDateTime> column) {
        column.setCellFactory(col -> new TableCell<>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd "
                    + Constants.TIME_FORMAT);

            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    // Convert the database's UTC time to the user's timezone
                    LocalDateTime localDateTime = TimeZoneConversion.utcToLocal(item);

                    setText(formatter.format(localDateTime));  // Add the formatting
                }
            }
        });
    }


    /**
     * Displays an alert instructing a user to remedy an empty input field.
     *
     * @param inputName The name of the field that the user has left empty.
     */
    private static void emptyInputAlert(String inputName) {
        Alerts.showAlert("Empty input field", "Please provide a " + inputName + '.');
    }

    /**
     * Validates whether a {@link TextField} has been left empty and displays an alert if so.
     *
     * @param textField The {@link TextField} to check.
     * @param inputName The name of the input field, used for generating the alert message.
     * @return {@code true} if the input is empty, {@code false} otherwise.
     */
    public static boolean isInputEmpty(TextField textField, String inputName) {
        if (textField.getText().isEmpty()) {
            emptyInputAlert(inputName);
            return true;
        }
        return false;
    }

    /**
     * Validates whether a {@link DatePicker} has been left without a selected date and displays an alert if so.
     *
     * @param datePicker The {@link DatePicker} to check.
     * @param inputName The name of the input field, used for generating the alert message.
     * @return {@code true} if no date is selected, {@code false} otherwise.
     */
    public static boolean isInputEmpty(DatePicker datePicker, String inputName) {
        if (datePicker.getValue() == null) {
            emptyInputAlert(inputName);
            return true;
        }
        return false;
    }

    /**
     * Validates whether a {@link ComboBox} has been left without a selected option and displays an alert if so.
     *
     * @param comboBox The {@link ComboBox} to check.
     * @param inputName The name of the input field, used for generating the alert message.
     * @param <T> The type of the items contained within the combo box.
     * @return {@code true} if no option is selected, {@code false} otherwise.
     */
    public static <T> boolean isInputEmpty(ComboBox<T> comboBox, String inputName) {
        if (comboBox.getValue() == null) {
            emptyInputAlert(inputName);
            return true;
        }
        return false;
    }

    /**
     * Validates whether a {@link ChoiceBox} has been left without a selected option and displays an alert if so.
     *
     * @param choiceBox The {@link ChoiceBox} to check.
     * @param inputName The name of the input field, used for generating the alert message.
     * @param <T> The type of the items contained within the choice box.
     * @return {@code true} if no option is selected, {@code false} otherwise.
     */
    public static <T> boolean isInputEmpty(ChoiceBox<T> choiceBox, String inputName) {
        if (choiceBox.getValue() == null) {
            emptyInputAlert(inputName);
            return true;
        }
        return false;
    }
}
