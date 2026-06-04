package com.ohgiraffers.section01;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class Template {

    /*
    * SqlSessionFactory: 애플리케이션 실행 동안 딱 하나만 존재해야 한다 -> 싱글톤 패턴
    * static 필드로 선언 -> 클래스 로딩 시 메모리에 올라가 앱 종료까지 유지
    * */

    private static SqlSessionFactory sqlSessionFactory;

    public static SqlSession getSqlSession() {

        if(sqlSessionFactory == null) {
            String resource = "mybatis-config.xml";

            try {
                InputStream inputStream = Resources.getResourceAsStream(resource);

                sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        SqlSession sqlSession = sqlSessionFactory.openSession(false);

        System.out.println("sqlSessionFactory: " + sqlSessionFactory.hashCode());
        System.out.println("sqlSession: " + sqlSession.hashCode());
        System.out.println();

        return sqlSession;


    }

}
