# Transaction - ALL or Nothing 모두 성공하거나, 모두 실패해야 하는 하나의 작업 묶음

SET autocommit = OFF; -- 설정 자체를 바꾼다

-- 지금부터 트랜잭션을 시작하겠다. (수동 커밋 모드로 전환)
START TRANSACTION;

SELECT * FROM tbl_menu;

-- 임시 작업
INSERT INTO tbl_menu VALUES(null, '추가된 메뉴', 9000, 4, 'Y');
UPDATE tbl_menu SET menu_name='수정된 메뉴' WHERE menu_code= 24;
DELETE FROM tbl_menu WHERE menu_code = 26;

-- START TRANSACTION 이후에 수행했던 모든 작업을 '취소'하고 트랜잭션 시작 전의 상태로 되돌림
ROLLBACK;	-- COMMIT 이후에는 소용이 없음

-- 작업이 올바르다고 판단되면 최종 저장을 명령
COMMIT;

SELECT * FROM tbl_menu;







