function toggleMessageBox(friendName, element) {
    let messageBox = element.nextElementSibling;

    if (messageBox && messageBox.classList.contains('message-box')) {
        if (messageBox.style.display === 'none' || messageBox.style.display === '') {
            messageBox.style.display = 'block';
        } else {
            messageBox.style.display = 'none';
        }
    } else {
        messageBox = document.createElement('div');
        messageBox.classList.add('message-box');
        messageBox.innerHTML = `
            <p>Message ${friendName}</p>
            <textarea class="message-text" rows="4" cols="50" style="width: 300px; height: 100px;"></textarea>
            <br>
            <button onclick="sendMessage('${friendName}', this)">Send Message</button>
        `;
        element.parentNode.insertBefore(messageBox, element.nextSibling);
    }
}

function sendMessage(friendName, element) {
    const messageBox = element.parentElement;
    const message = messageBox.querySelector('.message-text').value;

    const xhr = new XMLHttpRequest();
    xhr.open("POST", "SendMessageServlet", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            alert("Message sent to " + friendName);
            messageBox.style.display = 'none';
        } else if (xhr.readyState == 4 && xhr.status != 200) {
            alert(xhr.status + " Error while sending message.");
        }
    };
    xhr.send("friendName=" + encodeURIComponent(friendName) + "&message=" + encodeURIComponent(message));
}
