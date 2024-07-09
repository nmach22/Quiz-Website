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
@WebServlet(urlPatterns = {"/removeServlet"})
public class RemoveServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AccountManager accMan = (AccountManager) getServletContext().getAttribute("Account Manager");
        try {
            String userId = req.getParameter("name");
            if(accMan.removeAcc(userId) == 1){
                resp.setContentType("text/plain");
                PrintWriter out = resp.getWriter();
                out.print("Successfully deleted " + userId);
            } else if(accMan.removeAcc(userId) == 0) {
                resp.setContentType("text/plain");
                PrintWriter out = resp.getWriter();
                out.print("main.main.test.User " + userId +" does not exist");
            }else{
                resp.setContentType("text/plain");
                PrintWriter out = resp.getWriter();
                out.print("You can not remove an admin: " + userId);
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


