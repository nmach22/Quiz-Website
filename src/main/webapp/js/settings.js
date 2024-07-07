document.addEventListener('DOMContentLoaded', function() {
    // Initialize AJAX call to servlet
    const xhr = new XMLHttpRequest();
    const name = document.getElementById('username').value;
    xhr.open('GET', 'SaveChangesServlet?username=' + encodeURIComponent(name), true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            document.getElementById('user-bio').value = xhr.responseText.substring(0,xhr.responseText.indexOf("/"));
            document.getElementById('firstname').value = xhr.responseText.substring(xhr.responseText.indexOf("/") + 1, xhr.responseText.lastIndexOf("/"));
            document.getElementById('lastname').value = xhr.responseText.substring(xhr.responseText.lastIndexOf("/") + 1);

            // Initialize character count based on the initial value of user-bio
            updateCharacterCount();
        }
    };
    xhr.send();

    // Function to update character count
    function updateCharacterCount() {
        const area = document.getElementById('user-bio');
        const charCount = document.getElementById('char-count');
        charCount.textContent = area.value.length + " / 250 characters";

        if (area.value.length >= 250) {
            area.value = area.value.substring(0, 250); // Truncate excess characters
            charCount.style.color = 'red'; // Optionally change color to indicate limit reached
        } else {
            charCount.style.color = ''; // Reset color
        }
    }

    // Event listener for input changes in user-bio
    const area = document.getElementById('user-bio');
    area.addEventListener('input', function() {
        updateCharacterCount();
    });
});
