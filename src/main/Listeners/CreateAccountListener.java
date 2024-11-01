package main.Listeners;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;

import main.Manager.AccountManager;


import main.Manager.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

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

        try {
            con.setAttribute("User Manager", new User());
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Stop the abandoned connection cleanup thread
        AbandonedConnectionCleanupThread.checkedShutdown();
    }

}
