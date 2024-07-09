<%--
  Created by IntelliJ IDEA.
  User: alexsurmava
  Date: 14.06.24
  Time: 20:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="main.Manager.Announcement" %>
<!DOCTYPE html>
<%
    String username = request.getParameter("username");
%>
<html>
<head>
    <title>All Announcements</title>
    <link rel="stylesheet" type="text/css" href="css/allAnnouncements.css">
    <link rel="stylesheet" type="text/css" href="css/global.css">
</head>
<body>
<h1>All Announcements</h1>
<%
    ArrayList<Announcement> announcements = null;
    try {
        announcements = Announcement.getAnnouncements(0);
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
<form action="homePage.jsp" method="get">
    <input type="hidden" name="username" value=<%=username%>>
    <button type="submit">Back to home page</button>
</form>
</body>
</html>
