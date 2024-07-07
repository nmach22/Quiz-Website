document.addEventListener('DOMContentLoaded', function (){
    function getUnreadMessages(){
        return Math.floor(Math.random() * 10) + 1;

    }
    const unreadCount = getUnreadMessages();
    const elem = document.getElementById('unread-count');
    elem.textContent = unreadCount;
});