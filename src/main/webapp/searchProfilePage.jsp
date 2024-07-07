<%@ page import="main.Manager.User" %><%--
  Created by IntelliJ IDEA.
  main.Manager.User: main.Manager.User
  Date: 6/9/2024
  Time: 3:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<%
    String username = request.getParameter("username");
%>
<head>
    <title>Profile Page</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" type="text/css" href="css/ProfilePage.css">

</head>
<body>
<%@include file="header.jsp"%>
<main id="centerContent">
    <div id="profile-top">
        <div class="user-info">
            <div class="info-top">
                <div class="profile-user">
                    <h2><%=username%></h2>
                </div>
                <div class="actions">
                    <a id="settings-link" href="settings.jsp?username=<%=username%>" title="Edit Settings">
                        <i class="fas fa-edit"></i>
                    </a>
                </div>
            </div>
            <div class="top-stats">
                <div class="stats-item">
                    <div class="number">
                        0
                    </div>
                    <div class="label">Quizzes Played</div>
                </div>
                <div class="stats-item">
                    <div class="number">
                        0
                    </div>
                    <div class="label">Friends</div>
                </div>
                <div class="stats-item">
                    <div class="number">
                        0
                    </div>
                    <div class="label">Quizzes Created</div>
                </div>
                <div class="stats-item">
                    <div class="number">
                        0
                    </div>
                    <div class="label">Score</div>
                </div>
                <div class="stats-item">
                    <div class="number">
                        0
                    </div>
                    <div class="label">Badges</div>
                </div>
            </div>
            <div class="activity-body">
                <h2>Activity</h2>
            </div>
        </div>
    </div>
</main>
</body>
</html>
