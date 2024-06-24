package com.example.proiect_poo;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class ForgotPasswordController {

    @FXML
    private TextField tf_email;

    @FXML
    private void handleResetPassword(ActionEvent event) {
        String email = tf_email.getText().trim();
        if (email.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter your email!");
            alert.show();
        } else {
            DBUtils.resetPassword(email);
        }
    }

    @FXML
    private void handleBackToLogin(ActionEvent event) {
        DBUtils.changeScene(event, "Main.fxml", "Log In!", null);
    }
}
