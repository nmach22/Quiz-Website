<%@ page import="main.Manager.User" %>
<%@ page import="main.Manager.Achievement" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Vector" %>
<%@ page import="main.Manager.QuizManager" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="main.Manager.QuizManager" %>
<%@ page import="java.util.Objects" %><%--
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
<%
    Vector<Vector<String>> takenQuizzes = User.takenQuizzesByDate(username);
    ArrayList<Achievement> achievements = Achievement.getAchievements(username);
    int countFriends = User.getFriends(username).size();
    Vector<String> createdQuizzes = User.getCreatedQuizzes(username);
    String userBio = AccountManager.getBio(username);
%>
<main id="centerContent">
    <div id="profile-top">
        <div class="user-info">
            <div class="info-top">
                <div class="profile-user">
                    <h2><%=username%></h2>
                    <p class="user-bio"><%=userBio%></p>
                    <%
                        if (!loggedInUser.equals(username)) {
                            if(User.getFriends(loggedInUser).contains(username)){
                    %>
                    <form action="friendRequestHandlerServlet" method="post">
                        <input type="hidden" name="action" value="remove" />
                        <input type="hidden" name="friend" value="<%=username%>" />
                        <button type="submit" class="btn btn-primary">Remove Friend</button>
                    </form>
                    <%
                    }else {
                        try {
                            if(User.doesFriendRequestExist(loggedInUser, username)){

                    %>
                    <form action="friendRequestHandlerServlet" method="post">
                        <input type="hidden" name="action" value="cancel" />
                        <input type="hidden" name="friend" value="<%=username%>" />
                        <button type="submit" class="btn btn-primary">Cancel Friend Request</button>
                    </form>
                    <%
                    } else if(User.doesFriendRequestExist(username, loggedInUser)){

                    %>
                    <form action="friendRequestHandlerServlet" method="post">
                        <input type="hidden" name="action" value="acceptR" />
                        <input type="hidden" name="friend" value="<%=username%>" />
                        <button type="submit" class="btn btn-primary">Accept Friend Request</button>
                    </form>
                    <form action="friendRequestHandlerServlet" method="post">
                        <input type="hidden" name="action" value="rejectR" />
                        <input type="hidden" name="friend" value="<%=username%>" />
                        <button type="submit" class="btn btn-primary">Reject Friend Request</button>
                    </form>
                    <%
                    } else {

                    %>
                    <form action="friendRequestHandlerServlet" method="post">
                        <input type="hidden" name="action" value="add" />
                        <input type="hidden" name="friend" value="<%=username%>" />
                        <button type="submit" class="btn btn-primary">Add Friend</button>
                    </form>
                    <%
                                    }
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    %>
                </div>
                <% if (Objects.equals(username, loggedInUser)){%>
                <div class="actions">
                    <a id="settings-link" href="settings.jsp?username=<%=username%>" title="Edit Settings">
                        <i class="fas fa-edit"></i>
                    </a>
                </div>
                <%}%>


            </div>
            <div class="top-stats">
                <div class="stats-item">
                    <div class="number">
                        <%=takenQuizzes.size() %>
                    </div>
                    <div class="label">Quizzes Played</div>
                </div>
                <div class="stats-item">
                    <div class="number">
                        <%= countFriends%>
                    </div>
                    <div class="label">Friends</div>
                </div>
                <div class="stats-item">
                    <div class="number">
                        <%=createdQuizzes.size()%>
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
                        <%=achievements.size()%>
                    </div>
                    <div class="label">Achievements</div>
                </div>
            </div>
            <div class="activity-body">
                <h2>Activity</h2>
                <div class="achievements">
                    <h3 onclick="toggleDropdown(this)">
                        Achievements
                        <i class="fas fa-chevron-down toggle-icon"></i>
                    </h3>
                    <ul>
                        <%
                            if (!achievements.isEmpty()) {
                                for (Achievement achievement : achievements) {
                        %>
                        <li>
                            <div class="achievement">
                                <span class="action-text">Earned the badge</span>
                                <div class="activity-item">
                                    <%= achievement.type%>
                                </div>
                                <div class="time">
                                    <%= achievement.created%>
                                </div>
                            </div>
                        </li>
                        <%
                            }
                        } else {
                        %>
                        <li>No achievements earned yet.</li>
                        <%
                            }
                        %>
                    </ul>
                </div>
                <div class="quizzes-taken">
                    <h3 onclick="toggleDropdown(this)">
                        Quizzes Taken
                        <i class="fas fa-chevron-down toggle-icon"></i>
                    </h3>
                    <ul>
                        <%
                            if (!takenQuizzes.isEmpty()) {
                                for (Vector<String> v : takenQuizzes) {
                        %>
                        <li>
                            <div class="quiz-taken">
                                <span class="action-text">Scored <%=v.get(1)%> on the quiz</span>
                                <div class="activity-item">
                                    <%= QuizManager.getQuizName(v.get(0))%>
                                </div>
                                <div class="time">
                                    <%= v.get(2)%>
                                </div>
                            </div>
                        </li>
                        <%
                            }
                        } else {
                        %>
                        <li>No quizzes taken yet.</li>
                        <%
                            }
                        %>
                    </ul>
                </div>
                <div class="quizzes-created">
                    <h3 onclick="toggleDropdown(this)">
                        Quizzes Created
                        <i class="fas fa-chevron-down toggle-icon"></i>
                    </h3>
                    <ul>
                        <%
                            if (!createdQuizzes.isEmpty()) {
                                for (String quiz : createdQuizzes) {
                        %>
                        <li>
                            <div class="quiz-created">
                                <span class="action-text">Quiz Created</span>
                                <div class="activity-item">
                                    <%= QuizManager.getQuizName(quiz)%>
                                </div>
                                <div class="time">
                                    <%= QuizManager.getQuizCreationDate(quiz)%>
                                </div>
                            </div>
                        </li>
                        <%
                            }
                        } else {
                        %>
                        <li>No quizzes created yet.</li>
                        <%
                            }
                        %>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</main>

<script>
    function toggleDropdown(header) {
        const icon = header.querySelector('.toggle-icon');
        const ul = header.nextElementSibling;

        if (ul.classList.contains('show')) {
            ul.classList.remove('show');
            icon.classList.remove('rotated');
        } else {
            ul.classList.add('show');
            icon.classList.add('rotated');
        }
    }

</script>
</body>
</html>
