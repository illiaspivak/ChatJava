package sk.kosickaakademia.illiaspivak.chat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sk.kosickaakademia.illiaspivak.chat.connect.Information;
import sk.kosickaakademia.illiaspivak.chat.entity.Message;

import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Chat");
        primaryStage.setScene(new Scene(root, 356, 226));
        primaryStage.show();
    }


    public static void main(String[] args) {
//        Database database = new Database();
//        List<Message> messages = database.getMyMessages(Information.getLogin());
//        for (Message message : messages){
//            System.out.println(message.getFrom() + " " + message.getTo() + " " + message.getText());
//        }
        launch(args);
    }
}
