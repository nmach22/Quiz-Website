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
    <form action="QuizSummaryServlet" method="post">
        <input type="hidden" name="quiz_id" value="1">
        <input type="hidden" name="username" value="kato">
        <input type="submit" value="TAKE QUIZ">
    </form>
</body>
</html>
