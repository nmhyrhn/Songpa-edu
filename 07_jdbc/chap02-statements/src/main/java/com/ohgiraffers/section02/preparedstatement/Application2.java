package com.ohgiraffers.section02.preparedstatement;

import java.sql.*;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplates.close;
import static com.ohgiraffers.common.JDBCTemplates.getConnection;

public class Application2 {
    public static void main(String[] args) {

        Connection con = getConnection();

        PreparedStatement pstmt = null;

        ResultSet rset = null;

        Scanner sc = new Scanner(System.in);
        System.out.print("조회할 메뉴 번호를 입력: ");
        String menuCode = sc.nextLine();

        /* ? : 위치 홀더*/
        String query = "SELECT MENU_NAME FROM TBL_MENU WHERE MENU_CODE=?";

        try {
            //PrepsredStatemnet 객체 생성 시 수행할 sql 구문을 인자로 전달하며 생성
            pstmt = con.prepareStatement(query);
            /*sql문의 위치홀더를 설정*/
            pstmt.setString(1, menuCode);

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
