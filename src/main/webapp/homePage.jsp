<%@ page import="main.Manager.Quiz" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="main.Manager.Announcement" %>
<%@ page import="java.util.Vector" %>
<%@ page import="main.Manager.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Quiz Website</title>
    <link rel="stylesheet" type="text/css" href="css/index.css">
    <link rel="stylesheet" type="text/css" href="css/main_background.css">
    <link rel="stylesheet" type="text/css" href="css/friend-list.css">
    <script src="js/MessageScript.js"></script>

</head>
<body>
<%@include file="header.jsp" %>
<div class="announcements">
    <div class="title"> ANNOUNCEMENTS</div>
    <%
        ArrayList<Announcement> announcements = Announcement.getAnnouncements(0);
        for (Announcement ann : announcements) {
            out.println("<div class=\"announcement-item\">");
            out.println("<div class=\"singleTitle\">" + ann.title + "</div>");
            out.println("<div class=\"description\">" + ann.description + "</div>");
            out.println("<div class=\"from\">From " + ann.user + "</div>");
            out.println("</div>");
        }
    %>
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
<a href="index.jsp">Go to the Website</a>
</body>
</html>