import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
@WebServlet(urlPatterns = {"/LoginWeb/RemoveServlet"})
public class RemoveServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AccountManager accMan = (AccountManager) getServletContext().getAttribute("Account Manager");
        try {
            if(accMan.removeAcc(req.getParameter("user_id"))){
                resp.setContentType("text/plain");
                PrintWriter out = resp.getWriter();
                out.print("Successfully deleted " + req.getParameter("user_id"));
            }else{
                resp.setContentType("text/plain");
                PrintWriter out = resp.getWriter();
                out.print("User " + req.getParameter("user_id") + " does not exist");
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


