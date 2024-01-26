package c195.c195assessment;

import c195.c195assessment.dao.JDBC;
import c195.c195assessment.helper.AppContext;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AppEntryPoint extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Begin program with the login screen
        FXMLLoader fxmlLoader = new FXMLLoader(AppEntryPoint.class.getResource("/c195/c195assessment/fxml/login.fxml"));
        // FXMLLoader fxmlLoader = new FXMLLoader(AppEntryPoint.class.getResource("/c195/c195assessment/fxml/appointmentMain.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("C195 Assessment");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        // Detect user's settings
        AppContext appContext = AppContext.getInstance();

        // Choose ResourceBundle based on user's Locale
        //ResourceBundle rb1 = ResourceBundle.getBundle("c195.c195assessment.localization.messages", appContext.getUserLocale());

        JDBC.openConnection();
        launch();
        JDBC.closeConnection();
    }
}