<%--
  Created by IntelliJ IDEA.
  main.Manager.User: main.Manager.User
  Date: 6/6/2024
  Time: 7:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create New Account</title>
    <link rel="stylesheet" type="text/css" href="css/index.css">
</head>
<div class="background-container"> </div>
<div class="login-popup">
    <h1> Create new account</h1>
    <p> Fill in </p>
    <form action="CreateAccountServlet" method="post">
        First Name: <input type = "text" name="firstName" required/> <br/>
        Last Name: <input type = "text" name="lastName" required/> <br/>
        Username: <input type="text" name="username" required/> <br/>
        Password: <input type="password" name = "pas" required/>
        <input type="submit" value="Create"/>
    </form>
</div>
</html>
