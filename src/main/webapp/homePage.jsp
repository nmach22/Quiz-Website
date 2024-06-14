<%@ page import="main.Manager.Quiz" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="main.Manager.Announcement" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Recent Announcements</title>
</head>
<body>
<h1>Recent Announcements</h1>
<%
    ArrayList<Announcement> announcements = null;
    try {
        announcements = Announcement.getAnnouncements(5);
    } catch (Exception e) {
        e.printStackTrace();
    }

    if (announcements != null && announcements.size() > 0) {
        for (Announcement announcement : announcements) {
            out.println("<div>");
            out.println("<h2>" + announcement.title + "</h2>");
            out.println("<p><strong>By:</strong> " + announcement.user + "</p>");
            out.println("<p><strong>Date:</strong> " + announcement.created + "</p>");
            out.println("<p>" + announcement.description + "</p>");
            out.println("</div><hr/>");
        }
    } else {
        out.println("<p>No announcements found.</p>");
    }
%>
<form action="announcements.jsp" method="get">
    <button type="submit">All Announcements</button>
</form>
</body>
</html>