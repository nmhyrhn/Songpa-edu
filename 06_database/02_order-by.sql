# ORDER BY 기준컬럼
-- SELECT문의 가장 마지막에 위치하며, 결과 집합을 하나의 열로 정렬

-- 오름차순 정렬
SELECT 
	menu_code,
    menu_name,
    menu_price
FROM
	tbl_menu
ORDER BY 
	menu_price ASC; -- ASC는 오름차순을 의미(생략가능)
    
-- 내림차순 정렬
SELECT 
	menu_code,
    menu_name,
    menu_price
FROM
	tbl_menu
ORDER BY 
	menu_price DESC,  -- DESC는 내림차순을 의미 (생략 불가능)
    menu_name ASC; -- 2차 기준: 가격이 같다면, 이름 오름차순으로 
    
-- 연산 결과로 정렬(보통 별칭을 사용)
SELECT
	menu_code,
    menu_price,
    menu_code * menu_price AS calculated_value
FROM
	tbl_menu
ORDER BY
	calculated_value DESC;

-- FIELD(컬럼, 첫번째, 두번째, ...): 컬럼 값이 목록의 몇 번째에 있는지 숫자로 알려준다
SELECT FIELD('B', 'A', 'B', 'C');

-- 'N'을 1순위, 'Y'를 2순위로 정렬
SELECT
	menu_name,
    orderable_status
FROM
	tbl_menu
ORDER BY
	FIELD(orderable_status, 'N', 'Y');
    
-- null 값이 있는 컬럼에 대한 정렬
SELECT
	category_code,
    category_name,
    ref_category_code
FROM
	tbl_category
ORDER BY
	-- ref_category_code IS NULL; -- NULL이면 true(1), 아니면 flase(0) 반환
    ref_category_code IS NULL DESC;
    


    
    
    

