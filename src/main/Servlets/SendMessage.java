package main.Servlets;
import main.Manager.DataBaseConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/SendMessageServlet")
public class SendMessage extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        String friendName = request.getParameter("friendName");
        String message = request.getParameter("message");

        if (username == null) {
            response.sendRedirect("index.jsp");
        }
        if (friendName == null || message == null || friendName.isEmpty() || message.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        try {
            Connection conn = DataBaseConnection.getConnection();
            String sql = "INSERT INTO chat (user_from, user_to, message) VALUES (?, ?, ?)";
            try {
                PreparedStatement st = conn.prepareStatement(sql);
                st.setString(1, username);
                st.setString(2, friendName);
                st.setString(3, message);
                st.executeUpdate();


            } catch (SQLException e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return;
            }
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}