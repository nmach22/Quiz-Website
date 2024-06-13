import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
    private static Connection conn;

    public static Connection getConnection() throws SQLException {
        if (conn == null) {
            // Replace with your MySQL connection details
            String url = "jdbc:mysql://localhost:3306/mysql";
            String user = "root";
            String password = "rootroot";

            conn = DriverManager.getConnection(url, user, password);
        }
        return conn;
    }
}
