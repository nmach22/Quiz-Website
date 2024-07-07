package main.Servlets;

import com.google.gson.Gson;
import main.Manager.Statistics;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/fetchStatisticsServlet")
public class fetchStatisticsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String timeRange = request.getParameter("timeRange");
        if (timeRange == null) {
            timeRange = "day";
        }
        try {
            ArrayList<Statistics> res = Statistics.getUserStatistics(timeRange);
            String json = new Gson().toJson(res);
            try (PrintWriter out = response.getWriter()) {
                out.print(json);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
