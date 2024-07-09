package main.Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import main.Manager.Pair;
import main.Manager.User;

@WebServlet("/GetQuizzesServlet")
public class GetQuizzesServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String option = request.getParameter("option");
        ArrayList<Pair> quizzes = new ArrayList<>();

        try {
            if ("1".equals(option)) {
                quizzes = User.getPopularQuizzes();
            } else {
                quizzes = User.getRecentlyAddedQuizzes();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        out.print(gson.toJson(quizzes));
        out.flush();
    }
}