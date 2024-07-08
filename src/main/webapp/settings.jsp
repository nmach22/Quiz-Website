<%@ page import="main.Manager.AccountManager" %>
<%@ page import="java.sql.SQLException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Settings</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" type="text/css" href="css/settings.css">
    <script src="js/settings.js"></script>
</head>
<body>
<%@ include file="header.jsp"%>

<div class="settings-content">
    <h1 class="settings-title">Settings</h1>
    <h2 class="settings-subtitle">Manage your account</h2>
    <div class="settings-container">
        <h5>Username</h5>
        <p>
            <a class="username" href="profilePage.jsp?username=<%=loggedInUser%>"><%=loggedInUser %></a>
        </p>
        <section class="setting-bottom">
            <form id="profile-form" method="post" action="SaveChangesServlet">
                <div class="bio-container">
                    <label for="user-bio">Bio</label>
                    <textarea id="user-bio" name="user-bio" rows="5" class="save-item" maxlength="250" cols="50"></textarea>
                    <div class="info-text">
                        <div id="char-count">0 / 250 characters</div>
                    </div>
                </div>
                <div class="account-info-container">
                    <h1>Account Info</h1>
                    <h3>Update your information</h3>
                    <section class="information-container">
                        <div id="username-input">
                            <label for="user-name">Username</label>
                            <input type="text" id="user-name" name="user-name" class="save-item" maxlength="50" value="<%=loggedInUser%>">
                        </div>
                        <div id="password-input">
                            <label for="user-pas">Password</label>
                            <input type="password" id="user-pas" name="user-pas" class="save-item" maxlength="50" value="">
                        </div>
                        <div id="firstname-input">
                            <label for="firstname">First Name</label>
                            <input type="text" id="firstname" name="firstname" class="save-item" maxlength="50" value="">
                        </div>
                        <div id="lastname-input">
                            <label for="lastname">Last Name</label>
                            <input type="text" id="lastname" name="lastname" class="save-item" maxlength="50" value="">
                        </div>
                    </section>
                </div>
                <input type="hidden" id="username" name="username" value="<%=loggedInUser%>">
                <button type="submit" class="sc-button">Save Changes</button>
            </form>
        </section>
    </div>
</div>

</body>
</html>
