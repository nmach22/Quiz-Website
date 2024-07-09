package main.Servlets;

import main.Manager.AccountManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/CreateAccountServlet")
public class CreateAccountServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AccountManager accMan =(AccountManager)getServletContext().getAttribute("Account Manager");
        try {
            if(!accMan.hasAcc(req.getParameter("username"))){
                accMan.createAcc(req.getParameter("username"), req.getParameter("pas"), req.getParameter("firstName"), req.getParameter("lastName"));
                req.getSession().setAttribute("username", req.getParameter("username"));
                RequestDispatcher dis = req.getRequestDispatcher("homePage.jsp");
                dis.forward(req,resp);
            }else{
                RequestDispatcher dis =req.getRequestDispatcher("create_new_failed.jsp");
                dis.forward(req,resp);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
}
