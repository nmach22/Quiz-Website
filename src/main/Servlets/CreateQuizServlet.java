package main.Servlets;

import com.google.gson.Gson;
import main.Manager.ChatMessage;
import main.Manager.DataBaseConnection;
import org.json.JSONArray;
import org.json.JSONObject;

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


@WebServlet("/CreateQuizServlet")
public class CreateQuizServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String random = (String) request.getParameter("random");
        String onePage = (String) request.getParameter("onePage");
        String immediateCorrection = (String) request.getParameter("immediateCorrection");
        String practiceMode = (String) request.getParameter("practiceMode");
        String questions = (String) request.getParameter("questions");

        JSONObject obj = new JSONObject(questions);

        JSONArray arr = obj.getJSONArray("questions");
        for (int i = 0; i < arr.length(); i++) {
            System.out.println(arr.get(i));
        }
        System.out.println(random);
        System.out.println(onePage);
        System.out.println(immediateCorrection);
        System.out.println(practiceMode);

        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
}
