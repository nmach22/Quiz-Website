package main.Servlets;

import main.Manager.Notifications;
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

@WebServlet("/notificationsServlet")
public class NotificationsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        String user = request.getSession().getAttribute("username").toString();
        try {
            JSONObject result = new JSONObject();
            JSONArray friendRequestsAr = new JSONArray();
            JSONArray quizChallengesAr = new JSONArray();
            ArrayList<String> friendRequests = Notifications.getFriendRequests(user);
            ArrayList<Notifications> quizChallenges = Notifications.getChallenges(user);
            for (String friend : friendRequests) {
                JSONObject quizJson = new JSONObject();
                quizJson.put("friend", friend);
                friendRequestsAr.put(quizJson);
            }
            for (Notifications challenge : quizChallenges) {
                JSONObject quizJson = new JSONObject();
                quizJson.put("from", challenge.from);
                quizJson.put("quizId", challenge.quiz_id);
                quizJson.put("score", challenge.score);
                quizJson.put("challengeID", challenge.challenge_id);
                quizChallengesAr.put(quizJson);
            }
            result.put("friendRequests", friendRequestsAr);
            result.put("challenges", quizChallengesAr);
            resp.setContentType("application/json");
            resp.getWriter().write(result.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

}