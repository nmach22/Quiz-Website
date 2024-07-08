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
DROP TABLE IF EXISTS achievementTypes;

CREATE TABLE users
(
    username                  VARCHAR(50) PRIMARY KEY,
    password_hash             VARCHAR(50),
    is_admin                  TINYINT(1) DEFAULT '0',
    user_first_name           VARCHAR(50),
    user_last_name            VARCHAR(50),
    user_profile_picture_link VARCHAR(50),
    user_date_of_birth        TIMESTAMP,
    date_of_registration      TIMESTAMP  DEFAULT NOW(),
    user_bio                  VARCHAR(250)
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
    creation_date        TIMESTAMP  DEFAULT NOW(),
    duration             INT
);

CREATE TABLE history
(
    history_id INT AUTO_INCREMENT PRIMARY KEY,
    quiz_id    INT,
    username   VARCHAR(50),
    score      INT,
    time       timestamp,
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
    FOREIGN KEY (username) REFERENCES users (username),
    was_read tinyint default 0

    FOREIGN KEY (username) REFERENCES users (username)
);

CREATE TABLE achievementTypes
(
    type_id   INT AUTO_INCREMENT PRIMARY KEY,
    achievement_type VARCHAR(50),
    achievement_description   VARCHAR(200),
    achievement_badge VARCHAR(50)
);

CREATE TABLE friends
(
    friendship_id INT AUTO_INCREMENT PRIMARY KEY,
    user1      VARCHAR(50),
    user2        VARCHAR(50),
    addDate       TIMESTAMP DEFAULT NOW(),

    FOREIGN KEY (user1) REFERENCES users (username),
    FOREIGN KEY (user2) REFERENCES users (username)
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
    highest_score INT,
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
    answer_id   INT AUTO_INCREMENT PRIMARY KEY,
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
    answer_id   INT AUTO_INCREMENT PRIMARY KEY,
    quiz_id     INT,
    question_id INT,
    answer      VARCHAR(200) NOT NULL,

    FOREIGN KEY (quiz_id) REFERENCES quizzes (quiz_id),
    FOREIGN KEY (question_id) REFERENCES questions (question_id)
);
# select * from quizzes;
# select * from questionMultipleChoiceResponseAnswers;
# select * from questionMultipleChoice;
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
    answer_id   INT AUTO_INCREMENT PRIMARY KEY,
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
    answer_id   INT AUTO_INCREMENT PRIMARY KEY,
    quiz_id     INT,
    question_id INT,
    answer      VARCHAR(200) NOT NULL,
    is_correct  TINYINT(1),

    FOREIGN KEY (quiz_id) REFERENCES quizzes (quiz_id),
    FOREIGN KEY (question_id) REFERENCES questions (question_id)
);

INSERT INTO users (username, password_hash, is_admin)
VALUES ('kato', '34800e15707fae815d7c90d49de44aca97e2d759', 0),
       ('Nika', '86f7e437faa5a7fce15d1ddcb9eaeaea377667b8', 1),
       ('qatama', 'adeb6f2a18fe33af368d91b09587b68e3abcb9a7', 0),
       ('aleqsa', 'adeb6f2a18fe33af368d91b09587b68e3abcb9a7', 0);

INSERT INTO friends (user1, user2, addDate)
VALUES ('qatama', 'kato', NOW()),
       ('kato', 'qatama', NOW()),
       ('qatama', 'Nika', NOW()),
       ('Nika', 'qatama', NOW()),
       ('aleqsa', 'Nika', NOW()),
       ('Nika', 'aleqsa', NOW());

INSERT INTO quizzes (quiz_id, description, quiz_name, author,
                     is_random, one_page, immediate_correction,
                     practice_mode, creation_date, duration)
VALUES (1, 'PIRVELI QVIZI MTEL SAMYAROSHI', 'KATOS QUIZ', 'kato', 0, 0, 1, 0, NOW(), 30);

insert into announcements(announcement_id, username, announcement, title)
values (1, 'Nika', 'qatamas sheveci', 'kutu');

insert into achievements(username, achievement_type)
VALUES ('aleqsa', 'yvelaze magari');
insert into achievements(username, achievement_type)
VALUES ('aleqsa', 'yvelaze magari 2');
insert into achievements(username, achievement_type)
VALUES ('aleqsa', 'yvelaze magari 3');
insert into achievements(username, achievement_type)
VALUES ('aleqsa', 'yvelaze magari 4');
insert into achievements(username, achievement_type)
VALUES ('aleqsa', 'yvelaze magari 5');
insert into achievements(username, achievement_type)
VALUES ('aleqsa', 'yvelaze magari 6');

