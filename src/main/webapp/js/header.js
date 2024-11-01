document.addEventListener('DOMContentLoaded', function() {
    getUnreadAchievements();
    getUnreadMessages();
});

function getUnreadAchievements() {
    const xhr = new XMLHttpRequest();
    const name = document.getElementById('username').value; // Assuming you have an input with id="username"
    console.log('Fetching achievements for:', name);
    // Construct the URL with encodeURIComponent to safely encode the username
    xhr.open('GET', 'GetAchievementsCountServlet?username=' + encodeURIComponent(name), true);

    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4) { // Check if the request is complete
            if (xhr.status == 200) { // Check if the request was successful
                console.log('Achievements response:', xhr.responseText);
                document.getElementById('unread_achievements').textContent = xhr.responseText; // Update the element with the response
            } else {
                console.error('Error fetching data. Status:', xhr.status); // Log any errors
            }
        }
    };

    xhr.send(); // Send the request
}

function getUnreadMessages() {
    const xhr = new XMLHttpRequest();
    const name = document.getElementById('username').value; // Assuming you have an input with id="username"

    // Construct the URL with encodeURIComponent to safely encode the username
    xhr.open('GET', 'GetMessagesCountServlet?username=' + encodeURIComponent(name), true);

    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4) { // Check if the request is complete
            if (xhr.status == 200) { // Check if the request was successful

                document.getElementById('unread-count').textContent = xhr.responseText; // Update the element with the response
            } else {
                console.error('Error fetching data. Status:', xhr.status); // Log any errors
            }
        }
    };

    xhr.send(); // Send the request
}


