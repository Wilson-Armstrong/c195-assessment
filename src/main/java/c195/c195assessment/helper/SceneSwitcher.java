package c195.c195assessment.helper;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * Provides static utility methods for switching between scenes in a JavaFX application.
 * This class simplifies the process of loading new scenes from FXML files and optionally passing information to the
 * controller of the new scene.
 */
public class SceneSwitcher {

    /**
     * Changes the current scene by loading the specified FXML file. This method retrieves the event's source stage and
     * replaces its scene with the one defined in the FXML file.
     *
     * @param actionEvent The {@link ActionEvent} that triggered the scene switch. Used to retrieve the source stage for
     *                   the scene transition.
     * @param fxmlPath The path to the FXML file that defines the new scene.
     */
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

    /**
     * Switches to a new scene defined by the specified FXML file and allows for passing information to the controller
     * of the new scene. This method facilitates communication between scenes by enabling the setup of the new scene's
     * controller before the scene is displayed.
     *
     * @param actionEvent The {@link ActionEvent} that triggered the scene switch. Used to retrieve the source stage
     *                    for the scene transition.
     * @param fxmlPath The path to the FXML file that defines the new scene.
     * @param setupMethod A {@link Consumer} that accepts the controller of the new scene allowing for the setup of
     *                    necessary data prior to displaying the scene.
     * @param <T> The type of the controller for the new scene. This allows the setupMethod to work with the specific
     *           type of the controller.
     */
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
