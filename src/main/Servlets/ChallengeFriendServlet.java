package main.Servlets;

import main.Manager.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/ChallengeFriendServlet")
public class ChallengeFriendServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String friendName = req.getParameter("friendName");
        String quizId = req.getParameter("quizId");
        String username = req.getParameter("username");


        // Validate the parameters (optional but recommended)
        if (friendName == null || quizId == null || username == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Missing parameters");
            return;
        }

        try {
            int highestScrore = User.getUsersHighestScoreOnQuiz(username, Integer.parseInt(quizId));
            // Assuming UserManager has a method to send challenges
            User.sendChallenge(username, friendName, Integer.parseInt(quizId),highestScrore);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("Challenge sent successfully");

        } catch (Exception e) {
            // Log the exception (using your preferred logging framework)
            e.printStackTrace();

            // Send error response
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("An error occurred while sending the challenge");
        }
    }
}
