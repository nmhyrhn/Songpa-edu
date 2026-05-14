# LIMIT
-- 결과중에 딱 내가 원하는 만큼만 잘라서 줘! 라고 요청하는 키워드

-- LIMIT [개수]
SELECT
	menu_name,
    menu_price
FROM
	tbl_menu
ORDER BY
	menu_price DESC 
LIMIT  5;

-- LIMIT [시작 위치(건너뛸 개수)], [개수] (게시판 페이징 핵심 원리)
-- 2번째 행부터 4개의 행을 가져오기
SELECT 
	menu_code,
    menu_name,
    menu_price
FROM
	tbl_menu
ORDER BY
	menu_price DESC
LIMIT 1, 4;
