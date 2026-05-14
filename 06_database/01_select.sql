# SELECT
-- SELECT 컬럼명 FROM 테이블명;
-- 특정 테이블에서 원하는 데이터를 조회하는데 사용

-- 단일 열(컬럼) 데이터 검색
SELECT menu_name FROM tbl_menu;

-- 여러 열의 데이터 검색
SELECT
	menu_code,
    menu_name,
    menu_price
FROM
	tbl_menu;
    
-- 모든 열에서 데이터 검색 (*: 모든 컬럼을 의미하는 와일드 카드)
SELECT * FROM tbl_menu;

-- FROM 절 없이 간단한 연산
SELECT 6 + 3;
SELECT 6 * 3;

-- 내장 함수 사용
SELECT NOW(); -- 현재 날짜와 시간을 보여SELECT CONCAT('홍', '길동'); -- 여러 문자열을 합쳐준다.

-- 컬럼 별칭(Alias) 사용
SELECT CONCAT('홍', '길동') AS name;
SELECT CONCAT('홍', '길동') AS 'Full name';