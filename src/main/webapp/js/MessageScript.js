document.addEventListener('DOMContentLoaded',
    function () {
    const chatWindow = document.getElementById('chat-window');
    const messageInput = document.getElementById('message-input');
    const chatContainer = document.getElementById('chat-container');
    const friendName = document.getElementById('friend-name');
    const username = document.getElementById('username').value;
    let currentFriend = null;

    const socket = new WebSocket('//localhost:8080/chat?user=' + username);
    socket.onmessage = function (event) {
        const data = JSON.parse(event.data);
        displayMessage(data.senderName, data.message);
        scrollToBottom();
    };

    window.toggleMessageBox = function (friend, element) {
        if (currentFriend === friend) {
            chatContainer.style.display = 'none';
            currentFriend = null;
        } else {
            chatContainer.style.display = 'block';
            // Clear chat window for new friend
            chatWindow.innerHTML = '';
            // Set the friend's name
            friendName.textContent = friend;
            currentFriend = friend;

            // Load messages for this friend from database
            loadMessages(friend).then(r => r);
            scrollToBottom();
        }
    }

    window.sendMessage = function () {
        const message = messageInput.value;
        if (message.trim() === '') {
            return;
        }
        // Send message via WebSocket
        socket.send(JSON.stringify({
            message: message,
            to: currentFriend
        }));
        // Save message to database via servlet
        saveMessageToServlet(currentFriend, message);

        scrollToBottom();
        messageInput.value = '';
    }

    // Fetch messages and display them
    async function loadMessages(friend) {
        try {
            const response = await fetch(`/FetchMessagesServlet?user_from=${friend}`);
            if (!response.ok) {
                throw new Error('Failed to fetch messages');
            }
            const messages = await response.json();
            const chatWindow = document.getElementById('chat-window');
            chatWindow.innerHTML = ''; // Clear chat window before appending messages
            messages.forEach(msg => displayMessage(msg.senderName, msg.message));
        } catch (error) {
            console.error('Error fetching messages:', error);
        }
    }


    // Function to display a message
    function displayMessage(sender, message) {
        const messageElement = document.createElement('div');
        messageElement.classList.add('message');

        const senderElement = document.createElement('strong');
        senderElement.textContent = `${sender}: `;

        const messageContent = document.createElement('span');
        messageContent.textContent = message;

        messageElement.appendChild(senderElement);
        messageElement.appendChild(messageContent);

        document.getElementById('chat-window').appendChild(messageElement);
        scrollToBottom();
    }

// Send message to the servlet and update the chat window
    function saveMessageToServlet(friend, message) {
        // Prepare data
        const data = new URLSearchParams();
        data.append('friendName', friend);
        data.append('message', message);

        // Send POST request to servlet
        fetch('/SendMessageServlet', {
            method: 'POST',
            body: data
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to send message');
                }
                // Reload messages after successfully sending the message
                return loadMessages(friend);
            })
            .catch(error => console.error('Error saving message:', error));
    }

    function scrollToBottom() {
        const chatWindow = document.getElementById('chat-window');
        chatWindow.scrollTop = chatWindow.scrollHeight - chatWindow.clientHeight;
    }
});
