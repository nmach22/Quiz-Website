USE mysql;

DROP TABLE IF EXISTS history;
DROP TABLE IF EXISTS announcements;
DROP TABLE IF EXISTS achievements;
DROP TABLE IF EXISTS friends;
DROP TABLE IF EXISTS friendRequests;
DROP TABLE IF EXISTS chat;
DROP TABLE IF EXISTS questionResponse;
DROP TABLE IF EXISTS questionFillInTheBlank;
DROP TABLE IF EXISTS questionPictureResponse;
DROP TABLE IF EXISTS questionMultipleChoice;
DROP TABLE IF EXISTS questionResponseAnswers;
DROP TABLE IF EXISTS questionFillInTheBlankAnswers;
DROP TABLE IF EXISTS questionMultipleChoiceResponseAnswers;
DROP TABLE IF EXISTS questionPictureResponseAnswers;
DROP TABLE IF EXISTS quizChallenges;
DROP TABLE IF EXISTS questions;
DROP TABLE IF EXISTS quizzes;
DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    username                  VARCHAR(50) PRIMARY KEY,
    password_hash             VARCHAR(50),
    is_admin                  TINYINT(1) DEFAULT '0',
    user_first_name           VARCHAR(50),
    user_last_name            VARCHAR(50),
    user_profile_picture_link VARCHAR(50),
    user_date_of_birth        TIMESTAMP,
    date_of_registration      TIMESTAMP  DEFAULT NOW()
);

CREATE TABLE quizzes
(
    quiz_id              INT AUTO_INCREMENT PRIMARY KEY,
    description          VARCHAR(1000),
    quiz_name            VARCHAR(1000),
    author               VARCHAR(50),
    is_random            TINYINT(1) DEFAULT '0',
    one_page             TINYINT(1) DEFAULT '0',
    immediate_correction TINYINT(1) DEFAULT '0',
    practice_mode        TINYINT(1) DEFAULT '0',
    creation_date        TIMESTAMP  DEFAULT NOW()
);

CREATE TABLE history
(
    history_id INT AUTO_INCREMENT PRIMARY KEY,
    quiz_id    INT,
    username   VARCHAR(50),
    score      INT,
    time       TIMESTAMP,
    date_taken TIMESTAMP DEFAULT NOW(),

    FOREIGN KEY (quiz_id) REFERENCES quizzes (quiz_id)
);

CREATE TABLE announcements (
                    announcement_id INT AUTO_INCREMENT PRIMARY KEY,
                    username         varchar(50),
                    announcement    varchar(1000),
                    title varchar(200),
                    creation_date   TIMESTAMP DEFAULT NOW(),
                    FOREIGN KEY (username) REFERENCES users (username)

);

CREATE TABLE achievements
(
    achievement_id   INT AUTO_INCREMENT PRIMARY KEY,
    username         VARCHAR(50),
    achievement_type VARCHAR(50),
    dateAchieved     TIMESTAMP DEFAULT NOW(),

    FOREIGN KEY (username) REFERENCES users (username)
);

CREATE TABLE friends
(
    friendship_id INT AUTO_INCREMENT PRIMARY KEY,
    username      VARCHAR(50),
    friend        VARCHAR(50),
    addDate       TIMESTAMP DEFAULT NOW(),

    FOREIGN KEY (username) REFERENCES users (username)
);

CREATE TABLE friendRequests
(
    request_id  INT AUTO_INCREMENT PRIMARY KEY,
    user_from   VARCHAR(50),
    user_to     VARCHAR(50),
    requestDate TIMESTAMP DEFAULT NOW(),

    FOREIGN KEY (user_from) REFERENCES users (username),
    FOREIGN KEY (user_to) REFERENCES users (username)
);

CREATE TABLE chat
(
    chat_id   INT AUTO_INCREMENT PRIMARY KEY,
    user_from VARCHAR(50),
    user_to   VARCHAR(50),
    message   VARCHAR(1000),
    sentDate  TIMESTAMP DEFAULT NOW(),

    FOREIGN KEY (user_from) REFERENCES users (username),
    FOREIGN KEY (user_to) REFERENCES users (username)
);

CREATE TABLE quizChallenges
(
    challenge_id INT AUTO_INCREMENT PRIMARY KEY,
    user_from    VARCHAR(50),
    user_to      VARCHAR(50),
    link         VARCHAR(100),
    quiz_id      INT,
    status       VARCHAR(20) default 'pending',
    sentDate     TIMESTAMP   DEFAULT NOW(),

    FOREIGN KEY (user_from) REFERENCES users (username),
    FOREIGN KEY (user_to) REFERENCES users (username),
    FOREIGN KEY (quiz_id) REFERENCES quizzes (quiz_id)
);

