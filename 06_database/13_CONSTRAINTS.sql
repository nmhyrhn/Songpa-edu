# CONSTRAINTS(제약조건)

# NOT NULL
-- NULL값 허용하지 않음

CREATE TABLE IF NOT EXISTS user_notnull(
	user_no INT NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    user_pwd VARCHAR(255) NOT NULL,
    gender VARCHAR(3)
)ENGINE=INNODB;

INSERT INTO user_notnull VALUES(1, 'user01', 'pass01', '남');

SELECT * FROM user_notnull;

INSERT INTO user_notnull VALUES(2, 'user02', NULL, '남');

# UNIQUE
-- 중복값 허용하지 않음
CREATE TABLE IF NOT EXISTS user_unique(
	user_no INT NOT NULL UNIQUE,	-- 회원번호는 중복될 수 없다
    user_id VARCHAR(255) NOT NULL,
    user_pwd VARCHAR(255) NOT NULL,
    gender VARCHAR(3),
    UNIQUE(user_id)	-- id도 중복될 수 없다.(테이블 레벨 선언 방식)
)ENGINE=INNODB;

INSERT INTO user_unique VALUES(1, 'user01', 'pass01', '남');
INSERT INTO user_unique VALUES(1, 'user02', 'pass02', '여');		-- UNIQUE 위반!

INSERT INTO user_unique VALUES(2, 'user01', 'pass01', '남');
INSERT INTO user_unique VALUES(3, 'user01', 'pass01', '남');

# PRIMARY KEY(기본 키)
-- 테이블에서 한 행의 정보를 찾기 위해 사용 할 컬럼을 의미
-- 테이블에 대한 식별자 역할을 함(한 행씩 구분하는 역할)
-- NOT NULL + UNIQUE 제약조건의 의미
CREATE TABLE IF NOT EXISTS user_primarykey(
	user_no INT PRIMARY KEY,	-- 이 컬럼이 이 테이블의 대표 식별자
    user_id VARCHAR(255) NOT NULL,
    user_pwd VARCHAR(255) NOT NULL,
    gender VARCHAR(3)
)ENGINE=INNODB;

-- NULL 시도
INSERT INTO user_primarykey VALUES(NULL, 'user01', 'pass01', '남');

-- 중복 값 시도
INSERT INTO user_primarykey VALUES(1, 'user01', 'pass01', '남');
INSERT INTO user_primarykey VALUES(1, 'user02', 'pass02', '여');	

# FORIEGN KEY
-- 두 테이블을 연결하고 관계를 맺어준다.
-- 참조된 다른 테이블에서 제공하는 값만 사용할 수 있다.
-- 제공되는 값 외에는 NULL을 사용할 수 없다.
CREATE TABLE IF NOT EXISTS user_grade (
	grade_code INT PRIMARY KEY,
    grade_name VARCHAR(255) NOT NULL
)ENGINE=INNODB;

INSERT INTO user_grade VALUES(10, '일반회원'), (20, '우수회원'), (30, '특별회원');

SELECT * FROM user_grade;

CREATE TABLE IF NOT EXISTS user_foreignkey1 (
	user_no INT PRIMARY KEY,
    grade_code INT,
    -- 이 테이블의 grade_code는 user_grade 테이블의 grade_code를 참조한다.
    -- 회원의 grade_code는 반드시 user_grade 테이블에 존재하는 grade_code중 하나여야만 한다!!!
    FOREIGN KEY(grade_code) REFERENCES user_grade(grade_code)
)ENGINE=INNODB;

INSERT INTO user_foreignkey1 VALUES(1, 10);

-- 참조 컬럼에 없는 값을 적용하면 에러 발생
INSERT INTO user_foreignkey1 VALUES(2, 50);

DESC user_foreignkey1;
DESC user_grade;

# ON UPDATE / ON DELETE 옵션
-- SET NULL : 부모(user_grade)가 바뀌거나 사라지면, 해당 값을 NULL로 바꾼다.
-- CASCADE : 부모가 바뀌면 자식도 따라 바뀌고, 부모가 사라지면 자식도 함께 사라진다.

CREATE TABLE IF NOT EXISTS user_foreignkey2 (
	user_no INT PRIMARY KEY,
    grade_code INT,
    -- 이 테이블의 grade_code는 user_grade 테이블의 grade_code를 참조한다.
    -- 회원의 grade_code는 반드시 user_grade 테이블에 존재하는 grade_code중 하나여야만 한다!!!
    FOREIGN KEY(grade_code) REFERENCES user_grade(grade_code)
    ON UPDATE SET NULL
    ON DELETE SET NULL
)ENGINE=INNODB;

INSERT INTO user_foreignkey2 VALUES(1, 10), (2, 20), (3, 30);
SELECT * FROM user_foreignkey2;

DROP TABLE IF EXISTS user_foreignkey1;

-- 부모테이블의 grade_code 수정
UPDATE user_grade
SET grade_code = 40
WHERE grade_code = 10;

SELECT * FROM user_foreignkey2;

# CHECK
-- 들어올 수 있는 값의 범위나 조건을 직접 지정
CREATE TABLE IF NOT EXISTS user_check(
	user_no INT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(255) NOT NULL,
    gender VARCHAR(3) CHECK(gender IN ('남', '여')),
    age INT CHECK(age >= 19)
)ENGINE=InnoDB;

INSERT INTO user_check VALUES(null, '홍길동', '남', 25);

SELECT * FROM user_check;

INSERT INTO user_check VALUES(null, '홍길동', '남자', 25);
INSERT INTO user_check VALUES(null, '홍길동', '남', 10);

# DEFAULT
-- INSERT 시 특정 컬럼에 값을 주지 않으면, 자동으로 채워질 기본값을 정한다.
CREATE TABLE IF NOT EXISTS user_default(
	user_no INT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(255) DEFAULT '아무개',
    add_day DATE DEFAULT(current_date)
)ENGINE=InnoDB;

INSERT INTO user_default VALUES(null, default, default);

SELECT * FROM user_default;




















