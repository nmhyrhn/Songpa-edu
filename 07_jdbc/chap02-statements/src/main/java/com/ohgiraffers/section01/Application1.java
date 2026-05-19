package com.ohgiraffers.section01;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.ohgiraffers.common.JDBCTemplates.close;
import static com.ohgiraffers.common.JDBCTemplates.getConnection;

public class Application1 {
    public static void main(String[] args) {

        //1. 컨넥션 객체 생성
        Connection con = getConnection();

        // 쿼리를 운반하고 결과를 반환하는 객체
        Statement stmt = null;

        // select 결과집합을 받아 올 용도의 객체
        ResultSet rset = null;

        try {
            //connection을 이용하여 statement(쿼리를 싣고 다닐 객체) 생성
            stmt = con.createStatement();

            rset = stmt.executeQuery("SELECT MENU_NAME, MENU_PRICE FROM TBL_MENU");

            while(rset.next()){
                //next(): ResultSet의 커서가 다음 행을 보며 행이 존재하면 true, 존재하지 않으면 false 반환
                //getXXX(컬럼명): 현재 행의 컬럼을 XXX 데이터 타입으로 반환
                System.out.println(rset.getString("MENU_NAME") + ", " + rset.getInt("MENU_PRICE"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            /*맨 마지막에 열렸던 애부터 순서대로 닫아줌*/
            close(rset);
            close(stmt);
            close(con);
        }

    }
}
