# GROUP BY
-- 지정된 컬럼의 값이 같은 데이터들을 하나의 그룹으로 묶는다

SELECT
	category_code
FROM
	tbl_menu
GROUP BYttttt
	category_code;
    
-- COUNT() : 각 그룹에 속한 행의 개수를 센다.
SELECT
	category_code,
    COUNT(*) AS '메뉴 개수'
FROM
	tbl_menu
GROUP BY
	category_code;
    
SELECT
	category_code,
    -- menu_code, GROUP BY를 사용할때, GROUP BY에 사용된 컬럼과 집계 함수만 SELECT 절에 올 수 있다.
    SUM(menu_price) AS '가격 총합',
    AVG(menu_price) AS '가격 평균'
FROM
	tbl_menu
GROUP BY
	category_code;
    
-- HAVING (그룹에 대한 조건 필터링) - WHERE는 그룹화 하기 전, 원본 데이터에 대한 조건
-- 카테고리별 메뉴 개수가 3개 이상인 카테고리만 보여주세요~
SELECT
	category_code,
    COUNT(*)
FROM
	tbl_menu
group by
	category_code
HAVING
	COUNT(*) >= 3;	-- 그룹화된 결과가 3개 이상인 그룹만 필터링
    
-- 작성 순서: FROM -> WHERE -> GROUP BY -> HAVING -> ORDER BY

-- ROLLUP(그룹별 집계 결과와 함께 그 그룹들의 총계를 보여준다)
SELECT
	category_code,
    SUM(menu_price)
FROM
	tbl_menu
GROUP BY
	category_code
WITH ROLLUP;

-- 가격대별, 그 안에서 카테고리별로 그룹화 하고 ROLLUP 적용
SELECT
	menu_price,
    category_code,
    SUM(menu_price)
FROM
	tbl_menu
GROUP BY
	menu_price,
    category_code
WITH ROLLUP;

    
    
    
    
    
