package sk.kosickaakademia.illiaspivak.chat;

import sk.kosickaakademia.illiaspivak.chat.connect.Connect;
import sk.kosickaakademia.illiaspivak.chat.util.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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

    public boolean insertNewUser(String login, String password) {
        if (login == null || login.equals("")) {
            System.out.println("You need to enter your username");
            return false;
        }
        if (password == null || password.length() < 5){
            System.out.println("The password is too short");
            return false;
        }
        String PasswordMD5 = new Util().getMD5(password);
        String addNewUser = "INSERT INTO user (login, password) VALUES (?,?)";
        try {
            Connection connection = getConnection();
            if (connection == null) {
                System.out.println("Error! No connection");
                return false;
            }
            PreparedStatement ps = connection.prepareStatement(addNewUser);
            ps.setString(1, login);
            ps.setString(2, PasswordMD5);
            int result = ps.executeUpdate();
            connection.close();
            if (result == 0) {
                System.out.println("Error");
                return false;
            }
            else {
                System.out.println("User " + login + " has been added to the chat");
                return true;
            }
        } catch (Exception ex) {
        }
        return true;
    }
}
