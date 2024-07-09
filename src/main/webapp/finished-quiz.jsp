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
</head>
<body>
<%@include file="header.jsp"%>
<p>Your score is <%= request.getSession().getAttribute("score") %></p>
<p>It took you <%= ((int) request.getSession().getAttribute("duration")) - ((int) request.getSession().getAttribute("timeLeft"))%></p>
<form action="TakeQuizServlet" method="post">
    <input type="hidden" name="quiz_id" id="quiz_id" value="<%=request.getSession().getAttribute("quiz_id")%>">
    <input type="hidden" name="username" id="username" value="<%=request.getSession().getAttribute("username")%>">
    <input type="submit" value="Retake Quiz">
</form>
    <button id="challengeFriendsBtn">Challenge Friends</button>
<div id="challengeModal" class="modal">
    <div class="modal-content">
        <span class="close">&times;</span>
        <h2>Challenge Friends</h2>
        <div id="friendsList">
            <%
                String username = (String) request.getSession().getAttribute("username");
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
<script>
    $(document).ready(function() {

        $('#challengeFriendsBtn').click(function() {
            $('#challengeModal').show();
        });


        $('.close').click(function() {
            $('#challengeModal').hide();
        });


        $(window).click(function(event) {
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
                username:  document.getElementById("username").value
            },
            success: function(response) {
                console.log('Challenge sent to ' + friendName); // Debugging statement
                alert('Challenge sent to ' + friendName);
            },
            error: function(xhr, status, error) {
                console.error('Error sending challenge:', error); // Debugging statement
            }
        });
    }
</script>

</body>
</html>