insert into achievementTypes(achievement_type, achievement_description, achievement_badge)
VALUES ('yvelaze magari', 'roca yvelaze magari xar', 'fas fa-medal');
insert into achievementTypes(achievement_type, achievement_description, achievement_badge)
VALUES ('yvelaze magari 2', 'roca yvelaze magari xar', 'fas fa-medal');
insert into achievementTypes(achievement_type, achievement_description, achievement_badge)
VALUES ('yvelaze magari 3', 'roca yvelaze magari xar', 'fas fa-users');
insert into achievementTypes(achievement_type, achievement_description, achievement_badge)
VALUES ('yvelaze magari 4', 'roca yvelaze magari xar', 'fas fa-lightbulb');
insert into achievementTypes(achievement_type, achievement_description, achievement_badge)
VALUES ('yvelaze magari 5', 'roca yvelaze magari xar', 'fas fa-users');
insert into achievementTypes(achievement_type, achievement_description, achievement_badge)
VALUES ('yvelaze magari 6', 'roca yvelaze magari xar', 'fas fa-lightbulb');

select *
from quizzes;

INSERT INTO questions (question_id, quiz_id, author, question_type)
VALUES (1,1,'kato','questionResponse'),
       (2,1,'kato','questionResponse'),
       (3,1,'kato','questionResponse'),
       (4,1,'kato','questionMultipleChoice'),
       (5,1,'kato','questionMultipleChoice'),
       (6,1,'kato','questionPictureResponse'),
       (7,1,'kato','questionFillInTheBlank'),
       (8,1,'kato','questionFillInTheBlank'),
       (9,1,'kato','questionPictureResponse'),
       (10,1,'kato','questionPictureResponse'),
       (11,1,'kato','questionFillInTheBlank');
INSERT INTO questionResponse (question_id, quiz_id, question)
VALUES (1,1, 'What is the currency of Japan called?'),
       (2,1, 'What is the chemical symbol for gold on the periodic table?'),
       (3,1,'Who wrote Romeo And Juliet?');
INSERT INTO questionResponseAnswers (answer_id, quiz_id, question_id, answer)
VALUES (1,1,1,'yen'),
       (2,1,2,'Au'),
       (3,1,3,'shakespear');
INSERT INTO questionMultipleChoice (question_id, quiz_id, question)
VALUES (4,1,'Which spelling is correct?'),
       (5,1,'How many ring are on the Olympic flag?');
INSERT INTO questionMultipleChoiceResponseAnswers (answer_id, quiz_id, question_id, answer, is_correct)
VALUES (1, 1, 4, 'maintenance', 1),
       (2, 1, 4, 'maintainence', 0),
       (6, 1, 4, 'maintenence', 0),
       (7, 1, 4, 'maintainance', 0),
       (4, 1, 5, '4', 0),
       (5, 1, 5, '5', 1),
       (3, 1, 5, '6', 0);
INSERT INTO questionPictureResponse (question_id, quiz_id, picture_link, question)
VALUES (6,1,'raisins.jpg',''),
       (9,1,'hottest-planet.jpg',''),
       (10,1,'highest-point.jpg','');
INSERT INTO questionPictureResponseAnswers (answer_id, quiz_id, question_id, answer)
VALUES (1,1,6,'grape'),
       (2,1,9,'Mercury'),
       (3,1,10,'Mount Everest');
INSERT INTO questionFillInTheBlank (question_id, quiz_id, question)
VALUES (7,1,'Saturn is the _ largest planet in the solar system.'),
       (8,1,'Antarctica is the _ place on Earth.'),
       (11,1,'25% of _ is 75');
INSERT INTO questionFillInTheBlankAnswers (answer_id, quiz_id, question_id, answer)
VALUES (1,1,7,'second'),
       (2,1,8,'coldest'),
       (3,1,11,'300');

insert into chat(user_from, user_to, message) VALUES ('aleqsa', 'qatama', 'aoie');


Insert into history (history_id, quiz_id, username, score, time, date_taken) values (4, 1,'qatama',7, NOW(), NOW());
select * from history;


Insert into friendRequests(request_id, user_from, user_to)
values (1, 'kato', 'aleqsa');
Insert into friendRequests(request_id, user_from, user_to)
values (3, 'qatama', 'aleqsa');

INSERT into quizChallenges(challenge_id, user_from, user_to, highest_score, quiz_id)
values (2, 'Nika', 'aleqsa', 20, 1);

select * from friendRequests;
SELECT * FROM friendRequests WHERE user_to = 'aleqsa' AND request_id = 3 ORDER BY requestDate

select * from friends where user1 = 'aleqsa';

insert into announcements(announcement_id, username, announcement, title)
values (3, 'qatama', '1', '2nd announcements');

insert into achievements (achievement_id, username, achievement_type, dateAchieved)
values (7 , 'qatama', 'yvelaze magari', NOW() );

select *
from achievements;
