import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/LoginWeb/LoginServlet"})
public class LoginAccountServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AccountManager accMan = (AccountManager)getServletContext().getAttribute("Account Manager");
        try {
            if(!accMan.isCorrect(req.getParameter("username"), req.getParameter("pas"))){
                RequestDispatcher dis = req.getRequestDispatcher("login_failed.jsp");
                dis.forward(req,resp);
            }else{
                RequestDispatcher dis = req.getRequestDispatcher("home_page.jsp");
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
