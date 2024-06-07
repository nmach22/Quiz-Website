USE mysql;
DROP TABLE IF EXISTS users_table;
DROP TABLE IF EXISTS quiz_table;
CREATE TABLE users (
                             username VARCHAR(50) PRIMARY KEY,
                             password VARCHAR(50),
                             is_admin TINYINT(1) DEFAULT '0',
                             user TEXT
);

CREATE TABLE quizes (
                            quiz_id INT AUTO_INCREMENT PRIMARY KEY,
                            username VARCHAR(50),
                            is_random TINYINT(1) DEFAULT '0',
                            one_page TINYINT(1) DEFAULT '0',
                            immediate_correction TINYINT(1) DEFAULT '0',
                            practice_mode TINYINT(1) DEFAULT '0',
                            quiz TEXT,
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
                            username varchar(50),
                            friend varchar(50),
                            isRequest TINYINT(1),
                            requestOrAddDate TIMESTAMP DEFAULT NOW()
);

CREATE TABLE chat (
                            username varchar(50),
                            friend varchar(50),
                            message varchar(1000),
                            isRequest TINYINT(1),
                            sentDate TIMESTAMP DEFAULT NOW()
);


