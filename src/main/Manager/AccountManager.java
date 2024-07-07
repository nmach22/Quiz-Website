package main.Manager;

import main.Manager.DataBaseConnection;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class AccountManager {
    private static Connection con;

    public AccountManager() throws ClassNotFoundException, SQLException {

        con = DataBaseConnection.getConnection();
    }
    static String generateHash(String password) {
        String hashString = "";
        try {
            MessageDigest md;
            md = MessageDigest.getInstance("SHA");
            md.update(password.getBytes());
            byte[] hashBytes;
            hashBytes = md.digest();
            hashString = hexToString(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hashString;
    }

    public static String hexToString(byte[] bytes) {
        StringBuffer buff = new StringBuffer();
        for (int i=0; i<bytes.length; i++) {
            int val = bytes[i];
            val = val & 0xff;  // remove higher bits, sign
            if (val<16) buff.append('0'); // leading 0
            buff.append(Integer.toString(val, 16));
        }
        return buff.toString();
    }

    public static String getFN(String userName) throws SQLException {
        String query = "SELECT user_first_name FROM users WHERE username = ?";
        PreparedStatement st = con.prepareStatement(query);
        st.setString(1 , userName);
        ResultSet s = st.executeQuery();
        if(s.next()){
            return s.getString(1);
        }
        return "";
    }

    public static String getLN(String userName) throws SQLException {
        String query = "SELECT user_last_name FROM users WHERE username = ?";
        PreparedStatement st = con.prepareStatement(query);
        st.setString(1 , userName);
        ResultSet s = st.executeQuery();
        if(s.next()){
            return s.getString(1);
        }
        return "";
    }

    public boolean isCorrect(String user , String pas) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ? AND password_hash = ?";
        PreparedStatement st = con.prepareStatement(query);
        st.setString(1 , user);
        st.setString(2 , generateHash(pas));
        ResultSet rs = st.executeQuery();
        if(rs.next()){
            return true;
        }
        return false;
    }

    public static boolean hasAcc(String user) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ?";
        PreparedStatement st = con.prepareStatement(query);
        st.setString(1 , user);
        ResultSet rs = st.executeQuery();
        return rs.next();
    }

    public void createAcc(String name ,String pas, String firstName, String lastName) throws SQLException {
        String query = "INSERT INTO users (username,password_hash,user_first_name, user_last_name) VALUES (? , ?, ?, ?)";
        PreparedStatement st = con.prepareStatement(query);
        st.setString(1 , name);
        st.setString(2 , generateHash(pas));
        st.setString(3,firstName);
        st.setString(4, lastName);
        st.executeUpdate();
    }
    public void changeBio(String bio, String username) throws SQLException {
        String query = "UPDATE users SET user_bio = ? WHERE username = ?";
        PreparedStatement st = con.prepareStatement(query);
        st.setString(1 ,bio);
        st.setString(2 ,username);
        st.executeUpdate();
    }
    public static String getBio(String username) throws SQLException {
        String query = "Select user_bio from users WHERE username = ?";
        PreparedStatement st = con.prepareStatement(query);
        st.setString(1,username);
        ResultSet rs = st.executeQuery();
        if(rs.next()){
            return rs.getString(1);
        }
        return "";
    }
    public int removeAcc(String name) throws SQLException {
        if(hasAcc(name)){
            if(!isAdmin(name)) {
                String query = "DELETE FROM users WHERE username = ?";
                PreparedStatement st = con.prepareStatement(query);
                st.setString(1, name);
                st.executeUpdate();
                return 1;
            }else{
                return 2;
            }
        }
        return 0;
    }
    public static boolean isAdmin(String name) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ?";
        PreparedStatement st = con.prepareStatement(query);
        st.setString(1 , name);
        ResultSet rs = st.executeQuery();
        if(rs.next()){
            return rs.getInt(3) == 1;
        }
        return false;
    }

    public int promoteToAdmin(String name) throws SQLException {
        if(hasAcc(name)){
            if(isAdmin(name)){
                return 2;
            }else{
                String query = "UPDATE users SET is_admin = 1 WHERE username = ?";
                PreparedStatement st = con.prepareStatement(query);
                st.setString(1, name);
                st.executeUpdate();
                return 1;
            }
        }
        return 0;
    }

    public void changePas(String parameter, String userName) throws SQLException {
        String pasHash = generateHash(parameter);
        String query = "UPDATE users SET password_hash = ? WHERE username = ?";
        PreparedStatement st = con.prepareStatement(query);
        st.setString(1, pasHash);
        st.setString(2, userName);
        st.executeUpdate();
    }

    public void changeFN(String firstname, String userName) throws SQLException {
        String query = "UPDATE users SET user_first_name = ? where username = ?";
        PreparedStatement st = con.prepareStatement(query);
        st.setString(1,firstname);
        st.setString(2,userName);
        st.executeUpdate();
    }
    public void changeLN(String lastname, String userName) throws SQLException {
        String query = "UPDATE users SET user_last_name = ? where username = ?";
        PreparedStatement st = con.prepareStatement(query);
        st.setString(1,lastname);
        st.setString(2,userName);
        st.executeUpdate();
    }
    public static String getMessageCount(String user) throws SQLException {
        String query = "SELECT COUNT(*) from chat WHERE user_to = ?";
        PreparedStatement st = con.prepareStatement(query);
        st.setString(1, user);
        ResultSet res = st.executeQuery();
        if(res.next()){
            return res.getString(1);
        }
        return "0";
    }

}
