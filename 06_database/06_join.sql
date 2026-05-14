# JOIN

-- 1. 컬럼 별칭
SELECT 
	menu_name AS '메뉴 이름',
    menu_price price
FROM
	tbl_menu;
    
-- 2. 테이블 별칭 (JOIN에서 필수)
SELECT
	a.menu_name,
    a.menu_price
FROM
	tbl_menu AS a;
    
-- INNER JOIN(교집합) - 가장 기본적인 조인, INNER 키워드 생략 가능
SELECT
	a.menu_name,
    b.category_name
FROM
	tbl_menu a
JOIN 
	tbl_category b ON a.category_code = b.category_code;
    
-- OUTER JOIN (LEFT/RIGTH)

-- LEFT : 조인구문을 기준으로 왼쪽 테이블의 데이터는 모두 보여주고,
-- 오른쪽 테이블에서 짝이 맞는 데이터가 없으면 'NULL'로 채워 보여준다.
-- RIGTH : 기준이 오른쪽 테이블이다.
SELECT
	a.category_name,
    b.menu_name
FROM
	tbl_category a
LEFT JOIN tbl_menu b ON a.category_code = b.category_code;

-- SELF JOIN
SELECT
	a.category_name AS '하위 카테고리',
    b.category_name AS '상위 카테고리'
FROM
	tbl_category a -- 하위 역할
JOIN tbl_category b ON a.ref_category_code = b.category_code;	-- 상위 역할













