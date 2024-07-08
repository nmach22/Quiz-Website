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
            request.getSession().setAttribute("author", author);
            request.getSession().setAttribute("pastPerformances", pastPerformances);
            request.getSession().setAttribute("topAllTime", topAllTime);
            request.getSession().setAttribute("topLastDay", topLastDay);
            request.getSession().setAttribute("recentTakers", recentTakers);
            request.getSession().setAttribute("summaryStats", summaryStats);
            request.getSession().setAttribute("quizId", quizId);
            request.getSession().setAttribute("one_page", one_page);
            request.getSession().setAttribute("immediate_correction", immediate_correction);
            request.getSession().setAttribute("practiceMode", practiceMode);
            request.getSession().setAttribute("is_random", is_random);
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
