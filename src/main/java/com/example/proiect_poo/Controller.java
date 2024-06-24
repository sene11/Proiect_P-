package com.example.proiect_poo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TextField tf_username;
//    @FXML
//    private TextField tf_email; // New email field
    @FXML
    private PasswordField password_field;
    @FXML
    private Button button_login;
    @FXML
    private Button button_sign_up;
    @FXML
    private Button button_forgot_password;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        button_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.logInUser(event, tf_username.getText(), password_field.getText());
            }
        });

        button_sign_up.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "sign-up.fxml", "Sign Up!", null);
            }
        });

        button_forgot_password.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleForgotPassword(event);
            }
        });
    }

    @FXML
    private void handleForgotPassword(ActionEvent event) {
        DBUtils.changeScene(event, "forgot-password.fxml", "Forgot Password", null);
    }
}
