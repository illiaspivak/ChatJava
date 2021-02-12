package sk.kosickaakademia.illiaspivak.chat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sk.kosickaakademia.illiaspivak.chat.util.Util;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
//        Database database = new Database();
//        database.TestConnection();
//        launch(args);
        Util util = new Util();
        String example = "example";
        System.out.println(util.getMD5(example));
    }
}