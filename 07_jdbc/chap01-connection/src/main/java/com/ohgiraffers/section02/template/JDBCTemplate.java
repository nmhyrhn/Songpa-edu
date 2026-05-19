package com.ohgiraffers.section02.template;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCTemplate {


    public static Connection getConnection(){

        Connection con = null;

        Properties prop = new Properties();

        try(FileReader reader = new FileReader("src/main/java/com/ohgiraffers/config/connection-info.properties")) {
            prop.load(reader);

            System.out.println(prop);

            String driver = prop.getProperty("driver");
            String url = prop.getProperty("url");

            Class.forName(driver);

            /*user, password가 들어있는 Properties 객체를 그대로 전달할 수 있다*/
            con = DriverManager.getConnection(url, prop);

            System.out.println(con);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*Connection을 사용하는 쪽으로 반환해야 하므로 여기서 close 해주지 않음 (Finally 미사용)*/
        return con;

    }

    public static void close(Connection con) {
        try {
            if(con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
