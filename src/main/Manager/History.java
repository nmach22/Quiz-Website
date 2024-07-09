package main.Manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class History {
    public History(int quiz_id, String username, int score, int timeLeft) throws SQLException, ClassNotFoundException {
        Connection con = DataBaseConnection.getConnection();
        String query = "INSERT INTO history (quiz_id, username, score, time) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(query);
            ps.setInt(1, quiz_id);
            ps.setString(2, username);
            ps.setInt(3, score);
            long currentTimeMillis = System.currentTimeMillis();

            // Calculate the end time by adding timeLeft in milliseconds
            long endTimeMillis = currentTimeMillis + (timeLeft * 1000L);

            // Convert to Timestamp
            Timestamp endTimestamp = new Timestamp(endTimeMillis);

            // Now you have the correct end time as a Timestamp
            System.out.println("End Time: " + endTimestamp);
//            Timestamp timestamp = new Timestamp(timetaken * 1000);
//            System.out.println("Timestamp: " + timestamp);
            ps.setTimestamp(4, endTimestamp);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
