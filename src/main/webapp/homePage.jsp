<%@ page import="main.Manager.Quiz" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="main.Manager.Announcement" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Quiz Website</title>
    <link rel="stylesheet" type="text/css" href="css/index.css">
    <link rel="stylesheet" type="text/css" href="css/main_background.css">
</head>
<body>
<%@include file="header.jsp"%>
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
<a href="index.jsp">Go to the Website</a>
</body>
</html>