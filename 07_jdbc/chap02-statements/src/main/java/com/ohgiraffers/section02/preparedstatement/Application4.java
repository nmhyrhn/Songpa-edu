package com.ohgiraffers.section02.preparedstatement;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Application4 {
    public static void main(String[] args) {

        /* XML 파일에 별도로 입력 한 sql문을 통해 처리*/

        //Connection 선언

        //PreparedStatement 선언

        //ResultSet 선언

        //EmployeeDTO 선언

        //List<EmployeeDTO> 선

        //사용자에게 성 입력받기

        //Properties 객체 생성하고 파일 읽어오기
        Properties prop =  new Properties();

        try(FileInputStream queryStream = new FileInputStream("src/main/java/com/ohgiraffers/section02/preparedstatement/employee-query.xml")) {

            prop.loadFromXML(queryStream);

            String query = prop.getProperty("selectEmpByFamilyName");
            System.out.println("query: " + query);

            //PreparedStatment 객체 생성

            //위치홀더 값 설정

            //쿼리 실행하여 ResultSet으로 반환받기

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } //finally 구문 작성

        //List에 담긴 직원 목록 출력하기


    }
}
