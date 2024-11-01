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
    <link rel="stylesheet" type="text/css" href="css/global.css">
    <link rel="stylesheet" type="text/css" href="css/friend-list.css">
    <link rel="stylesheet" type="text/css" href="css/chat.css">
    <link rel="stylesheet" type="text/css" href="css/home-page.css">
    <link rel="stylesheet" type="text/css" href="css/global.css">

    <script src="js/MessageScript.js"></script>
    <script src="js/QuizScript.js" defer></script>
    <script src="js/QuizCreated.js" defer></script>
</head>
<body>

<%@include file="header.jsp" %>

<div class="d-flex justify-content-evenly gap-4 w-100 px-5">
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
                        out.println("<div class='announcement rounded text-white'>");
                        out.println("<h4 class='text-white'>" + announcement.title + "</h2>");
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
        <%
            if(loggedInUser != null){
        %>
        <div class="d-flex justify-content-between">
            <h2 class="text-white mb-2">All Quizes</h2>
            <a href="createQuiz.jsp" class="btn btn-primary mb-2">Create New Quiz</a>
        </div>
        <%
            }
        %>

        <div class="quiz-container rounded">
            <div class="p-3">
                <select class="form-select mb-3" id="quizSelector" aria-label="Default select example">
                    <option value="1">Show the most popular quizzes</option>
                    <option value="2">Show recently added quizzes</option>
                </select>

                <ul id="quizList" class="quiz-list-contianer link-container mb-0">
                    <!-- Quiz items will be populated here -->
                </ul>

                <form id="quizSummaryForm" action="QuizSummaryServlet" method="post">
                    <input type="hidden" id="quizIdInput" name="quiz_id" value="">
                    <input type="hidden" id="usernameInput" name="username" value="<%= username %>">
                    <input type="submit" style="display:none;">
                </form>
            </div>
        </div>
    </div>
    <%
        if(loggedInUser != null){
    %>
    <div class="friend-list-container w-33">
        <h2 class="text-white">Friend List</h2>
        <div class="friend-list rounded">
            <%
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
                            out.println("<li class='link-container'><a href='#' onclick=\"toggleMessageBox('" + fr + "', this)\">" + fr + "</a></li>");
                        }
                    } else {
                        out.println("<li class=''>No friends found.</li>");
                    }
                %>
            </ul>
        </div>
    </div>
    <%
        }
    %>
</div>

<div class="rounded"  id="chat-container" style="display: none;">
    <div class="textarea-container"  id="friend-name"><%=username%>
    </div>
    <div class="quiz-container rounded" id="chat-window"></div>

    <input type="text" id="message-input" placeholder="Type a message...">
    <input type="hidden" id="user-name" class="logo" value="<%=loggedInUser%>">
    <button id="send-button" class="btn btn-primary ms-1" onclick="sendMessage()">Send</button>

</div>

</body>
</html>
