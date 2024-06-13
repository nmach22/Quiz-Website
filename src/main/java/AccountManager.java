import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class AccountManager {
    private Connection con;

    public AccountManager() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
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
            System.out.println(hashString);
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

    public int isCorrect(String user , String pas) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ? AND password_hash = ?";
        PreparedStatement st = con.prepareStatement(query);
        st.setString(1 , user);
        st.setString(2 , generateHash(pas));
        ResultSet rs = st.executeQuery();
        if(rs.next()){
            if (rs.getInt(3) == 1){
                return 2;
            }
            return 1;
        }
        return 0;
    }

    public boolean hasAcc(String user) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ?";
        PreparedStatement st = con.prepareStatement(query);
        st.setString(1 , user);
        ResultSet rs = st.executeQuery();
        return rs.next();
    }

    public void createAcc(String name ,String pas) throws SQLException {
        String query = "INSERT INTO users (username,password_hash) VALUES (? , ?)";
        PreparedStatement st = con.prepareStatement(query);
        st.setString(1 , name);
        st.setString(2 , generateHash(pas));
        st.executeUpdate();
    }
}
