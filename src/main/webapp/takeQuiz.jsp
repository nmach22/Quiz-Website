<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Kato
  Date: 05-Jul-24
  Time: 12:47 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Take Quiz</title>
</head>
<body>
<%
List<Map<String,Object>> questions = (List<Map<String,Object>>) request.getSession().getAttribute("questions");

%>
</body>
</html>
