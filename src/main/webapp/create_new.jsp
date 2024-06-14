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
</head>
<body>
    <h1> Create new account</h1>
    <p> Enter username and password </p>
    <form action="CreateAccountServlet" method="post">
        Username: <input type="text" name="username"/> <br/>
        password: <input type="text" name = "pas"/>
        <input type="submit" value="Create"/>
    </form>
</body>
</html>
