package com.ohgiraffers.model.dao;

import com.ohgiraffers.model.dto.CategoryDTO;
import com.ohgiraffers.model.dto.MenuDTO;

import javax.xml.transform.Result;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.ohgiraffers.common.JDBCTemplates.close;

public class MenuDAO {

    /*DAO(Database Access Object): 데이터베이스 접근용 객체
    * crud 연산을 담당하는 메소드들의 집합으로 이루어진 클래스
    * */

    private Properties prop = new Properties();

    public MenuDAO() {
        try(FileInputStream queryStream = new FileInputStream("src/main/java/com/ohgiraffers/mapper/menu-query.xml")) {

            prop.loadFromXML(queryStream);



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*1. 메뉴 등록에 사용할 하위 카테고리 조회*/
    public List<CategoryDTO> selectAllCategory(Connection con) {

        PreparedStatement pstmt = null;
        ResultSet rset = null;
        List<CategoryDTO> categoryList = new ArrayList<>();

        String query = prop.getProperty("selectAllCategoryList");

        try {
            pstmt = con.prepareStatement(query);
            rset = pstmt.executeQuery();

            while (rset.next()) {
                CategoryDTO categoryDTO = new CategoryDTO();
                categoryDTO.setCategoryCode(rset.getInt("category_code"));
                categoryDTO.setCategoryName(rset.getString("category_name"));
                categoryDTO.setRefCategoryCode(rset.getInt("ref_category_code"));

                categoryList.add(categoryDTO);

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(rset);
            close(pstmt);
        }

        return categoryList;

    }

    /*신규 메뉴 등록*/
    public int insertNewMenu(Connection con, MenuDTO newMenu) {

        PreparedStatement pstmt = null;
        int result = 0;
        String query = prop.getProperty("insertMenu");

        try {
            pstmt = con.prepareStatement(query);

            pstmt.setString(1, newMenu.getMenuName());
            pstmt.setInt(2, newMenu.getMenuPrice());
            pstmt.setInt(3, newMenu.getCategoryCode());
            pstmt.setString(4, newMenu.getOrderableStatus());

            result = pstmt.executeUpdate();



        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
        }
        return result;

    }


}
