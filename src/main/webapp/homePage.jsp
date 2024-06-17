<%@ page import="main.Manager.Quiz" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="main.Manager.Announcement" %>
<%@ page import="main.Manager.User" %>
<%@ page import="main.Manager.AccountManager" %>
<%@ page import="main.Manager.Achievement" %>
<%@ page import="java.sql.SQLException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<%
    String username = request.getParameter("username");
    int achievements = 0;
    try {
        achievements = Achievement.getAchievements(username).size();
    } catch (SQLException | ClassNotFoundException e) {
        throw new RuntimeException(e);
    }
%>
<html>
<head>
    <title>Home Page</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" type="text/css" href="css/home-page.css">
</head>
<body>
<div class="header">
    <h1>Recent Announcements</h1>
    <button class="achievements-button" onclick="viewAchievements()">
        <i class="fas fa-trophy"></i>
        <span class="badge"><%=achievements%></span>
    </button>
    <%
        try {
            if(AccountManager.isAdmin(username)){
                out.println("<button class=\"admin-button\" onclick=\"goToAdminPage()\">" );
                out.println("<i class=\"fas fa-shield-alt\"></i>" );
                out.println("</button>" );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    %>
    <form action="index.jsp" method="get">
        <button type="submit">Log Out</button>
    </form>
</div>
<div class="announcements">
<%
    ArrayList<Announcement> announcements = null;
    try {
        announcements = Announcement.getAnnouncements(5);
    } catch (Exception e) {
        e.printStackTrace();
    }

    if (announcements != null && announcements.size() > 0) {
        for (Announcement announcement : announcements) {
            out.println("<div class=" + "announcement>");
            out.println("<h2>" + announcement.title + "</h2>");
            out.println("<p><strong>By:</strong> " + announcement.user + "</p>");
            out.println("<p><strong>Date:</strong> " + announcement.created + "</p>");
            out.println("<p>" + announcement.description + "</p>");
            out.println("</div>");
            out.println("</hr>");
        }
    } else {
        out.println("<p>There are no announcements</p>");
    }
%>
<form action="announcements.jsp" method="get">
    <input type="hidden" name="username" value=<%=username%>>
    <button type="submit">All Announcements</button>
</form>
</div>
    <script>
        function viewAchievements() {
            window.location.href = 'achievements.jsp?username=<%=username%>';
        }
        function goToAdminPage() {
            window.location.href = 'admin_home_page.jsp?username=<%=username%>';
        }
    </script>
</body>
</html>