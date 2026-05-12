package com.ohgiraffers.section01;

import java.io.File;
import java.io.IOException;

public class Application {
    public static void main(String[] args) {

        //File 객체 생성 : 대상 파일이 실제 존재하지 않아도 인스턴스 생성가능
        File file = new File("src/com/ohgiraffers/section01/test.txt");

        try {
            //createNewFile() : 실제로 파일을 디스크에 생성
            boolean createSuccess = file.createNewFile();
            System.out.println("파일 생성 성공: " + createSuccess);

            //파일 메타데이터 조회 (내용은 못 읽음)
            System.out.println("파일 크기: " + file.length() + "byte");
            System.out.println("상대 경로: " + file.getPath());
            System.out.println("절대 경로: " + file.getAbsolutePath());

            boolean deleteSuccess = file.delete(); //파일 삭제 기능
            System.out.println(deleteSuccess);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
