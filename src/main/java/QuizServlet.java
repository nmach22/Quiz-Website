// QuizServlet.java

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;


public class QuizServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection connection;
        String url = "jdbc:mysql://localhost:3306/mysql";
        String user = "root";
        String password = "rootroot";

        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String quizId = request.getParameter("quiz_id");
        if (quizId == null || quizId.isEmpty()) {
            response.getWriter().println("No quiz ID provided.");
            return;
        }

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String query = "SELECT * FROM quizzes WHERE quiz_id = ?";
            ps = connection.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(quizId));
            rs = ps.executeQuery();

            if (!rs.next()) {
                response.getWriter().println("Quiz not found.");
                return;
            }

            String quizName = rs.getString("quiz_name");
            String description = rs.getString("description");
            String author = rs.getString("author");
            boolean practiceMode = rs.getBoolean("practice_mode");
            // Fetch author details
            String authorQuery = "SELECT * FROM users WHERE username = ?";
            ps = connection.prepareStatement(authorQuery);
            ps.setString(1, author);
            ResultSet authorRs = ps.executeQuery();
            authorRs.next();
            String authorUsername = authorRs.getString("username");

            // Fetch user's past performance on this quiz
            String performanceQuery = "SELECT * FROM quizzes WHERE quiz_id = ? AND username = ? ORDER BY creation_date DESC";
            ps = connection.prepareStatement(performanceQuery);
            ps.setInt(1, Integer.parseInt(quizId));
            ps.setInt(2, getUsername(request));
            ResultSet performanceRs = ps.executeQuery();

            List<Map<String, Object>> pastPerformances = new ArrayList<>();
            while (performanceRs.next()) {
                Map<String, Object> performance = new HashMap<>();
                performance.put("score", performanceRs.getBigDecimal("score"));
                performance.put("time_taken", performanceRs.getInt("time_taken"));
                performance.put("date_taken", performanceRs.getTimestamp("date_taken"));
                pastPerformances.add(performance);
            }

            // Fetch highest performers of all time
            String topAllTimeQuery = "SELECT users.username, quizzes.score FROM quizzes JOIN users ON quizzes.username = users.username WHERE quiz_id = ? ORDER BY score DESC LIMIT 10";
            ps = connection.prepareStatement(topAllTimeQuery);
            ps.setInt(1, Integer.parseInt(quizId));
            ResultSet topAllTimeRs = ps.executeQuery();

            List<Map<String, Object>> topAllTime = new ArrayList<>();
            while (topAllTimeRs.next()) {
                Map<String, Object> topPerformance = new HashMap<>();
                topPerformance.put("username", topAllTimeRs.getString("username"));
                topPerformance.put("score", topAllTimeRs.getBigDecimal("score"));
                topAllTime.add(topPerformance);
            }

            // Fetch top performers in the last day
            String topLastDayQuery = "SELECT users.username, quizzes.score FROM quizzes JOIN users ON quizzes.username = users.username WHERE quiz_id = ? AND creation_date >= NOW() - INTERVAL 1 DAY ORDER BY score DESC LIMIT 10";
            ps = connection.prepareStatement(topLastDayQuery);
            ps.setInt(1, Integer.parseInt(quizId));
            ResultSet topLastDayRs = ps.executeQuery();

            List<Map<String, Object>> topLastDay = new ArrayList<>();
            while (topLastDayRs.next()) {
                Map<String, Object> topPerformance = new HashMap<>();
                topPerformance.put("username", topLastDayRs.getString("username"));
                topPerformance.put("score", topLastDayRs.getBigDecimal("score"));
                topLastDay.add(topPerformance);
            }

            // Fetch recent test takers
            String recentTakersQuery = "SELECT users.username, quizzes.score FROM quizzes JOIN users ON quizzes.username = users.username WHERE quiz_id = ? ORDER BY creation_date DESC LIMIT 10";
            ps = connection.prepareStatement(recentTakersQuery);
            ps.setInt(1, Integer.parseInt(quizId));
            ResultSet recentTakersRs = ps.executeQuery();

            List<Map<String, Object>> recentTakers = new ArrayList<>();
            while (recentTakersRs.next()) {
                Map<String, Object> recentPerformance = new HashMap<>();
                recentPerformance.put("username", recentTakersRs.getString("username"));
                recentPerformance.put("score", recentTakersRs.getBigDecimal("score"));
                recentTakers.add(recentPerformance);
            }

            // Fetch summary statistics
            String summaryStatsQuery = "SELECT AVG(score) AS average_score, MAX(score) AS max_score, MIN(score) AS min_score FROM quizzes WHERE quiz_id = ?";
            ps = connection.prepareStatement(summaryStatsQuery);
            ps.setInt(1, Integer.parseInt(quizId));
            ResultSet summaryStatsRs = ps.executeQuery();
            summaryStatsRs.next();

            Map<String, Object> summaryStats = new HashMap<>();
            summaryStats.put("average_score", summaryStatsRs.getBigDecimal("average_score"));
            summaryStats.put("max_score", summaryStatsRs.getBigDecimal("max_score"));
            summaryStats.put("min_score", summaryStatsRs.getBigDecimal("min_score"));

            // Set attributes for the JSP
            request.setAttribute("description", description);
            request.setAttribute("author", author);
            request.setAttribute("authorPage", authorUsername);
            request.setAttribute("pastPerformances", pastPerformances);
            request.setAttribute("topAllTime", topAllTime);
            request.setAttribute("topLastDay", topLastDay);
            request.setAttribute("recentTakers", recentTakers);
            request.setAttribute("summaryStats", summaryStats);
            request.setAttribute("quizId", quizId);
            request.setAttribute("practiceMode", practiceMode);
            request.setAttribute("quizName", quizName);

            // Forward to JSP
            RequestDispatcher dispatcher = request.getRequestDispatcher("quizSummary.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error retrieving quiz summary.");
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (connection != null) try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    private int getUsername(HttpServletRequest request) {
        // Implement logic to get the logged-in user's ID from the session or context
        return (int) request.getSession().getAttribute("username");
    }
}
