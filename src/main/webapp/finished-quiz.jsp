<%@ page import="main.Manager.User" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: Kato
  Date: 07-Jul-24
  Time: 3:03 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Score</title>
    <link rel="stylesheet" type="text/css" href="css/finishQuiz.css">
    <link rel="stylesheet" type="text/css" href="css/global.css">
</head>
<body>
<%@include file="header.jsp" %>
<div class="finished-info-container rounded">
    <p>Your score is <%= request.getParameter("score") %>
    </p>
    <%
    int timeTaken = (int) request.getSession().getAttribute("duration") - (int) request.getSession().getAttribute("timeLeft");
    int minutes = timeTaken / 60;
    int remainingSeconds = timeTaken % 60;
    String result = String.format("%02d:%02d", minutes, remainingSeconds);
    %>
    <p>It took
        you <%= result %>
    </p>
    <form action="TakeQuizServlet" method="post">
        <input type="hidden" name="quiz_id" id="quiz_id" value="<%=request.getParameter("quiz_id")%>">
        <input type="hidden" name="username" id="username" value="<%=request.getParameter("username")%>">
        <button class="btn btn-secondary challenge-quiz-btn" type="submit">Retake Quiz</button>
    </form>
    <button class="btn btn-primary challenge-quiz-btn" id="challengeFriendsBtn">Challenge Friends</button>
    <div id="challengeModal" class="modal">
        <div class="modal-content">
            <span class="close">&times;</span>
            <h2>Challenge Friends</h2>
            <div id="friendsList">
                <%
                    ArrayList<String> friends = User.getFriends(loggedInUser);
                    for (String friend : friends) {
                %>
                <div class="friend-item">
                    <span><%= friend %></span>
                    <button onclick="challengeFriend('<%= friend %>')">Challenge</button>
                </div>
                <%
                    }
                %>
            </div>
        </div>
    </div>
</div>
<script>
    $(document).ready(function () {

        $('#challengeFriendsBtn').click(function () {
            $('#challengeModal').show();
        });


        $('.close').click(function () {
            $('#challengeModal').hide();
        });


        $(window).click(function (event) {
            if ($(event.target).is('#challengeModal')) {
                $('#challengeModal').hide();
            }
        });
    });

    function challengeFriend(friendName) {
        console.log("Challenging friend:", friendName); // Debugging statement
        $.ajax({
            url: 'ChallengeFriendServlet',
            method: 'POST',
            data: {
                friendName: friendName,
                quizId: document.getElementById("quiz_id").value,
                username: document.getElementById("username").value
            },
            success: function (response) {
                console.log('Challenge sent to ' + friendName); // Debugging statement
                alert('Challenge sent to ' + friendName);
            },
            error: function (xhr, status, error) {
                console.error('Error sending challenge:', error); // Debugging statement
            }
        });
    }
</script>

</body>
</html>