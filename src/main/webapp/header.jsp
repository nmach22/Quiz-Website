<%@ page import="main.Manager.AccountManager" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="main.Manager.User" %>
<!DOCTYPE html>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="js/header.js"></script>
    <link rel="stylesheet" type="text/css" href="css/header.css">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</head>
<body>
<header class="header">
    <% String loggedInUser = (String) session.getAttribute("username"); %>

    <div class="leftContainer">
        <div class="logo">
            <a href="homePage.jsp?username=<%=loggedInUser%>">
            </a>
        </div>
        <div class="searchBar">
            <input type="text" id="searchInput" placeholder="search" size="50" name="searchItem" required>
            <button type="submit" class="searchButton" onclick="search()">Search</button>
            <div id="searchDropdown" class="search-dropdown"></div>
        </div>
    </div>
    <div class="rightContainer">
        <div class="navigation">
            <%
                if (AccountManager.isAdmin(loggedInUser)) {
                    out.println("<a href='#' onclick = \"goToAdminPage()\" title=\"admin page\">");
                    out.println("<i class=\"fas fa-shield-alt\"></i>");
                    out.println("</a>");
                }
            %>
            <div class="achievements-notification">
                <a href='#' onclick = viewAchievements() title= "achievements page">
                    <i class="fas fa-trophy"></i>
                    <span class="badge" id="unread_achievements"></span>
                    <span class="tooltip">Achievements</span>
                </a>
            </div>
            <div class="inbox-notification">
                <a href="#" title="inbox" id="inbox-button">
                    <i class="fas fa-inbox"></i>
                    <span class="badge" id="unread-count"></span>
                    <span class="tooltip">Inbox</span>
                </a>
                <div id="notificationsBox" class="notifications-box">
                    <div class="notifications-section" id="friendRequests">
                        <h4>Friend Requests</h4>
                        <ul></ul>
                    </div>
                    <div class="notifications-section" id="quizChallenges">
                        <h4>Quiz Challenges</h4>
                        <ul></ul>
                    </div>
                </div>
            </div>
            <a href="settings.jsp?username=<%=loggedInUser%>" title="settings">
                <i class="fas fa-cog"></i>
            </a>
            <a href="profilePage.jsp?username=<%=loggedInUser%>" title="profile">
                <i class="fas fa-user"></i>
            </a>
            <a href="index.jsp" title="logout">
                <i class="fa fa-sign-out"></i>
            </a>
            <input type="hidden" id="username" value="<%= loggedInUser%>">
        </div>
    </div>
