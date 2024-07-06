package main.Servlets;

import main.Manager.TakeQuiz;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/TakeQuizServlet"})
public class TakeQuizServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String quizId = req.getParameter("quiz_id");
        String username = req.getParameter("username");
        TakeQuiz quiz = null;
        try {
            quiz = new TakeQuiz(quizId);
            req.getSession().setAttribute("questions", quiz.getQuestions());
            req.getSession().setAttribute("fillInTheBlankQuestions", quiz.fetchFillInTheBlankQuestions());
            req.getSession().setAttribute("responseQuestions", quiz.fetchResponseQuestions());
            req.getSession().setAttribute("pictureResponseQuestions", quiz.fetchPictureResponseQuestions());
            req.getSession().setAttribute("multipleChoiceQuestions", quiz.fetchMultipleChoiceQuestions());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher("take_Quiz.jsp");
        dispatcher.forward(req, resp);

    }
}
