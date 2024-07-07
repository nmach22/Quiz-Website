<%--
  Created by IntelliJ IDEA.
  main.Manager.User: main.Manager.User
  Date: 6/6/2024
  Time: 7:51 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>create new account failed</title>
    <link rel="stylesheet" type="text/css" href="css/index.css">
</head>
<div class="background-container"></div>
<div class = "login-popup">
    <h1> Sign in failed</h1> <br/>
    <h2> name "<%= request.getParameter("username") %>" is already taken</h2>
    <p> Enter another username</p>
    <form action="CreateAccountServlet" method="post">
        Username: <input type="text" name="username"/> <br/>
        Password: <input type="text" name="pas"/>
        <input type="submit" value="create"/>
    </form>
</div>
</html>
