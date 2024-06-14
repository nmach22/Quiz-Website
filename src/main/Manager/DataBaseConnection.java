package main.Manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
    private static Connection conn;

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        if (conn == null) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/mysql";
            String user = "root";
            String password = "rootroot";

            conn = DriverManager.getConnection(url, user, password);
        }
        return conn;
    }
}
