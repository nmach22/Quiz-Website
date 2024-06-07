USE mysql;
DROP TABLE IF EXISTS users_table;
DROP TABLE IF EXISTS quiz_table;
CREATE TABLE users (
                             username VARCHAR(50) PRIMARY KEY,
                             password_hash VARCHAR(50),
                             is_admin TINYINT(1) DEFAULT '0'
);

CREATE TABLE quizes (
                            quiz_id INT AUTO_INCREMENT PRIMARY KEY,
                            username VARCHAR(50),
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
                         from varchar(50),
                         to varchar(50),
                         requestDate TIMESTAMP DEFAULT NOW()
);

CREATE TABLE chat (
                            challenge_id INT AUTO_INCREMENT PRIMARY KEY,
                            from varchar(50),
                            to varchar(50),
                            message varchar(1000),
                            sentDate TIMESTAMP DEFAULT NOW()
);

CREATE TABLE quizChallenges (
                      challenge_id INT AUTO_INCREMENT PRIMARY KEY,
                      from varchar(50),
                      to varchar(50),
                      link varchar(100),
                      status varchar(20) default "pending",
                      sentDate TIMESTAMP DEFAULT NOW()
);


