console.log("QuizScript loaded");

function openQuizSummary(quizId) {
    document.getElementById('quizIdInput').value = quizId;
    document.getElementById('quizSummaryForm').submit();
}

document.getElementById('quizSelector').addEventListener('change', function() {
    var selectedValue = this.value;
    fetchQuizzes(selectedValue);
});

function fetchQuizzes(option) {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "GetQuizzesServlet?option=" + option, true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            var quizzes = JSON.parse(xhr.responseText);
            var quizList = document.getElementById('quizList');
            quizList.innerHTML = ''; // Clear existing quizzes

            quizzes.forEach(function(quiz) {
                var listItem = document.createElement('li');
                listItem.className = 'ms-3';
                var link = document.createElement('a');
                link.href = '#';
                link.innerHTML = quiz.first;
                link.onclick = function() {
                    openQuizSummary(quiz.second);
                };
                listItem.appendChild(link);
                quizList.appendChild(listItem);
                console.log(listItem);
            });
        }
    };
    xhr.send();
}

// Initial fetch for the default selected option
fetchQuizzes(document.getElementById('quizSelector').value);