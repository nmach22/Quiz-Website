package main.Servlets;

import main.Manager.ChatMessage;
import main.Manager.DataBaseConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/FetchMessagesServlet")
public class FetchMessagesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userFrom = request.getParameter("user_from");

        if (userFrom == null || userFrom.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // Fetch messages from database
        List<ChatMessage> messages = fetchMessages(userFrom);

        // Convert messages to JSON and send response
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.print("[");
            for (int i = 0; i < messages.size(); i++) {
                ChatMessage msg = messages.get(i);
                out.print("{\"user_from\":\"" + msg.getUserFrom() + "\",\"message\":\"" + msg.getMessage() + "\"}");
                if (i < messages.size() - 1) {
                    out.print(",");
                }
            }
            out.print("]");
        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private List<ChatMessage> fetchMessages(String userFrom) {
        List<ChatMessage> messages = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DataBaseConnection.getConnection();
            String sql = "SELECT user_from, user_to, message FROM chat " +
                    "WHERE (user_from = ? OR user_to = ?) " +
                    "ORDER BY sentDate ASC";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, userFrom);
            stmt.setString(2, userFrom);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String from = rs.getString("user_from");
                String to = rs.getString("user_to");
                String message = rs.getString("message");
                // Determine the sender's name
                String senderName = from.equals(userFrom) ? to : from;
                messages.add(new ChatMessage(senderName, message));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
//                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return messages;
    }
}
