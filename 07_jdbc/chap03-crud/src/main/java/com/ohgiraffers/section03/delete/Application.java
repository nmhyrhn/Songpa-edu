package com.ohgiraffers.section03.delete;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplates.close;
import static com.ohgiraffers.common.JDBCTemplates.getConnection;

public class Application {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.print("삭제할 메뉴 번호를 입력하세요: ");
        int menuCode = sc.nextInt();
        sc.nextLine();

        Connection con = getConnection();
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        int result = 0;

        Properties prop = new Properties();

        try (FileInputStream queryStream = new FileInputStream("src/main/java/com/ohgiraffers/mapper/menu-query.xml")) {

            prop.loadFromXML(queryStream);


            String selectQuery = prop.getProperty("selectMenuByCode");
            pstmt = con.prepareStatement(selectQuery);
            pstmt.setInt(1, menuCode);

            rset = pstmt.executeQuery();

            if (rset.next()) {
                String menuName = rset.getString("MENU_NAME");

                System.out.print("조회된 메뉴는 [" + menuName + "]입니다. 정말 삭제하시겠습니까? (Y/N): ");
                String answer = sc.nextLine().toUpperCase();

                if ("Y".equals(answer)) {

                    close(pstmt);

                    String deleteQuery = prop.getProperty("deleteMenu");
                    pstmt = con.prepareStatement(deleteQuery);
                    pstmt.setInt(1, menuCode);

                    result = pstmt.executeUpdate();

                    if (result > 0) {
                        System.out.println("메뉴 삭제 성공!");
                    } else {
                        System.out.println("메뉴 삭제 실패!");
                    }

                } else {
                    System.out.println("삭제가 취소!");
                }

            } else {
                System.out.println("해당 번호의 메뉴가 존재하지 않습니다.");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(pstmt);
            close(con);
        }
    }
}