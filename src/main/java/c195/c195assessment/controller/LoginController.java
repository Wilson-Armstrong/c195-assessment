package c195.c195assessment.controller;

import c195.c195assessment.dao.UsersQuery;
import c195.c195assessment.helper.AppContext;
import c195.c195assessment.helper.Alerts;
import c195.c195assessment.helper.SceneSwitcher;
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

/**
 * Controls the login view, handling user authentication and localization of UI elements.
 */
public class LoginController {
    public Label formLabel; // Large text at the top of the form
    public Label usernameLabel; // Identifies the username field
    public TextField usernameField; // Contains the password that will be used to log in.
    public Label passwordLabel; // Identifies the password field
    public PasswordField passwordField; // Contains the password that will be used to log in.
    public Label locationLabel; // Identifies locationValueLabel as a location
    public Label locationValueLabel; // Displays the detected location.
    public Button loginButton;  // Attempts to log in with the details in usernameField and passwordField.

    /**
     * Initializes the controller class. This method is automatically called after the FXML file has been loaded. It
     * localizes the login form's UI elements based on the user's locale.
     */
    @FXML
    public void initialize() {
        ResourceBundle rb1 = ResourceBundle.getBundle("c195.c195assessment.localization.messages", AppContext.getUserLocale());
        formLabel.setText(rb1.getString("login.form.label"));
        usernameLabel.setText(rb1.getString("login.username.label"));
        passwordLabel.setText(rb1.getString("login.password.label"));
        locationLabel.setText(rb1.getString("login.location.label"));
        locationValueLabel.setText(rb1.getString("login.locale.value"));
        loginButton.setText(rb1.getString("login.button"));
    }

    /**
     * Handles the login button click event. Validates the user's login credentials against the database. If the
     * credentials are valid, the user is logged in and the main application screen is displayed. Otherwise, an error
     * message is shown.
     *
     * @param actionEvent The event that triggered the method.
     */
    public void loginButtonHandler(ActionEvent actionEvent){
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

            // Open main appointment window
            SceneSwitcher.switchScene(actionEvent, "/c195/c195assessment/fxml/appointmentMain.fxml");
        }
        else {
            // Login failed
            Alerts.showAlert("Login Failed", "Invalid username or password.");
        }
    }
}
