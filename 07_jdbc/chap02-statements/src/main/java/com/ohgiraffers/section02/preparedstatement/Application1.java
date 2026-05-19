package com.ohgiraffers.section02.preparedstatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.ohgiraffers.common.JDBCTemplates.close;
import static com.ohgiraffers.common.JDBCTemplates.getConnection;

public class Application1 {
    public static void main(String[] args) {

        Connection con = getConnection();

        PreparedStatement pstmt = null;

        ResultSet rset = null;

        try {
            //PrepsredStatemnet 객체 생성 시 수행할 sql 구문을 인자로 전달하며 생성
            pstmt = con.prepareStatement("SELECT MENU_NAME FROM TBL_MENU");

            rset = pstmt.executeQuery();

            while (rset.next()) {
                System.out.println(rset.getString("MENU_NAME"));
            }



        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(pstmt);
            close(con);
        }

    }
}
