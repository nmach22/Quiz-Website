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

    public boolean hasAcc(String user) throws SQLException {
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
}
