package com.example.proiect_poo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class LoggedInController implements Initializable {

    public Label label_user;
//    @FXML
//    private Button button_logout;
//    @FXML
//    private Label label_welcome;
    @FXML
    private Button button_upper_body;
    @FXML
    private Button button_lower_body;
    @FXML
    private Button button_abs;
    @FXML
    private Button button_cardio;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        button_logout.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                DBUtils.changeScene(event, "Main.fxml", "Log in!", null);
//            }
//        });

        button_upper_body.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeSceneTraining(event, "workout_page.fxml", "Here is your selected workout!", button_upper_body.getText());
            }
        });

        button_lower_body.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeSceneTraining(event, "workout_page.fxml", "Here is your selected workout!", button_lower_body.getText());
            }
        });

        button_abs.setOnAction(event -> DBUtils.changeSceneTraining(event, "workout_page.fxml", "Here is your selected workout!", button_abs.getText()));

        button_cardio.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeSceneTraining(event, "workout_page.fxml", "Here is your selected workout!", button_cardio.getText());
            }
        });
    }

    public void setUserInformation(String username) {
        System.out.println("setUserInformation called with username: " + username);
            label_user.setText(username + "!");

//            System.out.println("label_welcome is null");

    }
}

