// QuizServlet.java

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet(urlPatterns = {"/LoginWeb/QuizSummaryServlet"})
public class QuizSummaryServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        quizId = "1";
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
            boolean is_random = rs.getBoolean("is_random");
            boolean one_page = rs.getBoolean("one_page");
            boolean immediate_correction = rs.getBoolean("immediate_correction");

            // Fetch user's past performance on this quiz
            String performanceQuery = "SELECT * FROM history WHERE quiz_id = ? AND username = ? ORDER BY date_taken DESC";
            ps = connection.prepareStatement(performanceQuery);
            ps.setInt(1, Integer.parseInt(quizId));
            ps.setString(2, getUsername(request));
            ResultSet performanceRs = ps.executeQuery();

            List<Map<String, Object>> pastPerformances = new ArrayList<>();
            while (performanceRs.next()) {
                Map<String, Object> performance = new HashMap<>();
                performance.put("score", performanceRs.getInt("score"));
                performance.put("time", performanceRs.getInt("time"));
                performance.put("date_taken", performanceRs.getTimestamp("date_taken"));
                pastPerformances.add(performance);
            }

            // Fetch highest performers of all time
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

            // Fetch top performers in the last day
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

            // Fetch recent test takers
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

            //          // Fetch summary statistics
            String summaryStatsQuery = "SELECT AVG(score) AS average_score, MAX(score) AS max_score, MIN(score) AS min_score FROM history WHERE quiz_id = ?";
            ps = connection.prepareStatement(summaryStatsQuery);
            ps.setInt(1, Integer.parseInt(quizId));
            ResultSet summaryStatsRs = ps.executeQuery();
            summaryStatsRs.next();

            Map<String, Object> summaryStats = new HashMap<>();
            summaryStats.put("average_score", summaryStatsRs.getInt("average_score"));
            summaryStats.put("max_score", summaryStatsRs.getInt("max_score"));
            summaryStats.put("min_score", summaryStatsRs.getInt("min_score"));

            // Set attributes for the JSP
            request.getSession().setAttribute("description", description);
            request.setAttribute("author", author);
            request.setAttribute("pastPerformances", pastPerformances);
            request.setAttribute("topAllTime", topAllTime);
            request.setAttribute("topLastDay", topLastDay);
            request.setAttribute("recentTakers", recentTakers);
            request.setAttribute("summaryStats", summaryStats);
            request.setAttribute("quizId", quizId);
            request.setAttribute("one_page", one_page);
            request.setAttribute("immediate_correction", immediate_correction);
            request.setAttribute("practiceMode", practiceMode);
            request.setAttribute("is_random", is_random);
            request.getSession().setAttribute("quizName", quizName);

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

    private String getUsername(HttpServletRequest request) {
        return (String) request.getSession().getAttribute("username");
    }
}
