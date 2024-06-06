USE mysql;
DROP TABLE IF EXISTS users_table;
DROP TABLE IF EXISTS quiz_table;
CREATE TABLE users_table (
                             username VARCHAR(50) PRIMARY KEY,
                             password VARCHAR(50),
                             is_admin TINYINT(1) DEFAULT '0',
                             user TEXT
);
CREATE TABLE quiz_table (
                            quiz_id INT PRIMARY KEY,
                            username VARCHAR(50),
                            is_random TINYINT(1) DEFAULT '0',
                            one_page TINYINT(1) DEFAULT '0',
                            immediate_correction TINYINT(1) DEFAULT '0',
                            practice_mode TINYINT(1) DEFAULT '0',
                            quiz TEXT
);
