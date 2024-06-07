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

            String questionsQuery = "SELECT * FROM questions WHERE quiz_id = ?";
            ps = connection.prepareStatement(questionsQuery);
            ps.setInt(1, Integer.parseInt(quizId));
            ResultSet questionsRs = ps.executeQuery();

            List<Map<String, String>> questions = new ArrayList<>();
            while (questionsRs.next()) {
                Map<String, String> question = new HashMap<>();
                question.put("question_id", questionsRs.getString("question_id"));
                question.put("question_text", questionsRs.getString("question_text"));
                questions.add(question);
            }

            // Set quiz details and questions as request attributes
            request.setAttribute("quizName", quizName);
            request.setAttribute("questions", questions);

            // Forward to the JSP page
            RequestDispatcher dispatcher = request.getRequestDispatcher("quiz.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error retrieving quiz.");
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (connection != null) try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}