</header>
<script>
    function viewAchievements() {
        $.ajax({
            url: 'updateDbForAchievements?username=' + encodeURIComponent('<%=loggedInUser%>'),
            method: 'GET',
            success: function () {
                // Call the function to refresh the achievements count in the header
                window.location.href = 'achievements.jsp?username=' + encodeURIComponent('<%=loggedInUser%>');
            },
            error: function (xhr, status, error) {
                console.error('Error:', error);
            }
        });
    }

    function goToAdminPage() {
        window.location.href = 'admin_home_page.jsp?username=<%=loggedInUser%>';
    }

    function search() {
        const query = document.getElementById('searchInput').value.trim(); // Trim to remove any leading/trailing whitespace
        if (query.length > 0) {
            $.ajax({
                url: 'searchResultServlet',
                method: 'GET',
                data: { searchItem: query },
                success: function (response) {
                    const dropdown = document.getElementById('searchDropdown');
                    dropdown.innerHTML = '';
                    dropdown.style.display = 'block';
                    if (response.profiles && response.profiles.length > 0) {
                        const item = document.createElement('div');
                        item.className = 'search-dropdown-item';
                        item.innerHTML = '<a href="profilePage.jsp?username=' + response.profiles + '">' + response.profiles + '</a>';
                        dropdown.appendChild(item);
                    }
                    if (response.quizzes && response.quizzes.length > 0) {
                        response.quizzes.forEach(function(quiz) {
                            const item = document.createElement('div');
                            item.className = 'search-dropdown-item';
                            item.innerHTML = '<a href="quizPage.jsp?quizId=' + quiz.quizId + '">' + quiz.title + '</a>';
                            dropdown.appendChild(item);
                        });
                    }
                    if (!response.profiles.length && !response.quizzes.length) {
                        dropdown.innerHTML = '<div class="search-dropdown-item">No results found</div>';
                    }
                },
                error: function (xhr, status, error) {
                    console.error('Error fetching search results:', error); // Log any errors to the console
                }
            });
        } else {
            document.getElementById('searchDropdown').style.display = 'none';
        }
    }

    $(document).ready(function() {
        $('#inbox-button').click(function(event) {
            event.preventDefault();
            const notificationsBox = $('#notificationsBox');
            notificationsBox.toggle();

            if (notificationsBox.is(':visible')) {
                $.ajax({
                    url: 'notificationsServlet',
                    method: 'GET',
                    success: function(response) {
                        console.log('Notifications response:', response);

                        const friendRequests = $('#friendRequests ul');
                        const quizChallenges = $('#quizChallenges ul');

                        friendRequests.html('');
                        quizChallenges.html('');

                        response.friendRequests.forEach(function(request) {
                            const listItem = document.createElement('li');
                            const anchor = document.createElement('a');
                            anchor.href = `profilePage.jsp?username=${request.friend}`;
                            anchor.textContent = request.friend;

                            const acceptButton = document.createElement('button');
                            acceptButton.textContent = 'Accept';
                            acceptButton.onclick = function() {
                                handleFriendRequest('accept', request.friend);
                                listItem.remove();
                            };

                            const rejectButton = document.createElement('button');
                            rejectButton.textContent = 'Reject';
                            rejectButton.onclick = function() {
                                handleFriendRequest('reject', request.friend);
                                listItem.remove();
                            };

                            listItem.appendChild(anchor);
                            listItem.appendChild(acceptButton);
                            listItem.appendChild(rejectButton);

                            friendRequests.append(listItem);
                        });

                        response.challenges.forEach(function(challenge) {
                            const listItem = document.createElement('li');
                            const anchor = document.createElement('a');
                            anchor.textContent = "challenged by: " + challenge.from + " score to beat: " + challenge.score;

                            const acceptButton = document.createElement('button');
                            acceptButton.textContent = 'Accept';
                            acceptButton.onclick = function() {
                                handleChallengeRequest('accept', challenge.challengeID, challenge.quizId);
                                listItem.remove();
                            };

                            const rejectButton = document.createElement('button');
                            rejectButton.textContent = 'Reject';
                            rejectButton.onclick = function() {
                                handleChallengeRequest('reject', challenge.challengeID, challenge.quizId);
                                listItem.remove();
                            };
                            listItem.appendChild(anchor);
                            listItem.appendChild(acceptButton);
                            listItem.appendChild(rejectButton);

                            quizChallenges.append(listItem);
                            });
                    },
                    error: function(xhr, status, error) {
                        console.error('Error fetching notifications:', error);
                    }
                });
            }
        });

        $(document).click(function(event) {
            if (!$(event.target).closest('#inbox-button, #notificationsBox').length) {
                $('#notificationsBox').hide();
            }
        });
    });

    function handleFriendRequest(acOrRej, friend) {
        $.ajax({
            url: 'friendRequestHandlerServlet',
            method: 'POST',
            data: {
                action: acOrRej,
                friend: friend
            },
            success: function() {
                alert(acOrRej);
            },
            error: function(xhr, status, error) {
                console.error('Error handling friend request:', error);
            }
        });
    }

    function handleChallengeRequest(acOrRej, ID, quizID) {
        $.ajax({
            url: 'challengeRequestHandlerServlet',
            method: 'POST',
            data: {
                action: acOrRej,
                ID: ID
            },
            success: function(response) {
                if (acOrRej === 'accept') {
                    window.location.href = `quizPage.jsp?quizId= ` + quizID;
                }
            },
            error: function(xhr, status, error) {
                console.error('Error handling friend request:', error);
            }
        });
    }

</script>
</body>

</html>
