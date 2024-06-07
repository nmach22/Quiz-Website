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
                    time TIMESTAMP
);

CREATE TABLE announcements (
                    announcement_id INT AUTO_INCREMENT PRIMARY KEY,
                    user_id         INT,
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
                    challenge_id INT AUTO_INCREMENT PRIMARY KEY,
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
                      status varchar(20) default 'pending',
                      sentDate TIMESTAMP DEFAULT NOW()
);

CREATE TABLE questions (
                    question_id INT AUTO_INCREMENT PRIMARY KEY,
                    author VARCHAR(50),
                    question_type VARCHAR(50) NOT NULL
);

CREATE TABLE questionResponse (
                    question_id INT PRIMARY KEY,
                    question varchar(200) NOT NULL ,
                    answer varchar(200) NOT NULL
);

CREATE TABLE questionFillInTheBlank (
                    question_id INT PRIMARY KEY,
                    question varchar(200) NOT NULL ,
                    answer varchar(200) NOT NULL
);

CREATE TABLE questionPictureResponse (
                    question_id INT PRIMARY KEY,
                    picture_link varchar(200) NOT NULL ,
                    answer varchar(200) NOT NULL
);

CREATE TABLE questionMultipleChoice (
                    question_id INT PRIMARY KEY,
                    question varchar(200),
                    poss_ans_1 varchar(100),
                    poss_ans_2 varchar(100),
                    poss_ans_3 varchar(100),
                    poss_ans_4 varchar(100),
                    poss_ans_5 varchar(100),
                    answer INT NOT NULL
);

select * from users;
select * from friends;
select * from history;