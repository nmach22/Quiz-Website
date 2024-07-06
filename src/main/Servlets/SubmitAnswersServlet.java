package main.Servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
            String submited = request.getParameter("submitted"+id);
            Set<String> correctAnswers = (Set<String>) request.getSession().getAttribute("correct_answers"+id);
            for (String s : correctAnswers) {
                if(s.equals(submited)) is_correct = true;
            }
            if(is_correct) score++;
        }
        request.getSession().setAttribute("score", score);
        RequestDispatcher dispatcher = request.getRequestDispatcher("finished_quiz.jsp");
        dispatcher.forward(request, response);
    }

}
