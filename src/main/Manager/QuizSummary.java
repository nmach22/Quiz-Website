package main.Manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizSummary {
    private Connection connection;
    private PreparedStatement ps;
    private ResultSet rs;
    private String quizId;
    private String username;
    private String description;
    private String author;
    private String quizName;
    boolean practiceMode;
    boolean is_random;
    boolean one_page;
    boolean immediate_correction;
    public QuizSummary(String quizId, String userName) throws SQLException {
        this.quizId = quizId;
        this.username = userName;
        getConnection();
        fillQuizInfo();
    }
    private void fillQuizInfo() throws SQLException{
        String query = "SELECT * FROM quizzes WHERE quiz_id = ?";
        ps = connection.prepareStatement(query);
        ps.setInt(1, Integer.parseInt(quizId));
        rs = ps.executeQuery();

        if (!rs.next()) {
            return;
        }

        quizName = rs.getString("quiz_name");
        description = rs.getString("description");
        author = rs.getString("author");
        practiceMode = rs.getBoolean("practice_mode");
        is_random = rs.getBoolean("is_random");
        one_page = rs.getBoolean("one_page");
        immediate_correction = rs.getBoolean("immediate_correction");
    }
    public String getDescription() {
        return description;
    }
    public String getAuthor() {
        return author;
    }
    public String getQuizName() {
        return quizName;
    }
    public boolean isPracticeMode() {
        return practiceMode;
    }
    public boolean isRandom() {
        return is_random;
    }
    public boolean isOnePage() {
        return one_page;
    }
    public boolean isImmediateCorrection() {
        return immediate_correction;
    }


    // Fetch user's past performance on this quiz
    public List<Map<String, Object>> getPastPerformances() throws SQLException {
        String performanceQuery = "SELECT * FROM history WHERE quiz_id = ? AND username = ? ORDER BY date_taken DESC";
        ps = connection.prepareStatement(performanceQuery);
        ps.setInt(1, Integer.parseInt(quizId));
        ps.setString(2, username);
        ResultSet performanceRs = ps.executeQuery();

        List<Map<String, Object>> pastPerformances = new ArrayList<>();
        while (performanceRs.next()) {
            Map<String, Object> performance = new HashMap<>();
            performance.put("score", performanceRs.getInt("score"));
            performance.put("time", performanceRs.getTimestamp("time"));
            performance.put("date_taken", performanceRs.getTimestamp("date_taken"));
            pastPerformances.add(performance);
        }
        return pastPerformances;
    }
    // Fetch highest performers of all time
    public List<Map<String, Object>> getTopAllTime() throws SQLException {
        String topAllTimeQuery = "SELECT username, score FROM history WHERE quiz_id = ? ORDER BY score DESC LIMIT 10";
        ps = connection.prepareStatement(topAllTimeQuery);
        ps.setInt(1, Integer.parseInt(quizId));
        ResultSet topAllTimeRs = ps.executeQuery();

        List<Map<String, Object>> topAllTime = new ArrayList<>();
        while (topAllTimeRs.next()) {
            Map<String, Object> topPerformance = new HashMap<>();
            topPerformance.put("username", topAllTimeRs.getString("username"));
            topPerformance.put("score", topAllTimeRs.getInt("score"));
            topAllTime.add(topPerformance);
        }
        return topAllTime;
    }
    // Fetch top performers in the last day
    public List<Map<String, Object>> getTopLastDay() throws SQLException {
        String topLastDayQuery = "SELECT username, score FROM history WHERE quiz_id = ? AND date_taken >= NOW() - INTERVAL 1 DAY ORDER BY score DESC LIMIT 10";
        ps = connection.prepareStatement(topLastDayQuery);
        ps.setInt(1, Integer.parseInt(quizId));
        ResultSet topLastDayRs = ps.executeQuery();

        List<Map<String, Object>> topLastDay = new ArrayList<>();
        while (topLastDayRs.next()) {
            Map<String, Object> topPerformance = new HashMap<>();
            topPerformance.put("username", topLastDayRs.getString("username"));
            topPerformance.put("score", topLastDayRs.getInt("score"));
            topLastDay.add(topPerformance);
        }
        return topLastDay;
    }
    // Fetch recent test takers
    public List<Map<String, Object>> getRecentTestTakers() throws SQLException {
        String recentTakersQuery = "SELECT username, score FROM history WHERE quiz_id = ? ORDER BY date_taken DESC LIMIT 10";
        ps = connection.prepareStatement(recentTakersQuery);
        ps.setInt(1, Integer.parseInt(quizId));
        ResultSet recentTakersRs = ps.executeQuery();

        List<Map<String, Object>> recentTakers = new ArrayList<>();
        while (recentTakersRs.next()) {
            Map<String, Object> recentPerformance = new HashMap<>();
            recentPerformance.put("username", recentTakersRs.getString("username"));
            recentPerformance.put("score", recentTakersRs.getInt("score"));
            recentTakers.add(recentPerformance);
        }
        return recentTakers;
    }
    public Map<String, Object> getSummarySats() throws SQLException {
        String summaryStatsQuery = "SELECT AVG(score) AS average_score, MAX(score) AS max_score, MIN(score) AS min_score FROM history WHERE quiz_id = ?";
        ps = connection.prepareStatement(summaryStatsQuery);
        ps.setInt(1, Integer.parseInt(quizId));
        ResultSet summaryStatsRs = ps.executeQuery();
        summaryStatsRs.next();

        Map<String, Object> summaryStats = new HashMap<>();
        summaryStats.put("average_score", summaryStatsRs.getInt("average_score"));
        summaryStats.put("max_score", summaryStatsRs.getInt("max_score"));
        summaryStats.put("min_score", summaryStatsRs.getInt("min_score"));
        return summaryStats;
    }
    public Connection getConnection() {
        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            connection = DataBaseConnection.getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
 }
