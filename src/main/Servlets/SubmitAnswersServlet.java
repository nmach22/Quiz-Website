package main.Servlets;

import main.Manager.History;
import main.Manager.User;

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
        int ID = Integer.parseInt(request.getParameter("quiz_id"));
        String name = request.getParameter("username");
        request.getSession().setAttribute("score", score);
        try {
            int prev = User.highestScore(ID);
            int timeLeft = Integer.parseInt(request.getParameter("timeLeft"));
            request.getSession().setAttribute("timeLeft", timeLeft);
            History h = new History(ID,name, score,((int) request.getSession().getAttribute("duration")) - timeLeft);
            if(score > prev){
                User.addAchievement(name, "I am the Greatest");
            }
            int takes = User.takenQuizzesAmount(name, ID);
            if(takes == 10){
                User.addAchievement(name, "Quiz Machine");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        response.sendRedirect("finished-quiz.jsp?quiz_id=" + ID + "&username=" + name + "&score=" + score);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = (String)request.getSession().getAttribute("username");
        String quiz_id = (String)request.getSession().getAttribute("quiz_id");
        int score = Integer.parseInt(request.getParameter("score"));;
        boolean is_correct = false;
        int id = (int) request.getSession().getAttribute("question_id");
        String submitted = request.getParameter("submitted"+id);
        request.getSession().setAttribute("is_submitted"+id, true);
        Set<String> correctAnswers = (Set<String>) request.getSession().getAttribute("correct_answers"+id);
        for (String s : correctAnswers) {
            if(s.equals(submitted)) is_correct = true;
        }
        int timeLeft = Integer.parseInt(request.getParameter("timeLeft"));
        if(timeLeft < 0) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("finished-quiz.jsp");
            dispatcher.forward(request, response);
        } else {
            if(is_correct)
                score++;

            request.getSession().setAttribute("timeLeft", timeLeft);
            int currInd = Integer.parseInt(request.getParameter("currentQuestionIndex")) + 1;
            response.sendRedirect("question.jsp?quiz_id=" + quiz_id + "&username=" + username + "&currentQuestionIndex=" + currInd + "&score=" + score);
        }
    }
}
