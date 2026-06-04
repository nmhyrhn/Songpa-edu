package com.ohgiraffers.section01.javaconfig;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

public class Application {

    private static String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static String URL = "jdbc:mysql://localhost/menudb";
    private static String USER = "ohgiraffers";
    private static String PASSWORD = "ohgiraffers";


    public static void main(String[] args) {


        /* 1. 환경(Environment) 설정*/
        /*
        * 트랜잭션 매니저 선택:
        *   JdbcTransactionFactory() -> 개발자가 commit/rollback을 직접 호출
        *   ManagedTransactionFactory -> WAS나 Spring 같은 외부 환경이 트랜잭션 관리
        *
        * DataSource 선택:
        *   PooledDataSource -> 커넥션 풀(Pool)사용 - 실무 표준
        *   UnpooledDataSource -> 매번 새 연결 생성 - 테스트용
        *
        * */
        Environment environment = new Environment(
                "dev", //환경 ID
                new JdbcTransactionFactory(), //트랜잭션 매니저
                new PooledDataSource(DRIVER, URL, USER, PASSWORD) //DB 연결 정보 + 커넥션 풀
        );

        /* 2. Configuration 생성 + Mapper 등록*/
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(Mapper.class); //이 인터페이스를 MyBatis가 관리하게 등록한다.


        /* 3. SqlSessionFactory 생성*/
        //SqlSessionFactory: SqlSession을 찍어내는 공장. 애플리케이션 실행 중 딱 하나만 만든다
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);

        /* 4. SqlSession 열기*/
        //fals: auto-commit 비활성화 -> 직접 commit() 또는 rollback() 호출
        //true: auto-commit 활성화 -> 쿼리 싱핼 즉시 자동 반영
        SqlSession sqlSession = sqlSessionFactory.openSession(false);

        /* 5. Mapper 가져와서 쿼리 실행*/
        //getMapper(0: Configuration에 등록된 Mapper 인터페이스의 구현 객체를 반환
        //MyBatis가 내부적으로 동적 프록시로 구현체를 생성
        Mapper mapper = sqlSession.getMapper(Mapper.class);
        java.util.Date date = mapper.selectSysdate();

        System.out.println("DB 서버 현재 날자: " + date);

        sqlSession.close();



    }
}
