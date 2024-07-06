<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.HashSet" %><%--
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
</head>
<body>
<%
    List<Map<String,Object>> questions = (List<Map<String,Object>>) request.getSession().getAttribute("questions");
    List<Map<String,Object>> multipleChoice = (List<Map<String,Object>>) request.getSession().getAttribute("multipleChoiceQuestions");
    List<Map<String,Object>> fillInTheBlankQuestions = (List<Map<String,Object>>) request.getSession().getAttribute("fillInTheBlankQuestions");
    List<Map<String,Object>> responseQuestions = (List<Map<String,Object>>) request.getSession().getAttribute("responseQuestions");
    List<Map<String,Object>> pictureResponseQuestions = (List<Map<String,Object>>) request.getSession().getAttribute("pictureResponseQuestions");
%>
<form action="submitAnswers" method="post">
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
            Set<String> choices = (HashSet)question.get("multipleChoices");
            request.getSession().setAttribute("correct_option"+id, question.get("correct_option"));
        %>
        <label>
            <p><%= question.get("question")%></p>
            <% for (String choice : choices) {%>
                <input type="radio"  name="submited<%= id %>"> <%= choice %>
            <%}%>
        </label><br />

        <!-- Fill In The Blank Questions -->
        <%
            } else if ("questionFillInTheBlank".equals(q.get("question_type"))) {
            Map<String, Object> question = fillInTheBlankQuestions.get(index);
            String questionTemp = (String) question.get("question");
            String[] parts = questionTemp.split("_");
            out.print(parts[0]);
            out.print("<input type='text' name='submited" + id +"'/>");
            out.print(parts[1]);
            request.getSession().setAttribute("correct_option"+id, question.get("correct_option"));
        %>

        <!-- Response Questions -->
        <%
            } else if ("questionResponse".equals(q.get("question_type"))) {
            Map<String, Object> question = responseQuestions.get(index);
        %>
        <p><%= question.get("question") %></p>
        <textarea name="submited<%= id %>"></textarea><br />

        <!-- Picture Questions -->
        <%
            } else if ("questionPictureResponse".equals(q.get("question_type"))) {
           Map<String, Object> question = pictureResponseQuestions.get(index);
           String imageUrl = (String) question.get("picture_link");
        %>
        <img src="/images/<%=imageUrl%>" alt=<%= question.get("question") %>><br />
        <textarea name="submited<%= id %>"></textarea><br />
        <% } %>
    </c:choose>
</div>
<br />
<% } %>
    <input type="submit" value="Submit">
</form>
</body>
</html>
