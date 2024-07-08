package main.Servlets;

import main.Manager.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/friendRequestHandlerServlet")
public class friendRequestHandlerServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        String friend = request.getParameter("friend");
        String user = request.getSession().getAttribute("username").toString();
        try {
            if (action.equals("accept")) {
                User.addFriend(user, friend);
            } else if (action.equals("reject")) {
                User.rejectFriendRequest(user, friend);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
