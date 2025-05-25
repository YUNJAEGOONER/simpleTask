DROP TABLE IF EXISTS task;
CREATE TABLE task
(	id BIGINT AUTO_INCREMENT PRIMARY KEY,
     task TEXT,
     user VARCHAR(30),
     pw VARCHAR(50),
     createdAt DATETIME,
     modifiedAt DATETIME
);