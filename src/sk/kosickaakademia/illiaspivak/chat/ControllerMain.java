package sk.kosickaakademia.illiaspivak.chat;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ControllerMain {
    public Button SingOut;

    public void LogOutOfTheChat(ActionEvent actionEvent) {
        openAuthorizationWindow();
        exitButtonOnAction(actionEvent);
    }

    private void openAuthorizationWindow(){
        try  {
            Stage newWindow = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
            newWindow.setTitle("Chat");
            newWindow.setScene(new Scene(root, 356, 226));
            newWindow.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void exitButtonOnAction(ActionEvent event){
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }
}
