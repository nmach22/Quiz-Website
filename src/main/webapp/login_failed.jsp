<%--
  Created by IntelliJ IDEA.
  main.Manager.User: main.Manager.User
  Date: 6/7/2024
  Time: 5:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>
        Please try again
    </title>
    <link rel="stylesheet" type="text/css" href="css/index.css">
    <link rel="stylesheet" type="text/css" href="css/global.css">
</head>
<div class="background-container"></div>
<div class="login-popup">
    <h1>
        Please try again
    </h1>
    <p>
        Either your username or password is incorrect. Try again
    </p>
    <form action = "LoginServlet" method = "post">
        Username: <input type="text" name="username"/> <br/>
        Password: <input type="text" name="pas"/>
        <input type="submit" value="login"/>
    </form>
    <a href="create_new.jsp">Create New Account</a>
</div>
</html>
