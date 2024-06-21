document.addEventListener('DOMContentLoaded', function() {
    const chatWindow = document.getElementById('chat-window');
    const messageInput = document.getElementById('message-input');
    const chatContainer = document.getElementById('chat-container');
    const friendName = document.getElementById('friend-name');
    let currentFriend = null;

    const socket = new WebSocket('ws://localhost:8080/chat');

    socket.onmessage = function(event) {
        const message = document.createElement('div');
        const data = JSON.parse(event.data);
        message.textContent = `${data.senderName}: ${data.message}`;
        chatWindow.appendChild(message);
    };

    window.toggleMessageBox = function(friend, element) {
        console.log("Clicked friend: " + friend);
        console.log("Current friend: " + currentFriend);

        if (currentFriend === friend) {
            chatContainer.style.display = 'none';
            currentFriend = null;
            console.log("Hiding chat container");
        } else {
            chatContainer.style.display = 'block';
            chatWindow.innerHTML = ''; // Clear chat window for new friend
            friendName.textContent = friend; // Set the friend's name
            currentFriend = friend;
            console.log("Showing chat container for friend: " + friend);

            // Load messages for this friend from database
            loadMessages(friend);
        }
    }

    window.sendMessage = function() {
        const message = messageInput.value;
        if (message.trim() === '') {
            return;
        }

        // Send message via WebSocket
        socket.send(message);

        // Save message to database via servlet
        saveMessageToServlet(currentFriend, message);

        // Display sent message in chat window
        displaySentMessage(message);

        messageInput.value = '';
    }

    function loadMessages(friend) {
        // Fetch messages from database via servlet
        fetch(`/FetchMessagesServlet?user_from=${currentFriend}`)
            .then(response => response.json())
            .then(messages => {
                messages.forEach(msg => displayMessage(msg.senderName, msg.message));
            })
            .catch(error => console.error('Error fetching messages:', error));
    }

    function displayMessage(senderName, message) {
        const messageDiv = document.createElement('div');
        messageDiv.classList.add('message');
        messageDiv.textContent = `${senderName}: ${message}`;
        chatWindow.appendChild(messageDiv);
    }

    function displaySentMessage(message) {
        const sentMessageDiv = document.createElement('div');
        sentMessageDiv.classList.add('sent-message');
        sentMessageDiv.textContent = `Me: ${message}`;
        chatWindow.appendChild(sentMessageDiv);
    }

    function saveMessageToServlet(userTo, message) {
        // Prepare data
        const data = new URLSearchParams();
        data.append('friendName', currentFriend);
        data.append('message', message);

        // Send POST request to servlet
        fetch('/SendMessageServlet', {
            method: 'POST',
            body: data
        })
            .then(response => response.text())
            .then(result => {
                console.log(result); // Log the response from servlet
                // Update chat window with new message (optional)
                displaySentMessage(message);
            })
            .catch(error => console.error('Error saving message:', error));
    }
});
