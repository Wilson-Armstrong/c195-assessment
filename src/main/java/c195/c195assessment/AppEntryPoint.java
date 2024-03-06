package c195.c195assessment;

import c195.c195assessment.dao.JDBC;
import c195.c195assessment.helper.AppContext;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class serves as the main entry point for the JavaFX application.
 * It initializes the application's GUI, starting with the login screen, and handles the connection to the database.
 */
public class AppEntryPoint extends Application {

    /**
     * Starts and displays the primary stage of the JavaFX application. It prepares and shows the login screen as the
     * initial view.
     *
     * @param stage The primary stage for this application.
     * @throws IOException If loading the FXML file for the login screen fails.
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Begin program with the login screen
        FXMLLoader fxmlLoader = new FXMLLoader(AppEntryPoint.class.getResource("/c195/c195assessment/fxml/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("C195 Assessment");
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> {
            if (AppContext.getUser() != null) { AppContext.setUser(null); }
        });
        stage.show();
    }

    /**
     * The main method that serves as the entry point for the application.
     * It establishes a connection to the database, launches the JavaFX application, and closes the database connection
     * when the application terminates.
     *
     * @param args The command line arguments passed to the application; they are not used in this application.
     */
    public static void main(String[] args) {
        JDBC.openConnection();
        launch();
        JDBC.closeConnection();
    }
}