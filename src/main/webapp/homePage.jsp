<%@ page import="java.util.ArrayList" %>
<%@ page import="main.Manager.Announcement" %>
<%@ page import="main.Manager.User" %>
<%@ page import="main.Manager.User" %>
<%@ page import="main.Manager.Achievement" %>
<%@ page import="java.sql.SQLException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<%
    String username = request.getParameter("username");
    int achievements = 0;
    try {
        achievements = Achievement.getAchievements(username).size();
    } catch (SQLException | ClassNotFoundException e) {
        throw new RuntimeException(e);
    }
%>
<html>
<head>
    <title>Home Page</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" type="text/css" href="css/friend-list.css">
    <link rel="stylesheet" type="text/css" href="css/chat.css">
    <link rel="stylesheet" type="text/css" href="css/home-page.css">

    <script src="js/MessageScript.js"></script>
</head>
<body>

<%@include file="header.jsp" %>

<div class="d-flex justify-content-between gap-4 w-100 px-5">
    <div class="d-flex flex-column w-33">
        <div class="d-flex justify-content-between">
            <h2 class="mb-2 text-white whitespace-nowrap">Recent Announcements</h2>
            <form class="mb-2" action="announcements.jsp" method="get">
                <input type="hidden" name="username" value="<%=username%>">
                <button class="btn btn-primary whitespace-nowrap" type="submit">See All</button>
            </form>
        </div>

        <div class="announcements">
            <%
                ArrayList<Announcement> announcements = null;
                try {
                    announcements = Announcement.getAnnouncements(5);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (announcements != null && announcements.size() > 0) {
                    for (Announcement announcement : announcements) {
                        out.println("<div class='announcement rounded'>");
                        out.println("<h2>" + announcement.title + "</h2>");
                        out.println("<p><strong>By:</strong> " + announcement.user + "</p>");
                        out.println("<p><strong>Date:</strong> " + announcement.created + "</p>");
                        out.println("<p>" + announcement.description + "</p>");
                        out.println("</div>");
                    }
                } else {
                    out.println("<p>There are no announcements</p>");
                }
            %>
        </div>
    </div>
    <div class="w-33">
        <div class="d-flex justify-content-between">
            <h3 class="text-white mb-2">All Quizes</h3>
            <a href="createQuiz.jsp" class="btn btn-primary mb-2">Create New Quiz</a>
        </div>

        <div class="quiz-container bg-white rounded">
            <ul class="p-3">
                <li class="ms-3"><a>Quiz 1</a></li>
                <li class="ms-3"><a>Quiz 2</a></li>
                <li class="ms-3"><a>Quiz 3</a></li>

                <%
                    // Retrieve the quizzes
                    ArrayList<String> quizzes = null;
                    try {
                        quizzes = User.getPopularQuizzes();
                    } catch (RuntimeException e) {
                        out.println("<div>Error retrieving quizzes: " + e.getMessage() + "</div>");
                    }
                %>
                <ul class="list-group p-3">
                    <%
                        if (quizzes != null && !quizzes.isEmpty()) {
                            for (String quiz : quizzes) {
                                out.println("<li class='ms-3'><a href='#' onclick=\"toggleMessageBox('" + quiz + "', this)\">" + quiz + "</a></li>");
                            }
                        } else {
                            out.println("<li class='ms-3'>No friends found.</li>");
                        }
                    %>
                </ul>
            </ul>
        </div>
    </div>
    <div class="friend-list-container w-33">
        <h2 class="text-white">Friend List</h2>
        <div class="friend-list rounded bg-white">
            <%
                // Retrieve the friends
                ArrayList<String> friendList = null;
                try {
                    friendList = User.getFriends(loggedInUser);
                } catch (RuntimeException e) {
                    out.println("<div>Error retrieving friends: " + e.getMessage() + "</div>");
                }
            %>
            <ul class="list-group p-3">
                <%
                    if (friendList != null && !friendList.isEmpty()) {
                        for (String fr : friendList) {
                            out.println("<li class='ms-3'><a href='#' onclick=\"toggleMessageBox('" + fr + "', this)\">" + fr + "</a></li>");
                        }
                    } else {
                        out.println("<li class='ms-3'>No friends found.</li>");
                    }
                %>
            </ul>
        </div>
    </div>
</div>

<div id="chat-container" style="display: none;">
    <div id="friend-name"><%=username%>
    </div>
    <div id="chat-window"></div>

    <input type="text" id="message-input" placeholder="Type a message...">
    <input type="hidden" id="user-name" class="logo" value="<%=loggedInUser%>">
    <button id="send-button" class="btn btn-primary" onclick="sendMessage()">Send</button>

</div>

<form action="QuizSummaryServlet" method="post">
    <input type="hidden" name="quiz_id" value="1">
    <input type="hidden" name="username" value="kato">
    <input type="submit" value="TAKE QUIZ">
</form>

<script>
    function viewAchievements() {
        window.location.href = 'achievements.jsp?username=<%=username%>';
    }
    function goToAdminPage() {
        window.location.href = 'admin_home_page.jsp?username=<%=username%>';
    }
</script>
</body>
</html>
