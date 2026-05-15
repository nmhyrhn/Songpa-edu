# SUBQUERIES(서브쿼리)

-- 민트미역국의 카테고리와 같은 다른 메뉴들을 조회

-- 민트미역국의 카테고리 코드를 알아낸다
-- 서브쿼리
SELECT
	category_code
FROM
	tbl_menu
WHERE
	menu_name='민트미역국';
    
-- 메인쿼리
-- 민트미역국과 같은 카테고리의 메뉴 조회
SELECT
	menu_name,
    category_code
FROM
	tbl_menu
WHERE
	category_code = (SELECT
						category_code
					FROM
						tbl_menu
					WHERE
						menu_name='민트미역국');
                        
-- FROM 절에 서브쿼리 사용
-- 즉석에서 만들어 쓰는 임시 테이블처럼 동작 -> 파생 테이블 이라고도 부름

-- 가장 많은 메뉴가 포함된 카테고리는 메뉴를 총 몇개 가지고 있을까??

-- (서브쿼리): 카테고리별로 메뉴가 몇 개씩 있는지?
SELECT
	COUNT(*) AS 'count'
FROM
	tbl_menu
GROUP BY
	category_code;
    
-- 가장 많은 메뉴가 포함된 카테고리의 메뉴 수
SELECT
	MAX(count) AS '최대 메뉴 수'
FROM
	(SELECT
		COUNT(*) AS 'count'
	FROM
		tbl_menu
	GROUP BY
		category_code) AS count_table;	-- 파생 테이블은 반드시 별칭이 있어야 한다
        
-- 상관 서브쿼리(심화) : 안쪽 쿼리가 바깥쪽 행의 값을 참고
-- 각 카테고리별 평균 가격보다 높은 가격의 메뉴 조회

SELECT
	AVG(menu_price)
FROM
	tbl_menu
WHERE
	category_code = 4;
    
SELECT
	menu_code,
    menu_name,
    menu_price,
    category_code
FROM
	tbl_menu a
WHERE
	menu_price > (SELECT
						AVG(menu_price)
					FROM
						tbl_menu
					WHERE
						-- a.category_code: 바깥쪽 쿼리에서 현재 보고 있는 메뉴의 카테고리 코드
						category_code = a.category_code);	

    
    
    
    







