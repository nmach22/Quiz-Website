<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.3/jquery.min.js"></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.2/jquery-ui.min.js"></script>
<script type="text/javascript" src="static/js/header.js"></script>
<script type="text/javascript" src="static/js/tooltips.js"></script>
<script type="text/javascript" src="static/js/jquery.tipTip.js"></script>

<link type="text/css" href="static/css/all.css" rel="stylesheet"/>
<link type="text/css" href="static/css/header.css" rel="stylesheet"/>
<link type="text/css" href="static/css/buttons.css" rel="stylesheet"/>
<link type="text/css" href="static/css/tooltips.css" rel="stylesheet"/>


<div id="header">
    <% String loggedInUser = (String) session.getAttribute("username"); %>

            <a href="homePage.jsp" id="user-name" class="logo"><%= loggedInUser%></a>
        </div>
        <!-- Search -->
        <div id="search">
            <form action="search.jsp" method="get">
                <span>
                        <input type="text" name="query" placeholder="Search" size="50" />
						<input type="submit" value="Search"> </input>
                </span>
            </form>

</div>