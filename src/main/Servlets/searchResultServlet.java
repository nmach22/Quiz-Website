package main.Servlets;

import main.Manager.AccountManager;
import main.Manager.SearchManager;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/searchResultServlet")
public class searchResultServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String search = req.getParameter("searchItem");
        try {
            JSONObject result = new JSONObject();
            if(AccountManager.hasAcc(search)){
                result.put("profiles", search);
            }
            ArrayList<SearchManager> quizzes = SearchManager.searchQuizzes(search);
            JSONArray quizzesArray = new JSONArray();
            for (SearchManager quiz : quizzes) {
                JSONObject quizJson = new JSONObject();
                quizJson.put("quizId", quiz.quiz_id);
                quizJson.put("title", quiz.quizName);
                quizzesArray.put(quizJson);
            }
            result.put("quizzes", quizzesArray);
            resp.setContentType("application/json");
            resp.getWriter().write(result.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
