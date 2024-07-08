package main.Servlets;

import main.Manager.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/challengeRequestHandlerServlet")
public class challengeRequestHandlerServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        int id = Integer.parseInt(request.getParameter("ID"));
        try {
            if (action.equals("accept")) {
                User.acceptChallenge(id);
            } else if (action.equals("reject")) {
                User.rejectChallenge(id);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
