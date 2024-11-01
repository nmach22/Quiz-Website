<%--
  Created by IntelliJ IDEA.
  main.Manager.User: alex
  Date: 6/7/24
  Time: 5:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
<%--<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">--%>
<%--<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>--%>
<%--<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>--%>
<link rel="stylesheet" type="text/css" href="css/adminPage.css">
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<%--<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>--%>
<html>
<%
    String username = request.getParameter("username");
%>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script type="text/javascript">
    $(document).ready(function (){
        $('#promote').click(function (e){
            e.preventDefault();
            var username = $('#userid').val();
            $.ajax({
                type: 'POST',
                url: 'promoteServlet',
                data: {name: username},
                success: function (result){
                    $('#message').html(result);
                },
                error: function (xhr, status, error) {
                    console.error("AJAX error: " + status + " - " + error);
                }
            });
        });
        $('#remove').click(function (e){
            e.preventDefault();
            var username = $('#userid').val();
            $.ajax({
                type: 'POST',
                url: 'removeServlet',
                data: {name: username},
                success: function (result){
                    $('#message').html(result);
                },
                error: function (xhr, status, error) {
                    console.error("AJAX error: " + status + " - " + error);
                }
            });
        });
        $('#createAnnouncementButton').click(function (e){
            e.preventDefault();
            var title = $('#announcementTitle').val();
            var description = $('#announcementText').val();

            if (title.trim() === "" || description.trim() === "") {
                alert("Title and description must not be empty.");
                return;
            }

            $.ajax({
                type: 'POST',
                url: 'createAnnouncement',
                data: {
                    title: title,
                    description: description,
                    username: '<%=username%>'
                },
                success: function (result){
                    alert(result);
                },
                error: function (xhr, status, error) {
                    console.error("AJAX error: " + status + " - " + error);
                }
            });
        });
        $('#removeQuiz').click(function (e){
            e.preventDefault();
            var quizID = $('#quiz_id').val();
            console.log(quizID);
            $.ajax({
                type: 'POST',
                url: 'removeQuizServlet',
                data: {
                    quizID: quizID,
                },
                success: function (result){
                    $('#message2').html(result);
                },
                error: function (xhr, status, error) {
                    console.error("AJAX error: " + status + " - " + error);
                }
            });
        });
        $('#removeQuizHistory').click(function (e){
            e.preventDefault();
            var quizID = $('#quiz_id').val();

            $.ajax({
                type: 'POST',
                url: 'removeQuizHistory',
                data: {
                    quizID: quizID,
                },
                success: function (result){
                    $('#message2').html(result);
                },
                error: function (xhr, status, error) {
                    console.error("AJAX error: " + status + " - " + error);
                }
            });
        });
    });
</script>
<head>
    <title>Admin Page</title><link rel="stylesheet" type="text/css" href="css/global.css"><link rel="stylesheet" type="text/css" href="css/global.css">
    <link rel="stylesheet" type="text/css" href="css/global.css">

</head>
<body>
<%@include file="header.jsp"%>

<div class="container">
    <div class="left">
        <div class="actions">
            <div class="action">
                <h2>Create Announcement</h2>
                <form>
                    <div class="wrapper">
                        <input id="announcementTitle" type="text" placeholder="Announcement Title" required />
                        <textarea class="textarea-container" id="announcementText" placeholder="Announcement Text" required></textarea>
                    </div>
                    <button class="btn btn-primary mt-3" type="submit" id="createAnnouncementButton">Create Announcement</button>
                </form>
            </div>

            <div class="action">
                <h2>Manage Users</h2>
                <form id="removeUserForm">
                    <input id="userid" name="user_id" type="text" value=""/>
                    <div class="mt-3">
                        <button  class="btn btn-primary" type="submit" id="remove">Remove User</button>
                        <button  class="btn btn-primary" type="submit" id ="promote">Promote User to Admin</button>
                    </div>
                    <span id="message"></span>
                </form>
            </div>
            <div class="action">
                <h2>Manage Quizzes</h2>
                <input id="quiz_id" name="quiz_id" type="text" value=""/>
                <div class="mt-3">
                    <button class="btn btn-primary" type="submit" id="removeQuiz">Remove Quiz</button>
                    <button class="btn btn-primary" type="submit" id="removeQuizHistory">Remove Quiz History</button>
                </div>
                <span id="message2"></span>
            </div>
        </div>
    </div>
    <div class="right">
        <h2>Site Statistics</h2>
        <select id="timeRange">
            <option value="day">Today</option>
            <option value="week">This Week</option>
            <option value="month">This Month</option>
            <option value="year">This Year</option>
            <option value="all_time">All Time</option>
        </select>
        <canvas id="userChart" width="400" height="200"></canvas>
        <canvas id="quizChart" width="400" height="200"></canvas>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function() {
        let userChart = null;
        let quizChart = null;

        function fetchStatistics(timeRange) {
            $.ajax({
                type: 'GET',
                url: 'fetchStatisticsServlet',
                data: { timeRange: timeRange },
                success: function (data) {
                    data = JSON.parse(data);
                    const userCounts = data.map(item => item.users);
                    const quizCounts = data.map(item => item.quizzes);
                    updateChart(userChart, 'User Statistics', userCounts);
                    updateChart(quizChart, 'Quiz Statistics', quizCounts);
                },
                error: function (xhr, status, error) {
                    console.error("AJAX error: " + status + " - " + error);
                }
            });
        }

        function createChart(chartId, title, data) {
            const ctx = document.getElementById(chartId).getContext('2d');
            return new Chart(ctx, {
                type: 'bar',
                data: {
                    datasets: [{
                        label: title,
                        data: data,
                        backgroundColor: 'rgba(54, 162, 235, 0.2)',
                        borderColor: 'rgba(54, 162, 235, 1)',
                        borderWidth: 1
                    }]
                },
                options: {
                    scales: {
                        y: {
                            beginAtZero: true
                        }
                    },
                    animation: {
                        duration: 1000,
                        easing: 'easeOutBounce'
                    }
                }
            });
        }

        function updateChart(chart, title, data) {
            chart.data.labels = data.map((_, index) => ``);
            chart.data.datasets[0].label = title;
            chart.data.datasets[0].data = data;
            chart.update();
        }

        $('#timeRange').change(function() {
            fetchStatistics($(this).val());
        });

        userChart = createChart('userChart', 'User Statistics', []);
        quizChart = createChart('quizChart', 'Quiz Statistics', []);
        fetchStatistics('day');
    });
</script>
</body>
</html>
