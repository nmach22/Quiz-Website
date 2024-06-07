<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 6/6/2024
  Time: 7:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login Page</title>
</head>
<body>
    <h1>Welcome to Quiz Website </h1>
    <p> Log in </p>
    <form action="LoginServlet" method="post">
        UserName: <input type="text" name="username"/> <br/>
        Password: <input type="text" name="pas"/>
        <input type="submit" value="login"/>
    </form>
    <a href="create_new.jsp">Create New Account</a>
</body>
</html>
