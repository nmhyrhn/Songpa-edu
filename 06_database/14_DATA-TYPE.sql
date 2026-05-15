# DATA TYPE

-- 형변환(실수 -> 정수)
-- CAST() :  값을 특정 데이터 타입으로 바꾸는 함수, 원본 데이터를 수정하는 것은 아님
-- CAST(값 AS 바꿀타입)
-- CONVERT(값, 바꿀타입)

SELECT 	
	CAST(AVG(menu_price) AS SIGNED INTEGER)
FROM
	tbl_menu;
    
SELECT 	
	CONVERT(AVG(menu_price), SIGNED INTEGER)
FROM
	tbl_menu;
    
SELECT CAST('2026-05-15' AS DATE);


    






