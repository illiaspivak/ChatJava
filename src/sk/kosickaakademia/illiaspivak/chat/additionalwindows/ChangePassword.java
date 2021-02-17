package sk.kosickaakademia.illiaspivak.chat.additionalwindows;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sk.kosickaakademia.illiaspivak.chat.Database;

public class ChangePassword {
    public TextField login;
    public PasswordField password;
    public PasswordField newPassword1;
    public PasswordField newPassword2;
    public Button change;
    public Label error;
    public Label incorrect;


    public void ChangeThisPassword(ActionEvent actionEvent) {
        String username = login.getText().trim();
        String oldPassword = password.getText().trim();
        String newPass1 = newPassword1.getText().trim();
        String newPass2 = newPassword2.getText().trim();
        if(newPass1!=newPass2){
            error.setVisible(true);
        }
        Database database = new Database();
        if(!database.changePassword(username,oldPassword,newPass1)){
            incorrect.setVisible(true);
        }else{
            exitButtonOnAction(actionEvent);
        }
    }

    private void exitButtonOnAction(ActionEvent event){
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }
}
