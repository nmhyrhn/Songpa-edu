package com.ohgiraffers.section02.update;

import com.ohgiraffers.model.dto.MenuDTO;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplates.close;
import static com.ohgiraffers.common.JDBCTemplates.getConnection;

public class Application {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.print("변경할 메뉴 번호를 입력하세요: ");
        int menuCode = sc.nextInt();
        System.out.print("변경할 메뉴의 이름을 입력하세요: ");
        sc.nextLine();
        String menuName = sc.nextLine();
        System.out.print("변경할 메뉴의 가격을 입력하세요: ");
        int menuPrice = sc.nextInt();

        MenuDTO changedMenu = new MenuDTO();
        changedMenu.setMenuCode(menuCode);
        changedMenu.setMenuName(menuName);
        changedMenu.setMenuPrice(menuPrice);

        Connection con = getConnection();

        PreparedStatement pstmt = null;

        int result = 0;

        Properties prop = new Properties();

        try(FileInputStream queryStream = new FileInputStream("src/main/java/com/ohgiraffers/mapper/menu-query.xml")) {

            prop.loadFromXML(queryStream);

            String query = prop.getProperty("updateMenu");

            pstmt = con.prepareStatement(query);

            pstmt.setString(1, menuName);
            pstmt.setInt(2, menuPrice);
            pstmt.setInt(3, menuCode);

            result = pstmt.executeUpdate();



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
            close(con);
        }

        if(result > 0) {
            System.out.println("메뉴 변경 성공!");
        } else {
            System.out.println("메뷰 변경 실패!");
        }


    }
}
