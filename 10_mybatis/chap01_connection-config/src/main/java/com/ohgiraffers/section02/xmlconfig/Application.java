package com.ohgiraffers.section02.xmlconfig;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class Application {
    public static void main(String[] args) {

        String resource = "mybatis-config.xml";

        try {
            //Resources.getResourceAsStream: classpath에서 XML파일을 InputStream으로 읽기
            InputStream inputStream = Resources.getResourceAsStream(resource);

            SqlSessionFactory sqlSessionFactory =
                    new SqlSessionFactoryBuilder().build(inputStream);

            SqlSession session = sqlSessionFactory.openSession(false);

            //Mapper 인터페이스 없이 쿼리 문자열로 직접 실행("namespace.id")
            java.util.Date date = session.selectOne("mapper.selectSysdate");

            System.out.println("현재 날짜: " + date);
            session.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
