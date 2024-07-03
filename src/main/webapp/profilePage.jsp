<%@ page import="main.Manager.User" %><%--
  Created by IntelliJ IDEA.
  main.Manager.User: main.Manager.User
  Date: 6/9/2024
  Time: 3:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile Page</title>
    <link rel="stylesheet" type="text/css" href="css/main_background.css">
</head>
<body>
    <%
      String user = request.getParameter("user");
    %>
    <h1>
        Profile of <%= user%>
    </h1>
    <div>
        <p> Friends: </p>
        <ul>
            <%
                User userMan = (User) application.getAttribute("User Manager");
                if (userMan != null) {
                    for (String friend : userMan.getFriends(user)) {
            %>
            <li><%= friend %></li>
            <%
                }
            } else {
            %>
            <li>No friends found.</li>
            <%
                }
            %>
        </ul>
    </div>
</body>
</html>
