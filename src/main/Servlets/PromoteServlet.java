package main.Servlets;

import main.Manager.AccountManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/promoteServlet"})
public class PromoteServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AccountManager accMan = (AccountManager) getServletContext().getAttribute("Account Manager");
        String username = req.getParameter("name");
        try {
            if(accMan.promoteToAdmin(username) == 1){
                resp.setContentType("text/plain");
                PrintWriter out = resp.getWriter();
                out.print("Successfully promoted " + username + " to admin");
            }else if(accMan.promoteToAdmin(username) == 2){
                resp.setContentType("text/plain");
                PrintWriter out = resp.getWriter();
                out.print("main.main.test.User " + username + " is already admin");
            }else{
                resp.setContentType("text/plain");
                PrintWriter out = resp.getWriter();
                out.print("main.main.test.User " + username + " does not exist");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
