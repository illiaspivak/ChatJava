package sk.kosickaakademia.illiaspivak.chat;

import sk.kosickaakademia.illiaspivak.chat.connect.Connect;
import sk.kosickaakademia.illiaspivak.chat.entity.Message;
import sk.kosickaakademia.illiaspivak.chat.entity.User;
import sk.kosickaakademia.illiaspivak.chat.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    /**
     * Connecting to the database
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    private Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(Connect.getUrl(),Connect.getUsername(),Connect.getPassword());
        return conn;
    }

    /**
     * Checking the database connection
     */
    public void TestConnection(){
        try{
            Connection connection = getConnection();
            System.out.println("Success");
            connection.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Adding a new chat user to the database
     * @param login
     * @param password
     * @return
     */
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
        String query = "INSERT INTO user (login, password) VALUES (?,?)";
        try {
            Connection connection = getConnection();
            if (connection == null) {
                System.out.println("Error! No connection");
                return false;
            }
            PreparedStatement ps = connection.prepareStatement(query);
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

    /**
     * Checking the username and password and creating a object the User
     * @param login
     * @param password
     * @return
     */
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
        String query = "Select * FROM user Where login LIKE ? and password LIKE ?";
        try {
            Connection con = getConnection();
            if (con == null) {
                System.out.println("Error! No connection");
                return null;
            }
            PreparedStatement ps = con.prepareStatement(query);                                                         ;
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

    public boolean changePassword(String login , String oldPassword, String newPassword){
        if (login == null || login.equals("")) {
            System.out.println("You need to enter your username");
            return false;
        }
        if (oldPassword == null || oldPassword.length() < 5){
            System.out.println("The password is too short");
            return false;
        }
        if (newPassword == null || newPassword.length() < 5){
            System.out.println("The password is too short");
            return false;
        }
        User user = loginUser(login,oldPassword);
        String PasswordMD5Old = new Util().getMD5(oldPassword);
        String PasswordMD5New = new Util().getMD5(newPassword);
        if (!login.equals(user.getLogin())){
            return false;
        }
        if (!PasswordMD5Old.equals(user.getPassword())){
            return false;
        }
        String query = "UPDATE user SET password = ? WHERE login = ? AND password = ?";
        try {
            Connection connection = getConnection();
            if (connection == null) {
                System.out.println("Error! No connection");
                return false;
            }
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, PasswordMD5New);
            ps.setString(2, login);
            ps.setString(3, PasswordMD5Old);
            ps.executeUpdate();
            System.out.println("Password changed");
            return true;
        } catch (Exception ex) {
        }
        return false;
    }

    /**
     * Getting an id number
     * @param login
     * @return
     */
    public int getUserId(String login){
        if (login == null || login.equals(""))
            return -1;
        String query = "Select id FROM user Where login LIKE ?";
        try {
            Connection connection = getConnection();
            if (connection == null) {
                System.out.println("Error! No connection");
                return -1;
            }
            PreparedStatement ps = connection.prepareStatement(query);                                                         ;
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                connection.close();
                return id;
            } else {
                connection.close();
                return -1;
            }

        } catch (Exception ex) {
        }
        return -1;
    }

    /**
     * Getting Login
     * @param id
     * @return
     */
    public String getLogin(int id){
        String query = "Select login FROM user Where id LIKE ?";
        try {
            Connection connection = getConnection();
            if (connection == null) {
                System.out.println("Error! No connection");
                return null;
            }
            PreparedStatement ps = connection.prepareStatement(query);                                                         ;
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String login = rs.getString("login");
                connection.close();
                return login;
            } else {
                connection.close();
                return null;
            }

        } catch (Exception ex) {
        }
        return null;
    }

    /**
     * Sending a message
     * @param from
     * @param toUser
     * @param text
     * @return
     */
    public boolean sendMessage(int from, String toUser, String text){
        if(text==null || text.equals(""))
            return false;
        if(toUser==null || toUser.equals(""))
            return false;
        int to = getUserId(toUser);
        if(to==-1)
            return false;
        String query = "INSERT INTO message(fromUser, toUser, text) VALUES(?, ?, ?)";
        try {
            Connection connection = getConnection();
            if(connection ==null){
                System.out.println("Error! No connection");
                return false;
            }
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1,from);
            ps.setInt(2, to);
            ps.setString(3, text);
            int result = ps.executeUpdate();
            connection.close();
            if(result<1){
                System.out.println("Error! The message wasn't sent");
                return false;
            }
            else{
                System.out.println("The message was sent");
                return true;
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return false;
    }

    public List<Message> getMyMessages(String login) {
        if (login == null || login.equals(""))
            return null;
        int to = getUserId(login);
        String query = "Select fromUser, toUser, text FROM message Where toUser LIKE ?";
        ArrayList<Message> messages = new ArrayList<>();
        try{
            Connection connection = getConnection();
            if(connection ==null){
                System.out.println("Error! No connection");
                return null;
            }
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1,to);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                String fromUser = getLogin(rs.getInt("fromUser"));
                String toUser = getLogin(rs.getInt("toUser"));
                String text = rs.getString("text");
                Message message = new Message(fromUser,toUser,text);
                messages.add(message);
            }
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return messages;
    }

    public void deleteAllMyMessages(String login){

    }

    /**
     * Creating a list of chat users
     * @return
     */
    public List<String> getUsers(){
        ArrayList<String> list = new ArrayList<String>();
        String query = "SELECT login FROM user; ";
        try {
            Connection connection = getConnection();
            if(connection ==null){
                System.out.println("Error! No connection");
                return null;
            }
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                list.add(rs.getString("login"));
            }
            connection.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
