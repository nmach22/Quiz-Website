package main.Servlets;

import main.Manager.AccountManager;
import main.Manager.Announcement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/createAnnouncement")
public class makeAnnouncementServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String user = request.getParameter("username");
        if (title == null || title.isEmpty() || description == null || description.isEmpty()) {
            response.getWriter().write("Title and description must not be empty.");
            return;
        }
        Announcement.makeAnnouncement(description, title, user);
        response.getWriter().write("Announcement created successfully.");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
