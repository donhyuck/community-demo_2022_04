# DB 생성
DROP DATABASE IF EXISTS sts_community;
CREATE DATABASE sts_community;
USE sts_community;

DROP TABLE IF EXISTS article;

# 게시물 테이블 생성
CREATE TABLE article (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    title CHAR(100) NOT NULL,
    `body` TEXT NOT NULL
);

SELECT * FROM article;

# 게시물 테스트 데이터
INSERT INTO article
SET regDate=NOW(),
updateDate=NOW(),
title='제목1',
`body`='내용1';

INSERT INTO article
SET regDate=NOW(),
updateDate=NOW(),
title='제목2',
`body`='내용2';

INSERT INTO article
SET regDate=NOW(),
updateDate=NOW(),
title='제목3',
`body`='내용3';