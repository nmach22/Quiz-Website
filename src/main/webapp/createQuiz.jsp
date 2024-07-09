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
    <link rel="stylesheet" type="text/css" href="css/global.css">
</head>
<body>
<script>
    function addQuestion() {
        console.log("adding question");
    }
</script>
<div class="d-flex justify-content-center h-100 p-5">
    <form id="myForm" class="w-100 text-white" action="CreateQuizServlet" method="post">
        <div class="d-flex justify-content-start gap-5 items-start">
            <div class="left-form-container w-33">
                <div class="mb-3">
                    <label for="quizTitle" class="form-label">Quiz Title</label>
                    <input type="text" class="form-control" id="quizTitle" name="title" placeholder="Enter quiz title">
                </div>
                <div class="mb-3">
                    <label for="quizDuration" class="form-label">Quiz Duration (minutes)</label>
                    <input type="number" value="3" class="form-control" id="quizDuration" name="duration" placeholder="Enter quiz duration">
                </div>
                <div class="mb-3">
                    <label for="quizDescription" class="form-label">Description</label>
                    <textarea class="form-control" id="quizDescription" rows="3" name="description" placeholder="Enter quiz description"></textarea>
                </div>

                <div class="d-flex flex-wrap">
                    <div class="mb-3 form-check w-50">
                        <input type="checkbox" class="form-check-input" id="random" name="random">
                        <label class="form-check-label" for="random">Random</label>
                    </div>
                    <div class="mb-3 form-check w-50">
                        <input type="checkbox" class="form-check-input" id="onePage" name="onePage">
                        <label class="form-check-label" for="onePage">One Page</label>
                    </div>
                    <div class="mb-3 form-check w-50">
                        <input type="checkbox" class="form-check-input" id="immediateCorrection" name="immediateCorrection">
                        <label class="form-check-label" for="immediateCorrection">Immediate Correction</label>
                    </div>
                    <div class="mb-3 form-check w-50" style="visibility: hidden">
                        <input type="checkbox" class="form-check-input" id="practiceMode" name="practiceMode">
                        <label class="form-check-label" for="practiceMode">Practice Mode</label>
                    </div>
                </div>
                <label id="submit-questions-btn" class="btn btn-primary" onclick="submitForm()">Submit Quiz</label>

            </div>

            <div class="right-form-container w-33">
                <button type="button" class="btn btn-primary mb-2" id="add-question-btn">Add question</button>
                <div id="question-container" class="mb-3"></div>

                <script>
                    let questionIndex = 0

                    const addQuestionDynamically = function (id, questionDiv) {
                        const answerContainer = document.createElement("div")
                        answerContainer.id = 'answerContainer-' + id
                        answerContainer.classList = 'd-flex justify-content-between gap-1';
                        const possibleAns = document.createElement('input');
                        const addAnswerBtn = document.createElement('button')
                        addAnswerBtn.classList = 'mb-1 rounded border border-0 p-1'
                        addAnswerBtn.innerHTML= '+'
                        addAnswerBtn.setAttribute("type", "button");
                        possibleAns.classList = 'w-100 mb-1 rounded border border-0 p-1';
                        possibleAns.type = 'text';
                        possibleAns.key = 'possible Answer ' + id;
                        possibleAns.placeholder = 'Answer ' + id;
                        answerContainer.appendChild(possibleAns)
                        answerContainer.appendChild(addAnswerBtn)

                        return answerContainer;
                    };
                    document.getElementById('add-question-btn').addEventListener('click', function () {
                        const questionDiv = document.createElement('div');
                        questionDiv.className = 'question-div';

                        const select = document.createElement('select');
                        select.classList = 'mb-1 p-1 rounded border border-0';
                        select.innerHTML = `
                <option value="">Select question type</option>
                <option value="questionResponse">Open Question</option>
                <option value="questionFillInTheBlank">Fill Blanks</option>
                <option value="questionPictureResponse">Picture Question</option>
                <option value="questionMultipleChoice">Multiple Choice</option>
            `;
                        select.addEventListener('change', function () {
                            const selectedValue = select.value;
                            questionDiv.innerHTML = '';
                            questionDiv.appendChild(select);

                            if (selectedValue === 'questionResponse' || selectedValue === 'questionFillInTheBlank') {
                                const questionInput = document.createElement('input');
                                questionInput.classList = 'w-100 mb-1 rounded border border-0 p-1';
                                questionInput.type = 'text';
                                questionInput.key = 'question';
                                if (selectedValue === 'questionResponse') {
                                    questionInput.placeholder = 'Enter question';
                                } else {
                                    questionInput.placeholder = 'Enter question (use "_" instead of blank space)';
                                }

                                const answerInput = document.createElement('input');
                                answerInput.classList = 'w-100 mb-3 rounded border border-0 p-1';
                                answerInput.type = 'text';
                                answerInput.key = 'answer';
                                answerInput.placeholder = 'Enter correct answer';

                                questionDiv.appendChild(questionInput);
                                questionDiv.appendChild(answerInput);
                            } else if (selectedValue === 'questionPictureResponse') {
                                const questionInput = document.createElement('input');
                                questionInput.classList = 'w-100 mb-1 rounded border border-0 p-1';
                                questionInput.type = 'text';
                                questionInput.key = 'question';
                                questionInput.placeholder = 'Enter question';

                                const image = document.createElement('input');
                                image.classList = 'w-100 mb-1 rounded border border-0 p-1';
                                image.type = 'text';
                                image.key = 'url';
                                image.placeholder = 'Enter Image URL';

                                const answerInput = document.createElement('input');
                                answerInput.classList = 'w-100 mb-3 rounded border border-0 p-1';
                                answerInput.type = 'text';
                                answerInput.key = 'answer';
                                answerInput.placeholder = 'Enter correct answer';

                                questionDiv.appendChild(questionInput);
                                questionDiv.appendChild(image);
                                questionDiv.appendChild(answerInput);

                            } else if (selectedValue === 'questionMultipleChoice') {
                                const questionInput = document.createElement('input');
                                questionInput.classList = 'w-100 mb-1 rounded border border-0 p-1';
                                questionInput.type = 'text';
                                questionInput.key = 'question';
                                questionInput.placeholder = 'Enter question';

                                questionDiv.appendChild(questionInput);
                                for (let i = 1; i <= 3; i++) {
                                    questionDiv.appendChild(addQuestionDynamically(i, questionDiv));
                                }

                                const answerInput = document.createElement('input');
                                answerInput.classList = 'w-100 mb-3 rounded border border-0 p-1';
                                answerInput.type = 'text';
                                answerInput.key = 'answer';
                                answerInput.placeholder = 'Enter correct answer';
                                questionDiv.appendChild(answerInput);

                            }
                        });

                        questionDiv.appendChild(select);
                        document.getElementById('question-container').appendChild(questionDiv);

                        questionIndex += 1;
                    });
                </script>

                <script>
                    function submitForm() {
                        const duration = document.getElementById("quizDuration").value;
                        const quizName = document.getElementById("quizTitle").value;
                        if (!duration || !quizName) {
                            if (!quizName) {
                                alert("Quiz title field shouldn't be empty");
                            } else {
                                alert("Quiz duration field shouldn't be empty");
                            }
                            return;
                        }
                        const questionContainer = document.getElementById('question-container');
                        const questionDivs = questionContainer.getElementsByClassName('question-div');
                        if(questionDivs.length === 0){
                            alert("You must add at least one question.");
                            return;
                        }
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
                        form.submit();
                    };
                </script>

                <div id="question"></div>
            </div>
        </div>
    </form>
</div>

<script>
    document.getElementById("onePage").addEventListener('input', function (evt) {
        if (evt.target.checked) document.getElementById("immediateCorrection").setAttribute("disabled", "true")
        else document.getElementById("immediateCorrection").removeAttribute("disabled")
    });
</script>

</body>
</html>
