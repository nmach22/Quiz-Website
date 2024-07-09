<%@ page import="java.util.*" %><%--
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
    <link rel="stylesheet" type="text/css" href="css/global.css">
</head>
<body>
<%
    String username = request.getParameter("username");
    String quiz_id = request.getParameter("quiz_id");
    List<Map<String,Object>> questions = (List<Map<String,Object>>) request.getSession().getAttribute("questions");
    List<Map<String,Object>> multipleChoice = (List<Map<String,Object>>) request.getSession().getAttribute("multipleChoiceQuestions");
    List<Map<String,Object>> fillInTheBlankQuestions = (List<Map<String,Object>>) request.getSession().getAttribute("fillInTheBlankQuestions");
    List<Map<String,Object>> responseQuestions = (List<Map<String,Object>>) request.getSession().getAttribute("responseQuestions");
    List<Map<String,Object>> pictureResponseQuestions = (List<Map<String,Object>>) request.getSession().getAttribute("pictureResponseQuestions");
    Map<String,Object> settings = (Map<String,Object>) request.getSession().getAttribute("settings");
    int timeLimit = ((int)settings.get("duration")) * 60; // Time in seconds
    if ((int)settings.get("is_random") == 1) {
        Collections.shuffle(questions);
    }
    if ((int)settings.get("one_page") == 1) {
%>
<form id="quizForm" action="submitAnswers" method="post">
    <div id="timer">Time left: <span id="time"><%=timeLimit%></span> seconds</div>
    <%
        for (Map<String, Object> q : questions) {
    %>
    <div class="question">
        <% int id = (int) q.get("question_id");
            int index = (int) q.get("index");
        %>
        <c:choose>

            <!-- Multiple Choice Questions -->
            <%
                if ("questionMultipleChoice".equals(q.get("question_type"))) {
                    Map<String, Object> question = multipleChoice.get(index);
                    Set<String> choices = (Set<String>)question.get("multipleChoices");
                    request.getSession().setAttribute("correct_answers"+id, question.get("correct_answers"));
            %>
            <label>
                <p><%= question.get("question")%></p>
                <% for (String choice : choices) {%>
                <input type="radio"  name="submitted<%= id %>" value="<%= choice %>"> <%= choice %>
                <%}%>
            </label><br />

            <!-- Fill In The Blank Questions -->
            <%
            } else if ("questionFillInTheBlank".equals(q.get("question_type"))) {
                Map<String, Object> question = fillInTheBlankQuestions.get(index);
                String questionTemp = (String) question.get("question");
                String[] parts = questionTemp.split("_");
                out.print(parts[0]);
                out.print("<input type='text' name='submitted" + id +"'>");
                out.print(parts[1]);
                request.getSession().setAttribute("correct_answers"+id, question.get("correct_answers"));
            %>

            <!-- Response Questions -->
            <%
            } else if ("questionResponse".equals(q.get("question_type"))) {
                Map<String, Object> question = responseQuestions.get(index);
                request.getSession().setAttribute("correct_answers"+id, question.get("correct_answers"));
            %>
            <p><%= question.get("question") %></p>
            <textarea class="textarea-container" name="submitted<%= id %>"></textarea><br />

            <!-- Picture Questions -->
            <%
            } else if ("questionPictureResponse".equals(q.get("question_type"))) {
                Map<String, Object> question = pictureResponseQuestions.get(index);
                String imageUrl = (String) question.get("picture_link");
                request.getSession().setAttribute("correct_answers"+id, question.get("correct_answers"));
            %>
            <img src="<%=imageUrl%>" alt='<%=question.get("question") %>'><br />
            <textarea class="textarea-container" name="submitted<%= id %>"></textarea><br />
            <% } %>
        </c:choose>
    </div>
    <br />
    <% } %>
    <input type="hidden" name="quiz_id" value= "<%=request.getParameter("quiz_id")%>">
    <input type="hidden" name="username" value=${username}>
    <input type="hidden" id="timeLeftInput" name="timeLeft">
    <input type="hidden" name="currentQuestionIndex" value="0">
    <%request.getSession().setAttribute("duration", timeLimit);%>
    <input type="submit" value="Submit">
</form>
<%
    } else {
        request.getSession().setAttribute("username", username);
        request.getSession().setAttribute("quiz_id", quiz_id);
        request.getSession().setAttribute("immediateScore", 0);
        request.getSession().setAttribute("timeLeft", timeLimit);
        request.getSession().setAttribute("duration", timeLimit);
        response.sendRedirect("question.jsp?quiz_id=" + quiz_id + "&username=" + username + "&currentQuestionIndex=" + 0+"&score="+0);
    }
%>
<script>
    var timeLeft = <%=timeLimit%>;
    var timerId = setInterval(countdown, 1000);

    function countdown() {
        if (timeLeft <= 0) {
            clearTimeout(timerId);
            document.getElementById('quizForm').submit();
        } else {
            var minutes = Math.floor(timeLeft / 60);
            var seconds = timeLeft % 60;
            var formattedTime =
                (minutes < 10 ? "0" : "") + minutes + ":" +
                (seconds < 10 ? "0" : "") + seconds;

            document.getElementById('time').innerHTML = formattedTime;
            timeLeft--;
        }
    }

    countdown();

    document.getElementById('quizForm').addEventListener('submit', function() {
        document.getElementById('timeLeftInput').value = timeLeft;
    });
</script>
</body>
</html>
