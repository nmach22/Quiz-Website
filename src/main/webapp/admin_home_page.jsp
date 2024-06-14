<%--
  Created by IntelliJ IDEA.
  main.Manager.User: nika
  Date: 6/7/24
  Time: 5:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String username = request.getParameter("username");
%>

<html>
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
    <title>Welcome <%=username%></title>
    <h1>Welcome <%=username%> (admin)</h1>

</head>
<body>

<div>
    <h2>Create Announcement</h2>
    <form>
        <input id="announcementTitle" type="text" placeholder="Announcement Title" required />
        <textarea id="announcementText" placeholder="Announcement Text" required></textarea>
        <button type="submit" id="createAnnouncementButton">Create Announcement</button>
    </form>
</div>

<div>
    <h2>Manage Users</h2>
    <form id="removeUserForm">
        <input id = "userid" name="user_id" type="text" value=""/>
        <button type="submit" id="remove">Remove User</button>
        <button type="submit" id ="promote">Promote User to Admin</button>
        <span id = "message"></span>
    </form>

</div>
<div>
    <input name="quiz_id" type="text" value=""/>
    <button type="submit">Remove Quiz</button>
    <button type="submit">Remove Quiz History</button>
</div>

<div>
    <a href="site_statistics.jsp">Site Statistics</a>
</div>
</body>
</html>