CREATE TABLE questions
(
    question_id   INT AUTO_INCREMENT PRIMARY KEY,
    quiz_id       INT,
    author        VARCHAR(50),
    question_type VARCHAR(50) NOT NULL,
    FOREIGN KEY (quiz_id) REFERENCES quizzes (quiz_id)
);

CREATE TABLE questionResponse
(
    question_id INT PRIMARY KEY,
    quiz_id     INT,
    question    VARCHAR(200) NOT NULL,

    FOREIGN KEY (quiz_id) REFERENCES quizzes (quiz_id),
    FOREIGN KEY (question_id) REFERENCES questions (question_id)
);


CREATE TABLE questionResponseAnswers
(
    answer_id   INT PRIMARY KEY,
    quiz_id     INT,
    question_id INT,
    answer      VARCHAR(200) NOT NULL,

    FOREIGN KEY (quiz_id) REFERENCES quizzes (quiz_id),
    FOREIGN KEY (question_id) REFERENCES questions (question_id)
);

CREATE TABLE questionFillInTheBlank
(
    question_id INT PRIMARY KEY,
    quiz_id     INT,
    question    VARCHAR(200) NOT NULL,

    FOREIGN KEY (quiz_id) REFERENCES quizzes (quiz_id),
    FOREIGN KEY (question_id) REFERENCES questions (question_id)

);

CREATE TABLE questionFillInTheBlankAnswers
(
    answer_id   INT PRIMARY KEY,
    quiz_id     INT,
    question_id INT,
    answer      VARCHAR(200) NOT NULL,

    FOREIGN KEY (quiz_id) REFERENCES quizzes (quiz_id),
    FOREIGN KEY (question_id) REFERENCES questions (question_id)
);

CREATE TABLE questionPictureResponse
(
    question_id  INT PRIMARY KEY,
    quiz_id      INT,
    picture_link VARCHAR(200) NOT NULL,
    question     VARCHAR(200) NOT NULL,

    FOREIGN KEY (quiz_id) REFERENCES quizzes (quiz_id),
    FOREIGN KEY (question_id) REFERENCES questions (question_id)

);
CREATE TABLE questionPictureResponseAnswers
(
    answer_id   INT PRIMARY KEY,
    quiz_id     INT,
    question_id INT,
    answer      VARCHAR(200) NOT NULL,

    FOREIGN KEY (quiz_id) REFERENCES quizzes (quiz_id),
    FOREIGN KEY (question_id) REFERENCES questions (question_id)
);
CREATE TABLE questionMultipleChoice
(
    question_id INT PRIMARY KEY,
    quiz_id     INT,
    question    VARCHAR(200),

    FOREIGN KEY (quiz_id) REFERENCES quizzes (quiz_id),
    FOREIGN KEY (question_id) REFERENCES questions (question_id)
);
CREATE TABLE questionMultipleChoiceResponseAnswers
(
    answer_id   INT PRIMARY KEY,
    quiz_id     INT,
    question_id INT,
    answer      VARCHAR(200) NOT NULL,
    is_correct  TINYINT(1),

    FOREIGN KEY (quiz_id) REFERENCES quizzes (quiz_id),
    FOREIGN KEY (question_id) REFERENCES questions (question_id)
);



select *
from users;
select *
from friends;
select *
from history;

INSERT INTO users (username, password_hash, is_admin)
VALUES ('kato', '34800e15707fae815d7c90d49de44aca97e2d759', 0),
       ('Nika', '86f7e437faa5a7fce15d1ddcb9eaeaea377667b8', 1),
       ('qatama', 'adeb6f2a18fe33af368d91b09587b68e3abcb9a7', 0),
       ('aleqsa', 'adeb6f2a18fe33af368d91b09587b68e3abcb9a7', 0);

INSERT INTO friends (username, friend, addDate)
VALUES ('qatama', 'kato', NOW()),
       ('kato', 'qatama', NOW()),
       ('aleqsa', 'Nika', NOW()),
       ('Nika', 'aleqsa', NOW());

INSERT INTO quizzes (quiz_id, description, quiz_name, author,
                     is_random, one_page, immediate_correction,
                     practice_mode, creation_date)
VALUES (1, 'PIRVELI QVIZI MTEL SAMYAROSHI', 'KATOS QUIZ', 'kato', 0, 0, 0, 0, NOW());

select *
from quizzes;