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
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("C195 Assessment");
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> {
            if (AppContext.getUser() != null) { AppContext.setUser(null); }
        });
        stage.show();
    }

    public static void main(String[] args) {
        JDBC.openConnection();
        launch();
        JDBC.closeConnection();
    }
}