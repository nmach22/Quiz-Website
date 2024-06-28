<%--
  Created by IntelliJ IDEA.
  main.Manager.User: nika
  Date: 6/7/24
  Time: 5:14 PM
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
            console.log("AJAX request for removing user sent");
        });
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
<%--    <link rel="stylesheet" type="text/css" href="css/main_background.css">--%>
</head>
<body>
<div class="header">
    <h1>Welcome <%=username%> (admin)</h1>
    <form action="site_statistics.jsp" method="get">
        <input type="hidden" name="username" value=<%=username%>>
        <button type="submit">Site statistics</button>
    </form>
    <form action="homePage.jsp" method="get">
        <input type="hidden" name="username" value=<%=username%>>
        <button type="submit">Back to home page</button>
    </form>
</div>
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
            <input id = "userid" name="user_id" type="text" value=""/>
            <button type="submit" id="remove">Remove User</button>
            <button type="submit" id ="promote">Promote User to Admin</button>
            <span id = "message"></span>
        </form>

    </div>
    <div class="action">
        <h2>Manage Quizzes</h2>
        <input name="quiz_id" type="text" value=""/>
        <button type="submit">Remove Quiz</button>
        <button type="submit">Remove Quiz History</button>
    </div>
</div>
</body>
</html>

