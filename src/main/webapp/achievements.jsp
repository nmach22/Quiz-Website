<%--
  Created by IntelliJ IDEA.
  User: alexsurmava
  Date: 16.06.24
  Time: 14:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="main.Manager.Achievement" %>
<%@ page import="java.util.ArrayList" %>
<html>
<head>
    <title>Achievements</title>
    <link rel="stylesheet" type="text/css" href="css/achievements.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>
<body>
<div class="achievements">
    <%
        String username = request.getParameter("username");
        ArrayList<Achievement> achievements = null;
        try {
            achievements = Achievement.getAchievements(username);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (achievements != null && achievements.size() > 0) {
            for (Achievement achievement : achievements) {
                out.println("<div class='achievement'>");
                out.println("<i class='" + achievement.badge + "'></i>");
                out.println("<h2>" + achievement.type + "</h2>");
                out.println("<p>" + achievement.created + "</p>");
                out.println("<p>" + achievement.description + "</p>");
                out.println("</div>");
            }
        } else {
            out.println("<p>No achievements found.</p>");
        }
    %>
</div>
    <form action="homePage.jsp" method="get">
        <input type="hidden" name="username" value=<%=username%>>
        <button type="submit">Back to home page</button>
    </form>
</body>
</html>
