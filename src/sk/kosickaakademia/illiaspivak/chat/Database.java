package sk.kosickaakademia.illiaspivak.chat;

import sk.kosickaakademia.illiaspivak.chat.connect.Connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    public void TestConnection(){
        try{
            Connection connection = getConnection();
            System.out.println("Success");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(Connect.getUrl(),Connect.getUsername(),Connect.getPassword());
        return conn;
    }
}