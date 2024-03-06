package c195.c195assessment.helper;

import javafx.scene.control.Alert;

/**
 * This class simplifies the process of alert generation. Currently, it only produces Warning alerts, but the other
 * alert types would be easy to implement.
 */
public abstract class Alerts {
    /**
     * Displays an alert dialog with a WARNING alert type to the user.
     *
     * @param title The title of the alert dialog window.
     * @param content The message to be displayed within the alert.
     */
    public static void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
