package sk.kosickaakademia.illiaspivak.chat.additionalwindows;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sk.kosickaakademia.illiaspivak.chat.Database;
import sk.kosickaakademia.illiaspivak.chat.entity.User;

public class Registration {
    public TextField login;
    public PasswordField password1;
    public PasswordField password2;
    public Button buttonCreate;
    public Label error;
    public Label enterLogin;

    public void CreateNewAccount(ActionEvent actionEvent) {
        String username = login.getText().trim();
        String pass1 = password1.getText().trim();
        String pass2 = password2.getText().trim();
        if(pass1!=pass2){
            error.setVisible(true);
        }else {
            if (username.length() > 0 && pass1.length() > 0) {
                Database database = new Database();
                database.insertNewUser(username, pass1);
                exitButtonOnAction(actionEvent);
            } else {
                enterLogin.setVisible(true);
            }
        }
    }

    private void exitButtonOnAction(ActionEvent event){
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }
}
