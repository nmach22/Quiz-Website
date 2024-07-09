package main.Manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class History {
    public History(int quiz_id, String username, int score, int timeTaken) throws SQLException, ClassNotFoundException {
        Connection con = DataBaseConnection.getConnection();
        String query = "INSERT INTO history (quiz_id, username, score, time) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(query);
            ps.setInt(1, quiz_id);
            ps.setString(2, username);
            ps.setInt(3, score);
            ps.setInt(4, timeTaken);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
