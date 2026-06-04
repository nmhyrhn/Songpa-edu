package com.ohgiraffers.section01;

import org.apache.ibatis.session.SqlSession;

import static com.ohgiraffers.section01.Template.getSqlSession;

public class Application {
    public static void main(String[] args) {

        printAndCloseSession();
        printAndCloseSession();
        printAndCloseSession();
        printAndCloseSession();
        printAndCloseSession();


    }


    private static void printAndCloseSession(){
        SqlSession sqlSession = getSqlSession();

        try {
            System.out.println(sqlSession);
        } finally {
            sqlSession.close();
        }
    }
}
