package main.Servlets;


import main.Manager.AccountManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

@WebServlet("/SaveChangesServlet")
public class SaveSettingsChangesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("username");
        AccountManager accMan = (AccountManager) getServletContext().getAttribute("Account Manager");
        try {
            String bio = AccountManager.getBio(userName);
            String fn = AccountManager.getFN(userName);
            String ln = AccountManager.getLN(userName);
            resp.setContentType("text/plain");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(bio);
            resp.getWriter().write("/"+fn);
            resp.getWriter().write("/"+ln);
        } catch (SQLException e) {
            throw new ServletException("Error fetching bio", e);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AccountManager accMan = (AccountManager) getServletContext().getAttribute("Account Manager");
        String userName = req.getParameter("username");
        if(!Objects.equals(req.getParameter("user-bio"), "")){
            try {
                accMan.changeBio(req.getParameter("user-bio"), userName);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(!Objects.equals(req.getParameter("user-pas"), "")){
            try {
                accMan.changePas(req.getParameter("user-pas"), userName);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(!Objects.equals(req.getParameter("firstname"), "")){
            try {
                accMan.changeFN(req.getParameter("firstname"), userName);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(!Objects.equals(req.getParameter("lastname"), "")){
            try {
                accMan.changeLN(req.getParameter("lastname"), userName);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        RequestDispatcher dis = req.getRequestDispatcher("settings.jsp");
        dis.forward(req,resp);
    }
}
