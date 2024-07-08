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
    <% String loggedInUser = (String) session.getAttribute("username");
        System.out.println(loggedInUser);
    %>
    <div class="leftContainer">
        <div class="logo">
            <a href="homePage.jsp?username=<%=loggedInUser%>">
                <img src="data:image/jpeg;base64,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">
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
                if(loggedInUser != null){

            %>
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
            <%
                } else {
            %>
                <label> If You Want To Take Quizzes And Partake In Other Various Activities, Please Log in:</label>
            <%
                }
            %>
            <a href="logoutServlet" title="logout">
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
                        item.innerHTML = '<a href="profilePage.jsp?username=' + response.profiles + '">'+ 'User: ' + response.profiles + '</a>';
                        dropdown.appendChild(item);
                    }
                    if (response.quizzes && response.quizzes.length > 0) {
                        response.quizzes.forEach(function(quiz) {
                            const item = document.createElement('div');
                            item.className = 'search-dropdown-item';
                            item.innerHTML = '<a href="QuizSummaryServlet?quiz_id=' + quiz.quizId + '">'+ 'Quiz: ' + quiz.title + '</a>';
                            dropdown.appendChild(item);
                        });
                    }
                    if (!response.profiles && !response.quizzes) {
                        const item = document.createElement('div');
                        item.className = 'search-dropdown-item';
                        item.innerHTML = '<label> No results found <label>';
                        dropdown.appendChild(item);
                    }
                },
                error: function (xhr, status, error) {
                    console.error('Error fetching search results:', error);
                }
            });
        } else {
            document.getElementById('searchDropdown').style.display = 'none';
        }

        $(document).click(function(event) {
            if (!$(event.target).closest(document.getElementById('searchDropdown')).length) {
                $(document.getElementById('searchDropdown')).hide();
            }
        });
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
                window.location.href = `homePage.jsp`;
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
                    window.location.href = `QuizSummaryServlet?quiz_id=` + quizID;
                }
                if (acOrRej === 'reject') {
                    window.location.href = `homePage.jsp`;
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
