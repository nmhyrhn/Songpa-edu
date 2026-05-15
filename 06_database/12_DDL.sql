# DDL(Data Definition Language)
-- 데이터 정의 언어 (CREATE, ALTER, DROP)

-- 테이블 생성을 위한 구문
/* 
CREATE TABLE 테이블명 (
	컬럼명 데이터타입(길이) [제약조건],
    컬럼명 데이터타입(길이) [제약조건],
    ...
*/

CREATE TABLE IF NOT EXISTS tb1(
	pk INT PRIMARY KEY,
    fk INT,
    col1 VARCHAR(255),
    CHECK(col1 IN ('Y', 'N'))
)ENGINE=INNODB;

DESC tb1;

SELECT * FROM tb1;

INSERT INTO tb1 VALUES(1, 10, 'Y');

-- AUTO_INCREMENT
-- INSERT시 PRIMARY KEY에 해당하는 컬럼에 자동으로 번호를 발생시켜 저장할 수 있다.
CREATE TABLE IF NOT EXISTS tb2(
	pk INT AUTO_INCREMENT PRIMARY KEY,
    fk INT,
    col1 VARCHAR(255),
    CHECK(col1 IN ('Y', 'N'))
)ENGINE=INNODB;

INSERT INTO tb2 VALUES(null, 10, 'Y');
INSERT INTO tb2 VALUES(null, 20, 'Y');

SELECT * FROM tb2;

# ALTER
-- 테이블에 추가/변경/삭제하는 모든것은 ALTER 명령어를 사용

-- 컬럼 추가(ADD)
ALTER TABLE tb2
ADD col2 INT NOT NULL;

DESC tb2;

-- 컬럼 삭제(DROP COLUMN)
ALTER TABLE tb2
DROP COLUMN col2;

-- CHANGE
-- fk 컬럼 이름과 데이터형식, 제약조건 바꾸기
ALTER TABLE tb2
CHANGE COLUMN fk change_fk INT NOT NULL;

-- MODIFY : 컬럼의 정의(속성) 변경 (컬럼의 이름은 그대로 두고 속성만 바꿀 때 사용)
ALTER TABLE tb2
MODIFY pk INT;

-- PRIMARY KEY에 auto-increament가 걸려있으면 삭제가 불가능하기 때문에 
-- MODIFY로 속성을 바꾼 뒤 삭제 가능하다
ALTER TABLE tb2
DROP PRIMARY KEY;

-- 제약조건 추가
ALTER TABLE tb2 ADD PRIMARY KEY(pk);

# DROP
-- 테이블의 구조와 데이터를 영구적으로 삭제
CREATE TABLE IF NOT EXISTS tb3(
	pk INT AUTO_INCREMENT PRIMARY KEY,
    fk INT,
    col1 VARCHAR(255),
    CHECK(col1 IN ('Y', 'N'))
)ENGINE=INNODB;

DROP TABLE IF EXISTS tb3;	-- 콤마를 이용하여 여러 테이블도 삭제 가능

# TRUNCATE
-- 테이블 내용물만 비우고 초기화하기(구조는 남겨두고, 데이터만 깨끗하게 비우고 싶을 때 사용)

CREATE TABLE IF NOT EXISTS tb4(
	pk INT AUTO_INCREMENT PRIMARY KEY,
    fk INT,
    col1 VARCHAR(255),
    CHECK(col1 IN ('Y', 'N'))
)ENGINE=INNODB;

INSERT INTO tb4 VALUES(NULL, 10, 'Y'),(NULL, 20, 'N');

SELECT * FROM tb4;

TRUNCATE TABLE tb4;

INSERT INTO tb4 VALUES(NULL, 10, 'Y'),(NULL, 20, 'N');





















