import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

@WebListener
public class CreateAccountListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        AccountManager accMan = null;
        try {
            accMan = new AccountManager();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

        ServletContext con = servletContextEvent.getServletContext();
        con.setAttribute("Account Manager", accMan);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
