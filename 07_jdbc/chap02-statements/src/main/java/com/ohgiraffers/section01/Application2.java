package com.ohgiraffers.section01;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplates.close;
import static com.ohgiraffers.common.JDBCTemplates.getConnection;

public class Application2 {
    public static void main(String[] args) {

        Connection con = getConnection();

        Statement stmt = null;

        ResultSet rset  = null;

        try {
            stmt = con.createStatement();

            Scanner sc = new Scanner(System.in);
            System.out.print("메뉴 번호를 입력하세요: ");

            String menuCode = sc.nextLine();
            String query = "SELECT MENU_NAME, MENU_PRICE FROM TBL_MENU WHERE MENU_CODE = '" + menuCode + "'";

            System.out.println("query: " + query);

            rset = stmt.executeQuery(query);

            if(rset.next()){
                System.out.println(rset.getString("MENU_NAME") + ", " + rset.getString("MENU_PRICE"));
            } else {
                System.out.println("해당 메뉴의 조회 결과가 없습니다.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(stmt);
            close(con);
        }

    }
}
