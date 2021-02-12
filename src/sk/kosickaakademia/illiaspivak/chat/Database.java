package sk.kosickaakademia.illiaspivak.chat;

import sk.kosickaakademia.illiaspivak.chat.connect.Connect;
import sk.kosickaakademia.illiaspivak.chat.entity.User;
import sk.kosickaakademia.illiaspivak.chat.util.Util;

import java.sql.*;

public class Database {
    private Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(Connect.getUrl(),Connect.getUsername(),Connect.getPassword());
        return conn;
    }

    public void TestConnection(){
        try{
            Connection connection = getConnection();
            System.out.println("Success");
        }catch(Exception e){
            e.printStackTrace();
        }
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

    public User loginUser(String login, String password){
        if (login == null || login.equals("")) {
            System.out.println("You need to enter your username");
            return null;
        }
        if (password == null || password.length() < 5){
            System.out.println("The password is too short");
            return null;
        }
        String hashPassword = new Util().getMD5(password);
        String findUser = "Select * FROM user Where login LIKE ? and password LIKE ?";
        try {
            Connection con = getConnection();
            if (con == null) {
                System.out.println("Error! No connection");
                return null;
            }
            PreparedStatement ps = con.prepareStatement(findUser);                                                         ;
            ps.setString(1, login);
            ps.setString(2, hashPassword);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("You are logged in to the chat");
                int id = rs.getInt("id");
                User user = new User(id, login, hashPassword);
                con.close();
                return user;
            } else {
                con.close();
                System.out.println("Error! Invalid credentials");
                return null;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
