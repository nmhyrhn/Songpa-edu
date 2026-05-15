# INDEX

-- 데이터를 더 빠르게 찾기 위해 사용하는 객체이다
-- 책의 목차처럼, WHERE 조건에 자주 사용되는 컬럼을 빠르게 찾을 수 있게 도와준다
-- 단, 조회는 빨라질 수 있지만, INSERT, UPDATE, DELETE시에는 인덱스도 함께 관리해야 하므로 부담이 생길 수 있음

CREATE TABLE phone (
	phone_code INT PRIMARY KEY,
    phone_name VARCHAR(100) NOT NULL,
    phone_price DECIMAL(10, 2) NOT NULL
);

INSERT INTO phone VALUES(1, 'iPhone 17 Pro', 2000000),
						(2, 'Galaxy S26', 1900000),
                        (3, 'Xiaomi', 1000000);
                        
SELECT * FROM phone;

-- 실행 계획 확인(조회 방식과 사용된 인덱스를 확인할 수 있음)
EXPLAIN
SELECT 
	* 
FROM
	phone
WHERE
	phone_name = 'iPhone 17 Pro';
    
-- 인덱스 생성
CREATE INDEX idx_phone_name
ON phone(phone_name);

-- 인덱스 확인
SHOW INDEX
FROM phone;

-- 인덱스 삭제
DROP INDEX idx_phone_name
ON phone;
    
    








