package main.Servlets;// QuizServlet.java
import main.Manager.QuizSummary;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet(urlPatterns = {"/QuizSummaryServlet"})
public class QuizSummaryServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String quizId = request.getParameter("quiz_id");
        if (quizId == null || quizId.isEmpty()) {
            response.getWriter().println("No quiz ID provided.");
            return;
        }
        QuizSummary quiz = null;
        try {
            System.out.println(quizId);
            System.out.println(getUsername(request));
            quiz = new QuizSummary(quizId, getUsername(request));

            String quizName = quiz.getQuizName();
            String description = quiz.getDescription();
            String author = quiz.getAuthor();
            boolean practiceMode = quiz.isPracticeMode();
            boolean is_random = quiz.isRandom();
            boolean one_page = quiz.isOnePage();
            boolean immediate_correction = quiz.isImmediateCorrection();

            List<Map<String, Object>> pastPerformances = quiz.getPastPerformances();
            List<Map<String, Object>> topAllTime = quiz.getTopAllTime();
            List<Map<String, Object>> topLastDay = quiz.getTopLastDay();
            List<Map<String, Object>> recentTakers = quiz.getRecentTestTakers();
            Map<String, Object> summaryStats = quiz.getSummarySats();

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

            RequestDispatcher dispatcher = request.getRequestDispatcher("quiz_summary.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error retrieving quiz summary.");
        }
    }
        private String getUsername(HttpServletRequest request) {
        return (String) request.getSession().getAttribute("username");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
