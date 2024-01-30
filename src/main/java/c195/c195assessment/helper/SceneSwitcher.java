package c195.c195assessment.helper;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.Consumer;

/** Handles transitions between scenes. */
public class SceneSwitcher {
    /** Change the current scene for the specified FXML file. */
    public static void switchScene(ActionEvent actionEvent, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneSwitcher.class.getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("FXML Loading error: " + e.getMessage());
        }
    }

    /** Switch to a new scene while passing information to the new controller */
    public static <T> void switchSceneWithInfo(ActionEvent actionEvent, String fxmlPath, Consumer<T> setupMethod) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneSwitcher.class.getResource(fxmlPath));
            Parent root = loader.load();

            // Assuming the controller has a method to set up data
            T controller = loader.getController();
            setupMethod.accept(controller);

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("FXML Loading error: " + e.getMessage());
        }
    }
}
