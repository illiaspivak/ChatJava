package sk.kosickaakademia.illiaspivak.chat.additionalwindows;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sk.kosickaakademia.illiaspivak.chat.Database;
import sk.kosickaakademia.illiaspivak.chat.entity.User;

public class ChangePassword {
    public TextField login;
    public PasswordField password;
    public PasswordField newPassword1;
    public PasswordField newPassword2;
    public Button change;
    public Label error;
    public Label incorrect;
    public Label enterNewPassword;


    public void ChangeThisPassword(ActionEvent actionEvent) {
        String username = login.getText().trim();
        String oldPassword = password.getText().trim();
        String newPass1 = newPassword1.getText().trim();
        String newPass2 = newPassword2.getText().trim();
        Database database = new Database();
        User user = database.loginUser(username,oldPassword);
        if(user==null){
            incorrect.setVisible(true);
        }else {
            if(newPass1.length() == 0 || newPass2.length() == 0){
                enterNewPassword.setVisible(true);
                incorrect.setVisible(false);
            }else {
                if (newPass1 != newPass2) {
                    error.setVisible(true);
                    incorrect.setVisible(false);
                    enterNewPassword.setVisible(false);
                } else {
                    database.changePassword(username, oldPassword, newPass1);
                    exitButtonOnAction(actionEvent);
                }
            }
        }
    }

    private void exitButtonOnAction(ActionEvent event){
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }
}
