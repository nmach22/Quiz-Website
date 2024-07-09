package main.Servlets;


import main.Manager.QuizManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;


@WebServlet("/removeQuizHistory")
public class removeQuizHistoryServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String quiz_id = req.getParameter("quizID");
        try {
            String quizName = QuizManager.getQuizName(quiz_id);
            if(!quizName.isEmpty()) {
                resp.setContentType("text/plain");
                QuizManager.removeQuizHistory(Integer.parseInt(quiz_id));
                PrintWriter out = resp.getWriter();
                out.print("Successfully deleted " + quiz_id + " history");
            } else {
                resp.setContentType("text/plain");
                PrintWriter out = resp.getWriter();
                out.print(quiz_id + " does not exist");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
