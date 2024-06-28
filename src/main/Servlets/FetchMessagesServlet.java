package main.Servlets;

import com.google.gson.Gson;
import main.Manager.ChatMessage;
import main.Manager.DataBaseConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@WebServlet("/FetchMessagesServlet")
public class FetchMessagesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String friendName = request.getParameter("user_from");

        if (username == null || friendName == null || username.isEmpty() || friendName.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            Connection conn = DataBaseConnection.getConnection();
            String sql = "SELECT user_from, user_to, message, sentDate FROM chat " +
                    "WHERE (user_from = ? AND user_to = ?) OR (user_from = ? AND user_to = ?) " +
                    "ORDER BY sentDate ASC";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, username);
            st.setString(2, friendName);
            st.setString(3, friendName);
            st.setString(4, username);
            ResultSet rs = st.executeQuery();

            ArrayList<Map<String, String>> messages = new ArrayList<>();
            while (rs.next()) {
                Map<String, String> message = new HashMap<>();
                message.put("senderName", rs.getString("user_from"));
                message.put("message", rs.getString("message"));
                messages.add(message);
            }

            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.println(new Gson().toJson(messages));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
