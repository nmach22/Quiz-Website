<%@ page import="main.Manager.Quiz" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="main.Manager.Announcement" %>
<%@ page import="java.util.Vector" %>
<%@ page import="main.Manager.User" %>
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
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" type="text/css" href="css/home-page.css">
    <link rel="stylesheet" type="text/css" href="css/friend-list.css">
    <link rel="stylesheet" type="text/css" href="css/chat.css">

    <script src="js/MessageScript.js"></script>
</head>
<body>

<%@include file="header.jsp" %>
<div class="hp-header">

    <h1>Recent Announcements</h1>
<%--    <button class="achievements-button" onclick="viewAchievements()">--%>
<%--        <i class="fas fa-trophy"></i>--%>
<%--        <span class="badge"><%=achievements%></span>--%>
<%--    </button>--%>
<%--    <%--%>
<%--        try {--%>
<%--            if (AccountManager.isAdmin(username)) {--%>
<%--                out.println("<button class=\"admin-button\" onclick=\"goToAdminPage()\">");--%>
<%--                out.println("<i class=\"fas fa-shield-alt\"></i>");--%>
<%--                out.println("</button>");--%>
<%--            }--%>
<%--        } catch (SQLException e) {--%>
<%--            throw new RuntimeException(e);--%>
<%--        }--%>
<%--    %>--%>
<%--    <form action="index.jsp" method="get">--%>
<%--        <button type="submit" class="announcementsButton">Log Out</button>--%>
<%--    </form>--%>
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
                out.println("<div class='announcement'>");
                out.println("<h2>" + announcement.title + "</h2>");
                out.println("<p><strong>By:</strong> " + announcement.user + "</p>");
                out.println("<p><strong>Date:</strong> " + announcement.created + "</p>");
                out.println("<p>" + announcement.description + "</p>");
                out.println("</div>");
                out.println("<hr>");
            }
        } else {
            out.println("<p>There are no announcements</p>");
        }
    %>
    <form action="announcements.jsp" method="get">
        <input type="hidden" name="username" value="<%=username%>">
        <button type="submit">All Announcements</button>
    </form>
</div>
<div class="friend-list">
    <h2>Friend List</h2>
    <%
        // Retrieve the friends
        ArrayList<String> friendList = null;
        try {
            friendList = User.getFriends(request.getParameter("username"));
        } catch (RuntimeException e) {
            out.println("<div>Error retrieving friends: " + e.getMessage() + "</div>");
        }
    %>
    <ul>
        <%
            if (friendList != null && !friendList.isEmpty()) {
                for (String fr : friendList) {
                    out.println("<li><a href='#' onclick=\"toggleMessageBox('" + fr + "', this)\">" + fr + "</a></li>");
                }
            } else {
                out.println("<li>No friends found.</li>");
            }
        %>
    </ul>
</div>

<div id="chat-container" style="display: none;">
    <div id="friend-name"><%=username%></div>
    <div id="chat-window"></div>

    <input type="text" id="message-input" placeholder="Type a message...">
    <input type="hidden" id="user-name" class="logo" value="<%=loggedInUser%>">
    <button id="send-button" class="btn btn-primary" onclick="sendMessage()">Send</button>

</div>

<script>
    function viewAchievements() {
        window.location.href = 'achievements.jsp?username=<%=username%>';
    }

    function goToAdminPage() {
        window.location.href = 'admin_home_page.jsp?username=<%=username%>';
    }

</script>

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
