package sk.kosickaakademia.illiaspivak.chat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sk.kosickaakademia.illiaspivak.chat.connect.Information;
import sk.kosickaakademia.illiaspivak.chat.entity.Message;
import sk.kosickaakademia.illiaspivak.chat.entity.User;

import java.util.List;

public class ControllerMain{
    public Button SingOut;
    public Label userName;
    public TextField messageTo;
    public TextArea textMessage;
    public Button buttonSend;
    public ListView viewMessages;
    public Button buttonView;
    public ComboBox selectARecipient;
    private User user;


    Database database = new Database();

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

    public void setUser(User user){
        this.user=user;
    }

    public void initUserName() {
        userName.setText(user.getLogin());
    }


    public void sendMessage(ActionEvent actionEvent) {
        String fromUser = user.getLogin();
        String toUser = messageTo.getText().trim();
        String text = textMessage.getText().trim();
        database.sendMessage(fromUser,toUser,text);
    }

    public void viewTheMessage(ActionEvent actionEvent) {
        List<Message> messages = database.getMyMessages(user.getLogin());
        if(messages.isEmpty()){
            return;
        }
        for(Message message: messages){
            viewMessages.getItems().add(message.getDt());
            viewMessages.getItems().add(message.getFrom());
            viewMessages.getItems().add(message.getText());
            viewMessages.getItems().add("--------------------------------------------------------------------------");
        }
    }

    public void ChangePassword(ActionEvent actionEvent) {
        openChangePasswordWindow();
    }

    private void openChangePasswordWindow(){
        try  {
            Stage newWindow = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("additionalwindows/changePassword.fxml"));
            newWindow.setTitle("Change Password");
            newWindow.setScene(new Scene(root, 257, 264));
            newWindow.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        selectARecipient.getItems().setAll(database.getUsers());
    }

    public void SelectARecipient(ActionEvent actionEvent) {
        messageTo.setText((String) selectARecipient.getValue());
    }

    public void deleteMessages(ActionEvent actionEvent) {
        database.deleteAllMyMessages(user.getLogin());
    }

    public void getSmile(ActionEvent actionEvent) {
        textMessage.appendText("\uD83D\uDE0A");
    }

    public void getHeart(ActionEvent actionEvent) {
        textMessage.appendText("❤");
    }

    public void getLaughter(ActionEvent actionEvent) {
        textMessage.appendText("\uD83D\uDE02");
    }

    public void getApproval(ActionEvent actionEvent) {
        textMessage.appendText("\uD83D\uDE14");
    }

    public void getSadness(ActionEvent actionEvent) {
        textMessage.appendText("\uD83D\uDC4D");
    }

    public void getSnowman(ActionEvent actionEvent) {
        textMessage.appendText("⛄");
    }

    public void getCoffee(ActionEvent actionEvent) {
        textMessage.appendText("☕");
    }

    public void getFire(ActionEvent actionEvent) {
        textMessage.appendText("\uD83D\uDD25");
    }

    public void getPenguin(ActionEvent actionEvent) {
        textMessage.appendText("\uD83D\uDC27");
    }
}
