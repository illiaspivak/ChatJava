package sk.kosickaakademia.illiaspivak.chat;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sk.kosickaakademia.illiaspivak.chat.entity.User;

public class Controller {
    public TextField enterLogin;
    public PasswordField enterPassword;
    public Button buttonEnter;
    public Label errorEnter;

    public void logInToTheChat(ActionEvent actionEvent) {
        System.out.println("Let's go");
        String login = enterLogin.getText().trim();
        String password = enterPassword.getText().trim();
        if(login.length()>0 && password.length()>0){
            Database database = new Database();
            User user = database.loginUser(login,password);
            if(user==null) {
                errorEnter.setVisible(true);
            }else{
                System.out.println("Welcome to the chat!");
            }
        }
    }
}
