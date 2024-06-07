<%--
  Created by IntelliJ IDEA.
  User: nika
  Date: 6/7/24
  Time: 5:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%

    String username = request.getParameter("username");

%>

<html>
<head>
    <title>Welcome <%=username%></title>
    <h>Welcome <%=username%> (admin)</h>

</head>
<body>

<div>
    <input name="announcement_field" type="text" value=""/>
    <button type="submit">Create announcement</button>

</div>

<div>
    <form id = "removeUser" action="RemoveServlet" method="post">
        <input id = "userid" name="user_id" type="text" value=""/>
        <button type="submit">Remove User</button>
    </form>
    <button type="submit">Promote User to Admin</button>

</div>
<div>
    <input name="quiz_id" type="text" value=""/>
    <button type="submit">Remove Quiz</button>
    <button type="submit">Remove Quiz History</button>
</div>

<div>
    <a href="site_statistics.jsp">Site Statistics</a>
</div>
</body>
</html>

