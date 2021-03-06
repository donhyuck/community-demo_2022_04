# DB 생성
DROP DATABASE IF EXISTS sts_community;
CREATE DATABASE sts_community;
USE sts_community;


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


# 회원 테이블 생성
CREATE TABLE `member` (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    loginId CHAR(20) NOT NULL,
    loginPw CHAR(60) NOT NULL,
    authLevel SMALLINT(2) UNSIGNED DEFAULT 3 COMMENT '권한레벨(3=일반,7=관리자)',
    `name` CHAR(20) NOT NULL,
    nickname CHAR(20) NOT NULL,
    cellPhoneNo CHAR(20) NOT NULL,
    email  CHAR(50) NOT NULL,
    delStatus TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '탈퇴여부(0=탈퇴전,1=탈퇴)',
    delDate DATETIME COMMENT '탈퇴날짜'
);

SELECT * FROM `member`;

# 회원, 테스트 데이터 생성(관리자 회원)
INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'admin',
loginPw = 'admin',
authLevel = 7,
`name` = '관리자',
nickname = '관리자01',
cellPhoneNo = '01012341234',
email = 'admin01@test.com';

# 회원, 테스트 데이터 생성(일반 회원)
INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'test1',
loginPw = 'test1',
`name` = '이몽룡',
nickname = '사용자1',
cellPhoneNo = '01045674567',
email = 'leedonhyuck123@gmail.com';

# 회원, 테스트 데이터 생성(일반 회원)
INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'test2',
loginPw = 'test2',
authLevel = 5,
`name` = '성춘향',
nickname = '사용자2',
cellPhoneNo = '0107894789',
email = 'test02@test.com';

# 회원, 테스트 데이터 생성(탈퇴 회원)
INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'delete1',
loginPw = 'delete1',
`name` = '김철수',
nickname = '철수',
cellPhoneNo = '01044554442',
email = 'lost01@test.com',
delStatus = 1,
delDate = '2022-01-05 15:00:00';

SELECT LAST_INSERT_ID();

# 게시글 테이블에 회원정보 추가
ALTER TABLE article ADD COLUMN memberId INT(10) UNSIGNED NOT NULL AFTER updateDate;

# 회원정보가 미등록된 게시물은 2번 회원으로 일괄 지정
UPDATE article
SET memberId = 2
WHERE memberId = 0;

SELECT * FROM article;

# 게시글 작성자 표시
SELECT a.*, m.nickname AS extra_writerName
FROM article AS a
LEFT JOIN `member` AS m
ON a.memberId = m.id
ORDER BY a.id DESC;

# 게시판 테이블 생성
CREATE TABLE board (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    `code` CHAR(50) NOT NULL UNIQUE COMMENT 'notice(공지사항),free1(자유게시판1),free2(자유게시판2),...',
    `name` CHAR(50) NOT NULL UNIQUE COMMENT '게시판 이름',
    delStatus TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '삭제여부(0=탈퇴전,1=탈퇴)',
    delDate DATETIME COMMENT '삭제날짜'
);

# 기본 게시판 생성
INSERT INTO board
SET regDate = NOW(),
updateDate = NOW(),
`code` = 'notice',
`name` = '공지사항';

INSERT INTO board
SET regDate = NOW(),
updateDate = NOW(),
`code` = 'free1',
`name` = '자유게시판';

# 게시판 테이블에 boardId 칼럼 추가
ALTER TABLE article ADD COLUMN boardId INT(10) UNSIGNED NOT NULL AFTER memberId;

# 기존 게시물에 게시판 정보 설정
UPDATE article
SET boardId = 1
WHERE id IN(1, 2);

UPDATE article
SET boardId = 2
WHERE id IN(3);

# 게시물 개수 늘리기
/*
INSERT INTO article
(
    regDate, updateDate, memberId, boardId, title, `body`
)
SELECT NOW(), NOW(), FLOOR(RAND() * 2) + 1, FLOOR(RAND() * 2) + 1, CONCAT('제목_', RAND()), CONCAT('내용_', RAND())
FROM article;
*/

# 게시글 테이블에 hitCount 칼럼 추가
ALTER TABLE article ADD COLUMN hitCount INT(10) UNSIGNED NOT NULL DEFAULT 0;

# 리액션포인트 테이블
CREATE TABLE reactionPoint (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    memberId INT(10) UNSIGNED NOT NULL,
    relTypeCode CHAR(100) NOT NULL COMMENT '관련데이터타입코드',
    relId INT(10) UNSIGNED NOT NULL COMMENT '관련데이터번호',
    `point` SMALLINT(2) NOT NULL
);

# 리액션포인트 테스트 데이터
## 1번 회원이 1번 article 에 대해서 싫어요
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 1,
relTypeCode = 'article',
relId = 1,
`point` = -1;

