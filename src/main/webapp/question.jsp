<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Set" %>
<%@ page import="main.Manager.History" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="main.Manager.User" %><%--
  Created by IntelliJ IDEA.
  User: Kato
  Date: 07-Jul-24
  Time: 5:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Quiz</title>
</head>
<body>
<%
    Integer timeLeft = (Integer) request.getSession().getAttribute("timeLeft");
    int duration = ((int) request.getSession().getAttribute("duration"));
    int currentQuestionIndex = (int) request.getSession().getAttribute("currentQuestionIndex");
    List<Map<String,Object>> questions = (List<Map<String,Object>>) request.getSession().getAttribute("questions");
    if (currentQuestionIndex >= questions.size() || timeLeft <= 0) {
        try {
            String username = (String)request.getSession().getAttribute("username");
            String quiz_id = (String)request.getSession().getAttribute("quiz_id");
            request.getSession().setAttribute("duration", duration);
            History h = new History(Integer.parseInt(quiz_id),username, (int)request.getSession().getAttribute("score"), ((int) request.getSession().getAttribute("duration")) - ((int) request.getSession().getAttribute("timeLeft")));
            int ID = Integer.parseInt(quiz_id);
            int score = (int)request.getSession().getAttribute("score");
            int prev = User.highestScore(ID);
            History h = new History(ID ,username, score);
            if(score > prev){
                User.addAchievement(username, "I am the Greatest");
            }
            int takes = User.takenQuizzesAmount(username, ID);
            if(takes == 10){
                User.addAchievement(username, "Quiz Machine");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
%>
    <input type="hidden" name="score" value="<%=request.getSession().getAttribute("score")%>"
<%
        response.sendRedirect("finished-quiz.jsp");
    } else {
%>
<div id="timer">Time left: <span id="time"></span></div>
<form action="submitAnswers" method="get" id="quizForm">
    <%
        List<Map<String,Object>> multipleChoice = (List<Map<String,Object>>) request.getSession().getAttribute("multipleChoiceQuestions");
        List<Map<String,Object>> fillInTheBlankQuestions = (List<Map<String,Object>>) request.getSession().getAttribute("fillInTheBlankQuestions");
        List<Map<String,Object>> responseQuestions = (List<Map<String,Object>>) request.getSession().getAttribute("responseQuestions");
        List<Map<String,Object>> pictureResponseQuestions = (List<Map<String,Object>>) request.getSession().getAttribute("pictureResponseQuestions");
        Map<String,Object> settings = (Map<String,Object>) request.getSession().getAttribute("settings");
        int immediate_correction = (int)settings.get("immediate_correction");
        Map<String, Object> q = questions.get(currentQuestionIndex);
        int id = (int) q.get("question_id");
        request.getSession().setAttribute("question_id", id);
        int index = (int) q.get("index");

        //Multiple Choice Questions
        if ("questionMultipleChoice".equals(q.get("question_type"))) {
            Map<String, Object> question = multipleChoice.get(index);
            Set<String> choices = (Set<String>)question.get("multipleChoices");
            request.getSession().setAttribute("correct_answers"+id, question.get("correct_answers"));
%>
    <label>
        <p><%= question.get("question")%></p>
        <% for (String choice : choices) {
        %>
        <input type="radio"  name="submitted<%= id %>" value="<%= choice %>"> <%= choice %>
        <%
        }%>
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
<textarea name="submitted<%= id %>"></textarea><br />

<!-- Picture Questions -->
<%
    } else if ("questionPictureResponse".equals(q.get("question_type"))) {
        Map<String, Object> question = pictureResponseQuestions.get(index);
        String imageUrl = (String) question.get("picture_link");
        request.getSession().setAttribute("correct_answers"+id, question.get("correct_answers"));
%>
<img src="<%=imageUrl%>" alt=<%= question.get("question") %>><br />
<textarea name="submitted<%= id %>"></textarea><br />
<% } %>
    <input type="submit" value="Submit">
</form>
<div id="feedback"></div>
<script>
    var timeLeft = <%= timeLeft %>;
    var timerId = setInterval(countdown, 1000);

    function countdown() {
        if (timeLeft < 0) {
            clearInterval(timerId);
            document.getElementById('timeUp').value = 'true';
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
    document.getElementById('quizForm').addEventListener('submit', function(e) {
        var timeLeftField = document.createElement('input');
        timeLeftField.type = 'hidden';
        timeLeftField.name = 'timeLeft';
        timeLeftField.value = timeLeft;
        this.appendChild(timeLeftField);

        var timeUpField = document.createElement('input');
        timeUpField.type = 'hidden';
        timeUpField.name = 'timeUp';
        timeUpField.value = (timeLeft <= 0).toString();
        this.appendChild(timeUpField);

        var immediateCorrection = <%= immediate_correction %>;

        if (immediateCorrection === 1) {
            e.preventDefault();

            var formData = new FormData(this);
            var questionId = <%= id %>; // Get the current question ID from JSP
            var submittedAnswer = formData.get('submitted' + questionId);
            var correctAnswers = <%= new com.google.gson.Gson().toJson(request.getSession().getAttribute("correct_answers"+id)) %>;

            var feedbackElement = document.getElementById('feedback');

            if (correctAnswers.includes(submittedAnswer)) {
                feedbackElement.innerHTML = "Correct!";
                feedbackElement.style.color = "green";
            } else {
                feedbackElement.innerHTML = "Incorrect. The correct answer(s) are: " + correctAnswers.join(", ");
                feedbackElement.style.color = "red";
            }

            setTimeout(() => {
                this.submit();
            }, 2000);
        }
    });
</script>
<%
    }
%>
</body>
</html>
