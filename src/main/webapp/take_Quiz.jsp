<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %><%--
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
    if(multipleChoice.isEmpty()) System.out.println("AKDNEFAIJDFBASHIDFVB");
    List<Map<String,Object>> fillInTheBlankQuestions = (List<Map<String,Object>>) request.getSession().getAttribute("fillInTheBlankQuestions");
    List<Map<String,Object>> responseQuestions = (List<Map<String,Object>>) request.getSession().getAttribute("responseQuestions");
    List<Map<String,Object>> pictureResponseQuestions = (List<Map<String,Object>>) request.getSession().getAttribute("pictureResponseQuestions");
%>
<% for (Map<String, Object> question : questions) { %>
<div class="question">
    <!-- Display Question Text -->
    <p><%= question.get("question") %></p>
    <c:choose>
        <!-- Multiple Choice Questions -->
        <% if ("questionMultipleChoice".equals(question.get("question_type"))) { %>
        <% for (Map.Entry<String, Object> entry : question.entrySet()) { %>
        <% String key = entry.getKey(); %>
        <% Object value = entry.getValue(); %>
        <% if (!"question_id".equals(key) && !"question_type".equals(key) && !"question".equals(key) && !"correct_option".equals(key)) { %>
        <label>
            <input type="radio" name="question_<%= question.get("question_id") %>" value="<%= value %>" />
            <%= value %>
        </label><br />
        <% } %>
        <% } %>
        <% } else if ("questionFillInTheBlank".equals(question.get("question_type"))) { %>
        <input type="text" name="question_<%= question.get("question_id") %>" /><br />
        <% } else if ("questionResponse".equals(question.get("question_type")) || "questionPictureResponse".equals(question.get("question_type"))) { %>
        <textarea name="question_<%= question.get("question_id") %>"></textarea><br />
        <% } %>
    </c:choose>
</div>
<br />
<% } %>
</body>
</html>