## 1번 회원이 1번 article 에 대해서 싫어요
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 1,
relTypeCode = 'article',
relId = 2,
`point` = 1;

## 2번 회원이 1번 article 에 대해서 싫어요
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 2,
relTypeCode = 'article',
relId = 1,
`point` = -1;

## 2번 회원이 2번 article 에 대해서 좋어요
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 2,
relTypeCode = 'article',
relId = 2,
`point` = 1;

## 3번 회원이 1번 article 에 대해서 좋어요
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 3,
relTypeCode = 'article',
relId = 1,
`point` = 1;

SELECT * FROM reactionPoint;

# 게시글 정보 가져오기
SELECT a.*,
IFNULL(SUM(rp.point), 0) AS extra_sumReactionPoint,
IFNULL(SUM(IF(rp.point > 0, rp.point, 0)), 0) AS extra_goodReactionPoint,
IFNULL(SUM(IF(rp.point < 0, rp.point, 0)), 0) AS extra_badReactionPoint
FROM (
    SELECT a.*, m.nickname AS extra__writerName
    FROM article AS a
    LEFT JOIN `member` AS m
    ON a.memberId = m.id
) AS a
LEFT JOIN `reactionPoint` AS rp
ON rp.relTypeCode = 'article'
AND a.id = rp.relId
GROUP BY a.id;

# 게시글 테이블에 ReactionPoint 칼럼 추가
ALTER TABLE article ADD COLUMN goodReactionPoint INT(10) UNSIGNED NOT NULL DEFAULT 0;
ALTER TABLE article ADD COLUMN badReactionPoint INT(10) UNSIGNED NOT NULL DEFAULT 0;

# 각 게시물 별, 좋아요, 싫어요 총합
SELECT rp.relTypeCode, relId, SUM(IF(rp.point > 0, rp.point, 0)) AS goodReactionPoint,
SUM(IF(rp.point < 0, rp.point*-1, 0)) AS badReactionPoint
FROM reactionPoint AS rp
GROUP BY rp.relTypeCode, rp.relId;

# 기존 게시물의 좋아요, 싫어요 필드의 값 채우기
UPDATE article AS a
INNER JOIN (
    SELECT rp.relId, SUM(IF(rp.point > 0, rp.point, 0)) AS goodReactionPoint,
    SUM(IF(rp.point < 0, rp.point*-1, 0)) AS badReactionPoint
    FROM reactionPoint AS rp
    WHERE rp.relTypeCode = 'article'
    GROUP BY rp.relTypeCode, rp.relId
) AS rp_sum
ON a.id = rp_sum.relId
SET a.goodReactionPoint = rp_sum.goodReactionPoint,
a.badReactionPoint = rp_sum.badReactionPoint;

SELECT * FROM article;

# 댓글 테이블
CREATE TABLE reply (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    memberId INT(10) UNSIGNED NOT NULL,
    relTypeCode CHAR(100) NOT NULL COMMENT '관련데이터타입코드',
    relId INT(10) UNSIGNED NOT NULL COMMENT '관련데이터번호',
    `body` TEXT NOT NULL
);

# 댓글 테스트 데이터
INSERT INTO reply
SET regDate = NOW(),
updateDate = NOW(),
memberId = 1,
relTypeCode = 'article',
relId = 1,
`body` = '댓글 1';

INSERT INTO reply
SET regDate = NOW(),
updateDate = NOW(),
memberId = 1,
relTypeCode = 'article',
relId = 1,
`body` = '댓글 2';

INSERT INTO reply
SET regDate = NOW(),
updateDate = NOW(),
memberId = 2,
relTypeCode = 'article',
relId = 1,
`body` = '댓글 3';

INSERT INTO reply
SET regDate = NOW(),
updateDate = NOW(),
memberId = 3,
relTypeCode = 'article',
relId = 2,
`body` = '댓글 4';

SELECT * FROM reply;

# 댓글 테이블에 ReactionPoint 칼럼 추가
ALTER TABLE reply ADD COLUMN goodReactionPoint INT(10) UNSIGNED NOT NULL DEFAULT 0;
ALTER TABLE reply ADD COLUMN badReactionPoint INT(10) UNSIGNED NOT NULL DEFAULT 0;

# 댓글 테이블에 인덱스 걸기
ALTER TABLE reply ADD INDEX (`relTypeCode`, `relId`);

# 부가정보테이블
# 댓글 테이블 추가
CREATE TABLE attr (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    `relTypeCode` CHAR(20) NOT NULL,
    `relId` INT(10) UNSIGNED NOT NULL,
    `typeCode` CHAR(30) NOT NULL,
    `type2Code` CHAR(70) NOT NULL,
    `value` TEXT NOT NULL
);

# attr 유니크 인덱스 걸기
## 중복변수 생성금지
## 변수찾는 속도 최적화
ALTER TABLE `attr` ADD UNIQUE INDEX (`relTypeCode`, `relId`, `typeCode`, `type2Code`);

