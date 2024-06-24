package com.example.proiect_poo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController  implements Initializable {

    public TextField email;
    @FXML
    private Button button_signup;
    @FXML
    private Button button_log_in;

    @FXML
    private RadioButton rb_male;
    @FXML
    private RadioButton rb_female;
    @FXML
    private RadioButton rb_prefer_not_to_say;

    @FXML
    private TextField tf_username;
    @FXML
    private TextField tf_password;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup toggleGroup = new ToggleGroup();
        rb_male.setToggleGroup(toggleGroup);
        rb_female.setToggleGroup(toggleGroup);
        rb_prefer_not_to_say.setToggleGroup(toggleGroup);

        rb_male.setSelected(true);

        button_signup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String toggleName = ((RadioButton) toggleGroup.getSelectedToggle()).getText();

                if (tf_username.getText().trim().isEmpty() || tf_password.getText().trim().isEmpty()){
                    System.out.println("Please fill in all the information!");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill in all the information!");
                    alert.show();
                } else {
                    DBUtils.signUpUser(event, tf_username.getText(), tf_password.getText(), toggleName, email.getText());
                }
            }
        });

        button_log_in.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "Main.fxml", "Log In!", null);

            }
        });


    }
}
