<%--
  Created by IntelliJ IDEA.
  User: Kato
  Date: 07-Jul-24
  Time: 3:03 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Score</title>
</head>
<body>
<p>Your score is <%= request.getSession().getAttribute("score") %></p>
<%--<p>It took you <%= ((int) request.getSession().getAttribute("duration")) - ((int) request.getSession().getAttribute("timeLeft"))%></p>--%>
<form action="TakeQuizServlet" method="post">
    <input type="hidden" name="quiz_id" value="1">
    <input type="hidden" name="username" value="kato">
    <input type="submit" value="Retake Quiz">
</form>
</body>
</html>