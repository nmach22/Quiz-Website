<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Kato
  Date: 07-Jun-24
  Time: 5:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Quiz</title>
    <link rel="stylesheet" type="text/css" href="css/global.css">
    <link rel="stylesheet" type="text/css" href="css/quiz-summary.css">
</head>
<body>
<%@include file="header.jsp" %>

<%
    String quiz_id = request.getParameter("quiz_id");
    String username = request.getParameter("username");
    String description = (String) request.getSession().getAttribute("description");
    String author = (String) request.getSession().getAttribute("author");
    List<Map<String, Object>> pastPerformances = (List<Map<String, Object>>) request.getSession().getAttribute("pastPerformances");
    List<Map<String, Object>> topAllTime = (List<Map<String, Object>>) request.getSession().getAttribute("topAllTime");
    List<Map<String, Object>> topLastDay = (List<Map<String, Object>>) request.getSession().getAttribute("topLastDay");
    List<Map<String, Object>> recentTakers = (List<Map<String, Object>>) request.getSession().getAttribute("recentTakers");
    Map<String, Object> summaryStats = (Map<String, Object>) request.getSession().getAttribute("summaryStats");
%>
<%
    if (!username.equals("null")) {
%>
<div class="quiz-summary-container rounded p-3">
    <h1>Welcome <%= username %>
    </h1>
    <%
    } else {
    %>
    <h1>Cannot Take Quiz As A Guest</h1>
    <%
        }
    %>
    <p><strong>Description:</strong> <%= description %>
    </p>
    <p class="link-container"><strong>Author: </strong> <a class="h5" href="profilePage.jsp?username=<%= author %>"><%= author %>
    </a></p>

    <div class="d-flex flex-column gap-3">
        <div class="left-section d-flex flex-column gap-3">
            <div class="section">
                <h4>Past Performances</h4>
                <% if (pastPerformances != null && !pastPerformances.isEmpty()) { %>
                <table>
                    <tr>
                        <th>Score</th>
                        <th>Time</th>
                        <th>Date Taken</th>
                    </tr>
                    <% for (Map<String, Object> performance : pastPerformances) { %>
                    <tr>
                        <td><%= performance.get("score") %>
                        </td>
                        <%
                            int totalSeconds = (int) performance.get("time");
                            int hours = totalSeconds / 3600;
                            int minutes = (totalSeconds % 3600) / 60;
                            int seconds = totalSeconds % 60;

                            String time = String.format("%02d:%02d:%02d", hours, minutes, seconds);
                        %>
                        <td><%=time %>
                        </td>
                        <td><%= performance.get("date_taken") %>
                        </td>
                    </tr>
                    <% } %>
                </table>
                <% } else { %>
                <p>No past performances found.</p>
                <% } %>
            </div>

            <div class="section">
                <h4>Top Performers of All Time</h4>
                <% if (topAllTime != null && !topAllTime.isEmpty()) { %>
                <table>
                    <tr>
                        <th>Username</th>
                        <th>Score</th>
                    </tr>
                    <% for (Map<String, Object> performance : topAllTime) { %>
                    <tr>
                        <td><%= performance.get("username") %>
                        </td>
                        <td><%= performance.get("score") %>
                        </td>
                    </tr>
                    <% } %>
                </table>
                <% } else { %>
                <p>No top performers found.</p>
                <% } %>
            </div>
        </div>

        <div class="right-section d-flex flex-column gap-3">
            <div class="section">
                <h4>Top Performers in the Last Day</h4>
                <% if (topLastDay != null && !topLastDay.isEmpty()) { %>
                <table>
                    <tr>
                        <th>Username</th>
                        <th>Score</th>
                    </tr>
                    <% for (Map<String, Object> performance : topLastDay) { %>
                    <tr>
                        <td><%= performance.get("username") %>
                        </td>
                        <td><%= performance.get("score") %>
                        </td>
                    </tr>
                    <% } %>
                </table>
                <% } else { %>
                <p>No top performers found in the last day.</p>
                <% } %>
            </div>

            <div class="section">
                <h4>Recent Test Takers</h4>
                <% if (recentTakers != null && !recentTakers.isEmpty()) { %>
                <table>
                    <tr>
                        <th>Username</th>
                        <th>Score</th>
                    </tr>
                    <% for (Map<String, Object> performance : recentTakers) { %>
                    <tr>
                        <td><%= performance.get("username") %>
                        </td>
                        <td><%= performance.get("score") %>
                        </td>
                    </tr>
                    <% } %>
                </table>
                <% } else { %>
                <p>No recent main.test takers found.</p>
                <% } %>
            </div>

            <div class="section">
                <h4>Summary Statistics</h4>
                <% if (summaryStats != null) { %>
                <table class="table-container">
                    <tr>
                        <th>Average Score</th>
                        <th>Max Score</th>
                        <th>Min Score</th>
                    </tr>
                    <tr>
                        <td><%= summaryStats.get("average_score") %>
                        </td>
                        <td><%= summaryStats.get("max_score") %>
                        </td>
                        <td><%= summaryStats.get("min_score") %>
                        </td>
                    </tr>
                </table>
                <% } else { %>
                <p>No summary statistics available.</p>
                <% } %>
            </div>
        </div>
    </div>
    <%
        if (!username.equals("null")) {
    %>
    <form action="TakeQuizServlet" method="post">
        <input type="hidden" name="quiz_id" value="<%=quiz_id%>">
        <input type="hidden" name="username" value=${username}>
        <input class="btn btn-primary mt-3" type="submit" value="Take Quiz">
    </form>
    <%
        }
    %>
</div>
</body>
</html>
