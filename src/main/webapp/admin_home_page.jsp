<%--
  Created by IntelliJ IDEA.
  main.Manager.User: alex
  Date: 6/7/24
  Time: 5:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<%
    String username = request.getParameter("username");
%>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script type="text/javascript">
    $(document).ready(function (){
        $('#promote').click(function (e){
            console.log("Promote button clicked");
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
            console.log("AJAX request for promoting user sent");
        });
        $('#remove').click(function (e){
            console.log("Remove button clicked");
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
            console.log("AJAX request for removing user sent");
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
    });
</script>
<head>
    <title>Admin Page</title>
    <link rel="stylesheet" type="text/css" href="css/adminPage.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
<div class="header">
    <h1>Welcome <%=username%> (admin)</h1>
    <form action="homePage.jsp" method="get">
        <input type="hidden" name="username" value=<%=username%>>
        <button type="submit">Back to home page</button>
    </form>
</div>
<div class="container">
    <div class="left">
        <div class="actions">
            <div class="action">
                <h2>Create Announcement</h2>
                <form>
                    <input id="announcementTitle" type="text" placeholder="Announcement Title" required />
                    <textarea id="announcementText" placeholder="Announcement Text" required></textarea>
                    <button type="submit" id="createAnnouncementButton">Create Announcement</button>
                </form>
            </div>

            <div class="action">
                <h2>Manage Users</h2>
                <form id="removeUserForm">
                    <input id="userid" name="user_id" type="text" value=""/>
                    <button type="submit" id="remove">Remove User</button>
                    <button type="submit" id ="promote">Promote User to Admin</button>
                    <span id="message"></span>
                </form>
            </div>
            <div class="action">
                <h2>Manage Quizzes</h2>
                <input name="quiz_id" type="text" value=""/>
                <button type="submit">Remove Quiz</button>
                <button type="submit">Remove Quiz History</button>
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
            chart.data.labels = data.map((_, index) => `Label ${index + 1}`);
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
