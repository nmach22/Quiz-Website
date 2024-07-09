function getUrlParameter(name) {
    name = name.replace(/[[]/, '\\[').replace(/[\]]/, '\\]');
    var regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
    var results = regex.exec(location.search);
    return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
}

function displayAlert() {
    var alertMessage = getUrlParameter('alert');
    if (alertMessage) {
        alert(alertMessage);
    }
}
window.onload = displayAlert;