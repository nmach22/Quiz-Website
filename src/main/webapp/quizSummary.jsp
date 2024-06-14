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
    <link rel="stylesheet" type="text/css" href="css/main_background.css">
    <style>
        .container {
            display: flex;
            justify-content: space-between;
            align-items: flex-start;
        }

        .left-section,
        .right-section {
            width: 48%; /* Adjust as needed */
        }

        .section {
            margin-bottom: 20px;
        }

        table {
            border-collapse: collapse;
            width: 100%;
        }

        th, td {
            border: 1px solid #dddddd;
            text-align: left;
            padding: 8px;
        }

        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
<%
    String username = request.getParameter("username");
    String description = (String) request.getSession().getAttribute("description");
    String author = (String) request.getSession().getAttribute("author");
    List<Map<String,Object>> pastPerformances = (List<Map<String,Object>>) request.getSession().getAttribute("pastPerformances");
    List<Map<String,Object>> topAllTime = (List<Map<String,Object>>) request.getSession().getAttribute("topAllTime");
    List<Map<String,Object>> topLastDay = (List<Map<String,Object>>) request.getSession().getAttribute("topLastDay");
    List<Map<String,Object>> recentTakers = (List<Map<String,Object>>) request.getSession().getAttribute("recentTakers");
    Map<String,Object> summaryStats = (Map<String,Object>) request.getSession().getAttribute("summaryStats");
%>
<h1>Welcome <%= username %></h1>
<p><strong>Description:</strong> <%= description %></p>
<p><strong>Author:</strong> <a href="userProfile.jsp?username=<%= author %>"><%= author %></a></p>

<div class="container">
    <div class="left-section">
        <div class="section">
            <h2>Past Performances</h2>
            <% if (pastPerformances != null && !pastPerformances.isEmpty()) { %>
            <table>
                <tr>
                    <th>Score</th>
                    <th>Time</th>
                    <th>Date Taken</th>
                </tr>
                <% for (Map<String,Object> performance : pastPerformances) { %>
                <tr>
                    <td><%= performance.get("score") %></td>
                    <td><%= performance.get("time") %></td>
                    <td><%= performance.get("date_taken") %></td>
                </tr>
                <% } %>
            </table>
            <% } else { %>
            <p>No past performances found.</p>
            <% } %>
        </div>

        <div class="section">
            <h2>Top Performers of All Time</h2>
            <% if (topAllTime != null && !topAllTime.isEmpty()) { %>
            <table>
                <tr>
                    <th>Username</th>
                    <th>Score</th>
                </tr>
                <% for (Map<String,Object> performance : topAllTime) { %>
                <tr>
                    <td><%= performance.get("username") %></td>
                    <td><%= performance.get("score") %></td>
                </tr>
                <% } %>
            </table>
            <% } else { %>
            <p>No top performers found.</p>
            <% } %>
        </div>
    </div>

    <div class="right-section">
        <div class="section">
            <h2>Top Performers in the Last Day</h2>
            <% if (topLastDay != null && !topLastDay.isEmpty()) { %>
            <table>
                <tr>
                    <th>Username</th>
                    <th>Score</th>
                </tr>
                <% for (Map<String,Object> performance : topLastDay) { %>
                <tr>
                    <td><%= performance.get("username") %></td>
                    <td><%= performance.get("score") %></td>
                </tr>
                <% } %>
            </table>
            <% } else { %>
            <p>No top performers found in the last day.</p>
            <% } %>
        </div>

        <div class="section">
            <h2>Recent Test Takers</h2>
            <% if (recentTakers != null && !recentTakers.isEmpty()) { %>
            <table>
                <tr>
                    <th>Username</th>
                    <th>Score</th>
                </tr>
                <% for (Map<String,Object> performance : recentTakers) { %>
                <tr>
                    <td><%= performance.get("username") %></td>
                    <td><%= performance.get("score") %></td>
                </tr>
                <% } %>
            </table>
            <% } else { %>
            <p>No recent test takers found.</p>
            <% } %>
        </div>

        <div class="section">
            <h2>Summary Statistics</h2>
            <% if (summaryStats != null) { %>
            <table>
                <tr>
                    <th>Average Score</th>
                    <th>Max Score</th>
                    <th>Min Score</th>
                </tr>
                <tr>
                    <td><%= summaryStats.get("average_score") %></td>
                    <td><%= summaryStats.get("max_score") %></td>
                    <td><%= summaryStats.get("min_score") %></td>
                </tr>
            </table>
            <% } else { %>
            <p>No summary statistics available.</p>
            <% } %>
        </div>
    </div>
</div>

</body>
</html>
