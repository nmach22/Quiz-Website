<%--
  Created by IntelliJ IDEA.
  main.Manager.User: main.Manager.User
  Date: 6/6/2024
  Time: 7:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login Page</title>
<%--    <link rel="stylesheet" type="text/css" href="css/main_background.css">--%>
    <link rel="stylesheet" type="text/css" href="css/index.css">
    <script src="js/index.js"></script> 
</head>
<div class="background-container"> </div>
        <div class="login-popup">
            <h1>Welcome to QuizzerRank </h1>
            <p> Log in </p>
            <form action="LoginServlet" method="post">
                Username: <input type="text" name="username"/> <br/>
                Password: <input type="password" name="pas"/>
                <input type="submit" value="login"/>
            </form>
            <a href="create_new.jsp">Create New Account</a>
            <a href="homePage.jsp">Visit As A Guest</a>
        </div>
</html>
