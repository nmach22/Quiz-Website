<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Set" %><%--
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
    int currentQuestionIndex = (int) request.getSession().getAttribute("currentQuestionIndex");
    List<Map<String,Object>> questions = (List<Map<String,Object>>) request.getSession().getAttribute("questions");
    if (currentQuestionIndex >= questions.size()) {
%>
    <input type="hidden" name="score" value="<%=request.getSession().getAttribute("score")%>"
<%
        response.sendRedirect("finished-quiz.jsp");
    } else {
        %>
<form action="submitAnswers" method="get">
    <%
        List<Map<String,Object>> multipleChoice = (List<Map<String,Object>>) request.getSession().getAttribute("multipleChoiceQuestions");
        List<Map<String,Object>> fillInTheBlankQuestions = (List<Map<String,Object>>) request.getSession().getAttribute("fillInTheBlankQuestions");
        List<Map<String,Object>> responseQuestions = (List<Map<String,Object>>) request.getSession().getAttribute("responseQuestions");
        List<Map<String,Object>> pictureResponseQuestions = (List<Map<String,Object>>) request.getSession().getAttribute("pictureResponseQuestions");
        Map<String,Object> settings = (Map<String,Object>) request.getSession().getAttribute("settings");
//        if((int)settings.get("immediate_correction") == 1) {
//            int immediateScore = (int) request.getSession().getAttribute("immediateScore");
//        }
//AMAS MOUBRUNDI MERE!!!
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
<img src="/images/<%=imageUrl%>" alt=<%= question.get("question") %>><br />
<textarea name="submitted<%= id %>"></textarea><br />
<% } %>
    <input type="submit" value="Submit">
</form>
<%
    }
%>
</body>
</html>
