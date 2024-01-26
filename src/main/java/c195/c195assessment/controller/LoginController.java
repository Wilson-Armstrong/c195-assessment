package c195.c195assessment.controller;

import c195.c195assessment.dao.UsersQuery;
import c195.c195assessment.helper.AppContext;
import c195.c195assessment.helper.Alerts;
import c195.c195assessment.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public class LoginController {
    public Label formLabel; // Large text at the top of the form
    public Label usernameLabel; // Identifies the username field
    public TextField usernameField; // Contains the password that will be used to log in.
    public Label passwordLabel; // Identifies the password field
    public PasswordField passwordField; // Contains the password that will be used to log in.
    public Label locationLabel; // Identifies locationValueLabel as a location
    public Label locationValueLabel; // Displays the detected location.
    public Button loginButton;  // Attempts to log in with the details in usernameField and passwordField.

    @FXML
    public void initialize() {
        ResourceBundle rb1 = ResourceBundle.getBundle("c195.c195assessment.localization.messages", AppContext.getInstance().getUserLocale());
        formLabel.setText(rb1.getString("login.form.label"));
        usernameLabel.setText(rb1.getString("login.username.label"));
        passwordLabel.setText(rb1.getString("login.password.label"));
        locationLabel.setText(rb1.getString("login.location.label"));
        locationValueLabel.setText(rb1.getString("login.locale.value"));
        loginButton.setText(rb1.getString("login.button"));
    }

    public void loginButtonHandler(ActionEvent event){
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty()) {
            Alerts.showAlert("Login Error", "Username is required.");
            return;
        }
        else if (password.isEmpty()) {
            Alerts.showAlert("Login Error", "Password is required.");
            return;
        }

        boolean isMatchFound = UsersQuery.loginVerification(username, password);
        if (isMatchFound) {
            // Login success
            User loggedUser = UsersQuery.readByUsername(username);
            AppContext.setUser(loggedUser);

            try {
                // Load next FXML file
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/c195/c195assessment/fxml/appointmentMain.fxml"));
                Parent root = loader.load();

                // Get the current stage from the event source
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                // Set the new scene
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                System.out.println("FXML Loading error: " + e.getMessage());
                e.printStackTrace();
            }


        }
        else {
            // Login failed
            Alerts.showAlert("Login Failed", "Invalid username or password.");
        }
    }
}