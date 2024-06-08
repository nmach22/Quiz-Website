USE mysql;

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS quizzes;
DROP TABLE IF EXISTS history;
DROP TABLE IF EXISTS announcements;
DROP TABLE IF EXISTS achievements;
DROP TABLE IF EXISTS friends;
DROP TABLE IF EXISTS friendRequests;
DROP TABLE IF EXISTS chat;
DROP TABLE IF EXISTS quizChallenges;
DROP TABLE IF EXISTS questions;
DROP TABLE IF EXISTS questionResponse;
DROP TABLE IF EXISTS questionFillInTheBlank;
DROP TABLE IF EXISTS questionPictureResponse;
DROP TABLE IF EXISTS questionMultipleChoice;

CREATE TABLE users (

    username VARCHAR(50) PRIMARY KEY,
    password_hash VARCHAR(50),
    is_admin TINYINT(1) DEFAULT '0'
);

CREATE TABLE quizzes (
                    quiz_id INT AUTO_INCREMENT PRIMARY KEY,
                    author VARCHAR(50),
                    is_random TINYINT(1) DEFAULT '0',
                    one_page TINYINT(1) DEFAULT '0',
                    immediate_correction TINYINT(1) DEFAULT '0',
                    practice_mode TINYINT(1) DEFAULT '0',
                    creation_date TIMESTAMP DEFAULT NOW()
);

CREATE TABLE history (
                    history_id INT AUTO_INCREMENT PRIMARY KEY,
                    quiz_id INT,
                    username VARCHAR(50),
                    score INT,
                    time TIMESTAMP,
                    date_taken TIMESTAMP DEFAULT NOW()
);

CREATE TABLE announcements (
                    announcement_id INT AUTO_INCREMENT PRIMARY KEY,
                    username         varchar(50),
                    announcement    varchar(1000),
                    creation_date   TIMESTAMP DEFAULT NOW()
);

CREATE TABLE achievements (
                    achievement_id INT AUTO_INCREMENT PRIMARY KEY,
                    username varchar(50),
                    achievement_type varchar(50),
                    dateAchieved TIMESTAMP DEFAULT NOW()
);

CREATE TABLE friends (
                    friendship_id INT AUTO_INCREMENT PRIMARY KEY,
                    username varchar(50),
                    friend varchar(50),
                    addDate TIMESTAMP DEFAULT NOW()
);

CREATE TABLE friendRequests (
                     request_id INT AUTO_INCREMENT PRIMARY KEY,
                     user_from varchar(50),
                     user_to varchar(50),
                     requestDate TIMESTAMP DEFAULT NOW()
);

CREATE TABLE chat (
                    chat_id INT AUTO_INCREMENT PRIMARY KEY,
                    user_from varchar(50),
                    user_to varchar(50),
                    message varchar(1000),
                    sentDate TIMESTAMP DEFAULT NOW()
);

CREATE TABLE quizChallenges (
                      challenge_id INT AUTO_INCREMENT PRIMARY KEY,
                      user_from varchar(50),
                      user_to varchar(50),
                      link varchar(100),
                      quiz_id INT,
                      status varchar(20) default 'pending',
                      sentDate TIMESTAMP DEFAULT NOW()
);

CREATE TABLE questions (
                    question_id INT AUTO_INCREMENT PRIMARY KEY,
                    quiz_id INT,
                    author VARCHAR(50),
                    question_type VARCHAR(50) NOT NULL
);

CREATE TABLE questionResponse (
                    question_id INT PRIMARY KEY,
                    quiz_id INT,
                    question varchar(200) NOT NULL
);


CREATE TABLE questionResponseAnswers(
    answer_id INT PRIMARY KEY ,
    quiz_id INT,
    question_id INT ,
    answer varchar(200) NOT NULL
);

CREATE TABLE questionFillInTheBlank (
                    question_id INT PRIMARY KEY,
                    quiz_id INT,
                    question varchar(200) NOT NULL

);

CREATE TABLE questionFillInTheBlankAnswers(
    answer_id INT PRIMARY KEY ,
    quiz_id INT,
    question_id INT,
    answer varchar(200) NOT NULL
);

CREATE TABLE questionPictureResponse (
        question_id INT PRIMARY KEY,
        quiz_id INT,
        picture_link varchar(200) NOT NULL,
        question varchar(200) NOT NULL

);
CREATE TABLE questionPictureResponseAnswers(
        answer_id INT PRIMARY KEY ,
        quiz_id INT,
        question_id INT,
        answer varchar(200) NOT NULL
);
CREATE TABLE questionMultipleChoice (
    question_id INT PRIMARY KEY,
    quiz_id INT,
    question varchar(200)
);
CREATE TABLE questionMultipleChoiceResponseAnswers(
    answer_id INT PRIMARY KEY ,
    quiz_id INT,
    question_id INT,
    answer varchar(200) NOT NULL,
    is_correct TINYINT(1)
);



select * from users;
select * from friends;
select * from history;

INSERT INTO users (username, password_hash, is_admin) VALUES
                ('kato', '34800e15707fae815d7c90d49de44aca97e2d759', 1),
                ('Nika', '86f7e437faa5a7fce15d1ddcb9eaeaea377667b8', 1),
                ('qatama', 'adeb6f2a18fe33af368d91b09587b68e3abcb9a7', 0);

INSERT INTO friends ( username, friend, addDate) VALUES
                ('luka', 'kato', NOW()),
                ('kato', 'luka', NOW()),
                ('aleqsa', 'nika', NOW()),
                ('nika', 'aleqsa', NOW());