package sk.kosickaakademia.illiaspivak.chat;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sk.kosickaakademia.illiaspivak.chat.Database;
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
                openMainWindow();
                exitButtonOnAction(actionEvent);
                System.out.println("Welcome to the chat!");
            }
        }
    }

    private void openMainWindow(){
        try  {
            Stage newWindow = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
            newWindow.setTitle("Chat");
            newWindow.setScene(new Scene(root, 500.0, 500.0));
            newWindow.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void exitButtonOnAction(ActionEvent event){
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }
}
