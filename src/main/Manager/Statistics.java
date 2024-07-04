package main.Manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Statistics {

    public int users;
    public int quizzes;

    public Statistics(int users, int quizzes) {
        this.users = users;
        this.quizzes = quizzes;
    }

    public static ArrayList<Statistics> getUserStatistics(String timeRange) throws SQLException, ClassNotFoundException {
        ArrayList<Statistics> res = new ArrayList<>();
        String query = "";
        String query2 = "";
        switch (timeRange) {
            case "day":
                query = "SELECT COUNT(*) AS total_users FROM users WHERE DATE(date_of_registration) >= DATE_SUB(CURDATE(), INTERVAL 1 DAY)";
                query2 = "SELECT COUNT(*) AS total_quizzes FROM history WHERE DATE(date_taken) >= DATE_SUB(CURDATE(), INTERVAL 1 DAY)";
                break;
            case "week":
                query = "SELECT COUNT(*) AS total_users FROM users WHERE DATE(date_of_registration) >= DATE_SUB(CURDATE(), INTERVAL 1 WEEK)";
                query2 = "SELECT COUNT(*) AS total_quizzes FROM history WHERE DATE(date_taken) >= DATE_SUB(CURDATE(), INTERVAL 1 WEEK)";
                break;
            case "month":
                query = "SELECT COUNT(*) AS total_users FROM users WHERE DATE(date_of_registration) >= DATE_SUB(CURDATE(), INTERVAL 1 MONTH )";
                query2 = "SELECT COUNT(*) AS total_quizzes FROM history WHERE DATE(date_taken) >= DATE_SUB(CURDATE(), INTERVAL 1 MONTH)";
                break;
            case "year":
                query = "SELECT COUNT(*) AS total_users FROM users WHERE DATE(date_of_registration) >= DATE_SUB(CURDATE(), INTERVAL 1 YEAR)";
                query2 = "SELECT COUNT(*) AS total_quizzes FROM history WHERE DATE(date_taken) >= DATE_SUB(CURDATE(), INTERVAL 1 YEAR)";
                break;
            case "all_time":
                query = "SELECT COUNT(*) AS total_users FROM users WHERE DATE(date_of_registration) <= CURDATE()";
                query2 = "SELECT COUNT(*) AS total_quizzes FROM history WHERE DATE(date_taken) <= CURDATE()";
                break;
            default:
                throw new IllegalArgumentException("Invalid time range");
        }
        Connection con = DataBaseConnection.getConnection();
        PreparedStatement st = con.prepareStatement(query);
        PreparedStatement st2 = con.prepareStatement(query2);
        ResultSet rs = st.executeQuery();
        ResultSet rs2 = st2.executeQuery();
        while (rs.next() && rs2.next()) {
            res.add(new Statistics(rs.getInt("total_users"), rs2.getInt("total_quizzes")));
        }
        return res;

    }


}
