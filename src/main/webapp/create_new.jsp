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
    <link rel="stylesheet" type="text/css" href="css/main_background.css">
</head>
<body>
    <h1> Create new account</h1>
    <p> Fill in </p>
    <form action="CreateAccountServlet" method="post">
        First Name: <input type = "text" name="firstName"/> <br/>
        Last Name: <input type = "text" name="lastName"/> <br/>
        Username: <input type="text" name="username"/> <br/>
        password: <input type="text" name = "pas"/>
        <input type="submit" value="Create"/>
    </form>
</body>
</html>