## 특정 조건을 만족하는 회원 또는 게시물(기타 데이터)를 빠르게 찾기 위해서
ALTER TABLE `attr` ADD INDEX (`relTypeCode`, `typeCode`, `type2Code`);

# attr에 만료날짜 추가
ALTER TABLE `attr` ADD COLUMN `expireDate` DATETIME NULL AFTER `value`;

SELECT * FROM attr;

# 회원테이블의 loginId칼럼에 유니크 인덱스
ALTER TABLE `member` ADD UNIQUE INDEX (`loginId`);

# 회원 대량 생성
/*
INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = UUID(),
loginPw = 'user2',
`name` = '사용자2',
nickname = '사용자2',
cellphoneNo = '01011111111',
email = 'tester@test.com';
*/

# loginPw 칼럼의 길이를 100으로 늘림
ALTER TABLE `member` MODIFY COLUMN loginPw VARCHAR(100) NOT NULL;

# 기존 회원의 비밀번호를 암호화 해서 저장
UPDATE `member`
SET loginPw = SHA2(loginPw, 256);

SELECT * FROM `member`;
SELECT * FROM `attr`;

# 신규등록회원은 비번변경할 필요가 없도록 설정
INSERT INTO attr (
    regDate,
	updateDate,
	relTypeCode,
	relId,
	typeCode,
	type2Code,
	`value`,
	expireDate
)
SELECT NOW(), NOW(), 'member', id, 'extra', 'isExpiredPassword', 0, NULL
FROM `member`;

# 댓글 정보 가져오기
SELECT r.*,
IFNULL(SUM(IF(rp.point > 0, rp.point, 0)), 0) AS extra_goodReactionPoint,
IFNULL(SUM(IF(rp.point < 0, rp.point, 0)), 0) AS extra_badReactionPoint
FROM (
    SELECT r.*, m.name AS extra__writerName
    FROM reply AS r
    LEFT JOIN `member` AS m
    ON r.memberId = m.id
) AS r
LEFT JOIN `reactionPoint` AS rp
ON rp.relTypeCode = 'reply'
AND r.id = rp.relId
GROUP BY r.id; 

# 리액션포인트 테스트 데이터(댓글)
## 1번 회원이 1번 reply 에 대해서 싫어요
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 1,
relTypeCode = 'reply',
relId = 1,
`point` = -1;

## 2번 회원이 1번 reply 에 대해서 싫어요
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 2,
relTypeCode = 'reply',
relId = 1,
`point` = -1;

## 2번 회원이 2번 reply 에 대해서 좋어요
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 2,
relTypeCode = 'reply',
relId = 2,
`point` = 1;

## 3번 회원이 1번 reply 에 대해서 좋어요
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 3,
relTypeCode = 'reply',
relId = 1,
`point` = 1;

# 기존 댓글의 좋아요, 싫어요 필드의 값 채우기
UPDATE reply AS r
INNER JOIN (
    SELECT rp.relId, SUM(IF(rp.point > 0, rp.point, 0)) AS goodReactionPoint,
    SUM(IF(rp.point < 0, rp.point*-1, 0)) AS badReactionPoint
    FROM reactionPoint AS rp
    WHERE rp.relTypeCode = 'reply'
    GROUP BY rp.relTypeCode, rp.relId
) AS rp_sum
ON r.id = rp_sum.relId
SET r.goodReactionPoint = rp_sum.goodReactionPoint,
r.badReactionPoint = rp_sum.badReactionPoint;

# 댓글 정보 가져오기
SELECT r.*,
IFNULL(SUM(IF(rp.point > 0, rp.point, 0)), 0) AS extra_goodReactionPoint,
IFNULL(SUM(IF(rp.point < 0, rp.point, 0)), 0) AS extra_badReactionPoint
FROM (
    SELECT r.*, m.nickname AS extra__writerName
    FROM reply AS r
    LEFT JOIN `member` AS m
    ON r.memberId = m.id
) AS r
LEFT JOIN `reactionPoint` AS rp
ON rp.relTypeCode = 'reply'
AND r.id = rp.relId
GROUP BY r.id;

SELECT * FROM reactionPoint;
SELECT * FROM reply;
SELECT * FROM article;

SELECT r.*,
IFNULL(SUM(IF(rp.point > 0, rp.point, 0)), 0) AS extra_goodReactionPoint,
IFNULL(SUM(IF(rp.point < 0, rp.point, 0)), 0) AS extra_badReactionPoint
FROM (
    SELECT r.*, m.name AS extra__writerName
    FROM reply AS r
    LEFT JOIN `member` AS m
    ON r.memberId = m.id
) AS r
LEFT JOIN `reactionPoint` AS rp
ON rp.relTypeCode = 'article'
WHERE r.relId = 1
GROUP BY r.id;