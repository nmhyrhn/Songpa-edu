/* DML(Data Mainpulation Language) */

-- INSERT, UPDATE, DELETE

# INSERT
-- 새로운 행 추가(테이블의 행의 수가 증가)

-- 테이블 컬럼 순서에 맞춰 모든 값을 순서대로 제공
INSERT INTO tbl_menu VALUES(null, '바나나해장국', 8500, 4, 'Y');

SELECT * FROM tbl_menu;

-- 특정 컬럼에만 값 추가
INSERT INTO 
	tbl_menu(menu_name, menu_price, category_code, orderable_status)
VALUES
	('초콜릿죽', 6500, 7, 'N');
    
-- 한번에 여러 행 추가
INSERT INTO
	tbl_menu
VALUES
	(NULL, '초코맛아이스크림', 1700, 12, 'Y'),
    (NULL, '딸기맛아이스크림', 1500, 11, 'N'),
    (NULL, '바닐라맛아이스크림', 1200, 8, 'Y');

-- UPDATE
-- 테이블에 이미 존재하는 행의 컬럼의 값을 수정

SELECT
	menu_code,
    category_code
FROM
	tbl_menu
WHERE
	menu_name = '바나나해장국';
    
UPDATE tbl_menu
SET
	category_code = 7	-- 바꿀 내용
WHERE
	menu_code = 22;	-- 바꿀 대상
    
SELECT * FROM tbl_menu;

# DELETE
-- 테이블에서 특정 행을 삭제하는 구문
SELECT * FROM tbl_menu WHERE menu_code = 22;

DELETE FROM
	tbl_menu
WHERE
	menu_code = 22;
    
# REPLACE
-- REPLACE를 통해 중복 된 데이터를 덮어 쓸 수 있다

-- PRAMARY KEY 중복 에러 발생
INSERT INTO tbl_menu VALUES(17, '참기름소주', 5000, 10, 'Y');

-- 해당 키의 데이터가 있으면, 기존 데이터를 삭제하고 새로운 데이터로 INSERT 한다
REPLACE INTO tbl_menu VALUES(17, '참기름소주', 5000, 10, 'Y');
    











