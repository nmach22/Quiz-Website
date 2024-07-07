<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %><%--
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
    int currentQuestionIndex = Integer.parseInt(request.getParameter("currentQuestionIndex"));
    List<Map<String,Object>> questions = (List<Map<String,Object>>) request.getSession().getAttribute("questions");
    if (currentQuestionIndex >= questions.size()) {
%>
    <input type="hidden" name="score" value="<%=request.getSession().getAttribute("score")%>"
<%
        response.sendRedirect("finished-quiz.jsp");
    } else {
        List<Map<String,Object>> multipleChoice = (List<Map<String,Object>>) request.getSession().getAttribute("multipleChoiceQuestions");
        List<Map<String,Object>> fillInTheBlankQuestions = (List<Map<String,Object>>) request.getSession().getAttribute("fillInTheBlankQuestions");
        List<Map<String,Object>> responseQuestions = (List<Map<String,Object>>) request.getSession().getAttribute("responseQuestions");
        List<Map<String,Object>> pictureResponseQuestions = (List<Map<String,Object>>) request.getSession().getAttribute("pictureResponseQuestions");
        Map<String,Object> settings = (Map<String,Object>) request.getSession().getAttribute("settings");
        if((int)settings.get("immediate_correction") == 1) {
            int immediateScore = Integer.parseInt(request.getParameter("immediateScore"));
        }

        Question currentQuestion = questions.get(currentQuestionIndex);
%>
<form action="submit_answer.jsp" method="post">
    <div>
        <p><%= currentQuestion.getText() %></p>
        <!-- Display question options here -->
    </div>
    <input type="submit" value="Next">
</form>
<%
    }
%>
</body>
</html>
