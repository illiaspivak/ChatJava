package sk.kosickaakademia.illiaspivak.chat;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Controller {
    public TextField enterLogin;
    public PasswordField enterPassword;
    public Button buttonEnter;
    public Label errorEnter;

    public void logInToTheChat(ActionEvent actionEvent) {
        System.out.println("Let's go");
    }
}
