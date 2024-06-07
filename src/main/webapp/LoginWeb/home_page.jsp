<%--
  Created by IntelliJ IDEA.
  User: alexsurmava
  Date: 07.06.24
  Time: 17:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home Page</title>
</head>
<body>
    <%
        String username = request.getParameter("username");
    %>
    <h1>Welcome <%= username%></h1>
</body>
</html>
