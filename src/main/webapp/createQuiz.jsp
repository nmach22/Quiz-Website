<%--
  Created by IntelliJ IDEA.
  User: nika
  Date: 7/6/24
  Time: 7:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Quiz</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>
<script>
    function addQuestion() {
        console.log("adding question");
    }
</script>
<form id="myForm" action="CreateQuizServlet" method="post">
    <div class="mb-3">
        <label for="quizTitle" class="form-label">Quiz Title</label>
        <input type="text" class="form-control" id="quizTitle" name="title">
    </div>

    <div class="mb-3">
        <label for="quizDescription" class="form-label">Description</label>
        <input type="text" class="form-control" id="quizDescription" rows="3" name="description"></input>
    </div>

    <div class="mb-3">
        <label for="quizAuthor" class="form-label">Author</label>
        <input type="text" class="form-control disabled" aria-disabled="true" id="quizAuthor" name="author">

    </div>

    <div class="mb-3 form-check">
        <input type="checkbox" class="form-check-input" id="random" name="random">
        <label class="form-check-label" for="random">Random</label>
    </div>
    <div class="mb-3 form-check">
        <input type="checkbox" class="form-check-input" id="onePag" name="onePage">
        <label class="form-check-label" for="onePag">One Page</label>
    </div>
<%--    if OnePage is on, then this shouldn't be possible to check --%>
    <div class="mb-3 form-check">
        <input type="checkbox" class="form-check-input" id="immediateCorrection" name="immediateCorrection">
        <label class="form-check-label" for="immediateCorrection">Immediate Correction</label>
    </div>
    <div class="mb-3 form-check">
        <input type="checkbox" class="form-check-input" id="practiceMode" name="practiceMode">
        <label class="form-check-label" for="practiceMode">Practice Mode</label>
    </div>
    <label id="add-question-btn">Add Question</label>
    <div id="question-container"></div>

    <script>
        let questionIndex = 0
        document.getElementById('add-question-btn').addEventListener('click', function () {
            const questionDiv = document.createElement('div');
            questionDiv.className = 'question-div';

            const select = document.createElement('select');
            select.innerHTML = `
                <option value="">Select question type</option>
                <option value="openQuestion">Open Question</option>
                <option value="fillBlanks">Fill Blanks</option>
                <option value="pictureQuestion">Picture Question</option>
                <option value="multipleChoice">Multiple Choice</option>
            `;
            select.addEventListener('change', function () {
                const selectedValue = select.value;
                questionDiv.innerHTML = ''; // Clear the div
                questionDiv.appendChild(select); // Re-add the dropdown

                if (selectedValue === 'openQuestion' || selectedValue === 'fillBlanks') {
                    const questionInput = document.createElement('input');
                    questionInput.type = 'text';
                    questionInput.key = 'question';
                    questionInput.placeholder = 'Enter question';

                    const answerInput = document.createElement('input');
                    answerInput.type = 'text';
                    answerInput.key = 'answer';
                    answerInput.placeholder = 'Enter correct answer';

                    questionDiv.appendChild(questionInput);
                    questionDiv.appendChild(answerInput);
                } else if (selectedValue === 'pictureQuestion') {
                    const questionInput = document.createElement('input');
                    questionInput.type = 'text';
                    questionInput.key = 'question';
                    questionInput.placeholder = 'Enter question';

                    const image = document.createElement('input');
                    image.type = 'text';
                    image.key = 'url';
                    image.placeholder = 'Enter URL';

                    const answerInput = document.createElement('input');
                    answerInput.type = 'text';
                    answerInput.key = 'answer';
                    answerInput.placeholder = 'Enter correct answer';

                    questionDiv.appendChild(questionInput);
                    questionDiv.appendChild(image);
                    questionDiv.appendChild(answerInput);

                } else if (selectedValue === 'multipleChoice') {
                    const questionInput = document.createElement('input');
                    questionInput.type = 'text';
                    questionInput.key = 'question';
                    questionInput.placeholder = 'Enter question';

                    const answerInput = document.createElement('input');
                    answerInput.type = 'text';
                    answerInput.key = 'answer';
                    answerInput.placeholder = 'Enter correct answer';

                    questionDiv.appendChild(questionInput);
                    questionDiv.appendChild(answerInput);

                    for (let i = 1; i <= 3; i++) {
                        const possibleAns = document.createElement('input');
                        possibleAns.type = 'text';
                        possibleAns.key = 'possible Answer ' + i;
                        possibleAns.placeholder = 'Answer ' + i;
                        questionDiv.appendChild(possibleAns);
                    }
                }
                // You can add more cases for other question types here
            });

            questionDiv.appendChild(select);
            document.getElementById('question-container').appendChild(questionDiv);

            questionIndex += 1;
        });
    </script>

    <script>
        function submitForm(){
            const questionContainer = document.getElementById('question-container');
            const questionDivs = questionContainer.getElementsByClassName('question-div');
            const form = document.getElementById('myForm');
            console.log(questionDivs);

            let questions = []

            for (let i = 0; i < questionDivs.length; i++) {
                let question = {id: i}
                const select = questionDivs[i].getElementsByTagName('select')[0];
                const inputs = questionDivs[i].getElementsByTagName('input');

                question['type'] = select.value
                for (let j = 0; j < inputs.length; j++) {
                    question[inputs[j].key] = inputs[j].value
                }
                questions.push(question)
            }
            console.log(questions);
            const typeField = document.createElement('input');
            typeField.type = 'hidden';
            typeField.name = `questions`;
            typeField.value = JSON.stringify({questions: questions});
            form.appendChild(typeField);
            form.submit()
        };
    </script>
    <div id="question">

    </div>

    <label  id="submit-questions-btn" class="btn btn-primary" onclick="submitForm()">Submit</label>
</form>

</body>
</html>
