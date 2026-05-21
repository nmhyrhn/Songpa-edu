CREATE DATABASE IF NOT EXISTS memo_db;

USE memo_db;

DROP TABLE IF EXISTS tbl_memo;

CREATE TABLE tbl_memo (
                          memo_id INT AUTO_INCREMENT PRIMARY KEY,
                          content VARCHAR(255) NOT NULL,
                          created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO tbl_memo(content) VALUES
                                  ('Servlet API에 JDBC 붙이기'),
                                  ('Next.js 미니 프로젝트에서는 fetch 주소만 맞추기');
