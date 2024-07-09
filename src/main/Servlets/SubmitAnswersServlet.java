package main.Servlets;

import main.Manager.History;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.event.HierarchyBoundsAdapter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@WebServlet(urlPatterns = {"/submitAnswers"})
public class SubmitAnswersServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int score = 0;
        List<Map<String, Object>> questions = (List<Map<String, Object>>)request.getSession().getAttribute("questions");
        for (Map<String, Object> q : questions) {
            int id = (int)q.get("question_id");
            boolean is_correct = false;
            String submitted = request.getParameter("submitted"+id);
            Set<String> correctAnswers = (Set<String>) request.getSession().getAttribute("correct_answers"+id);
            for (String s : correctAnswers) {
                if(s.equals(submitted)) is_correct = true;
            }
            if(is_correct) score++;
        }
        request.getSession().setAttribute("score", score);
        String timeLeftStr = request.getParameter("timeLeft");
        int timeLeft = 0;
        if (timeLeftStr != null)
            timeLeft = Integer.parseInt(timeLeftStr);
        try {
            int duration = (int)request.getSession().getAttribute("duration");
            History h = new History(Integer.parseInt(request.getParameter("quiz_id")),request.getParameter("username"), score, ((int) request.getSession().getAttribute("duration")) - ((int) request.getSession().getAttribute("timeLeft")));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("finished-quiz.jsp");
        dispatcher.forward(request, response);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = (String)request.getSession().getAttribute("username");
        String quiz_id = (String)request.getSession().getAttribute("quiz_id");
        int score = (int) request.getSession().getAttribute("score");
        boolean is_correct = false;
        int id = (int) request.getSession().getAttribute("question_id");
        String submitted = request.getParameter("submitted"+id);
        Set<String> correctAnswers = (Set<String>) request.getSession().getAttribute("correct_answers"+id);
        for (String s : correctAnswers)
            if(s.equals(submitted)) is_correct = true;
        int timeLeft = Integer.parseInt(request.getParameter("timeLeft"));
        if(timeLeft < 0) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("finished-quiz.jsp");
            dispatcher.forward(request, response);
        } else {
            if (is_correct)
                score++;
            request.getSession().setAttribute("timeLeft", timeLeft);
            request.getSession().setAttribute("score", score);
            int currInd = (int) request.getSession().getAttribute("currentQuestionIndex");
            request.getSession().setAttribute("currentQuestionIndex", currInd + 1);
            RequestDispatcher dispatcher = request.getRequestDispatcher("question.jsp?quiz_id=" + quiz_id + "&username=" + username);
            dispatcher.forward(request, response);
        }
    }
}
