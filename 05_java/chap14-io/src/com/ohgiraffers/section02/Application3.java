package com.ohgiraffers.section02;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Application3 {
    public static void main(String[] args) {

        /*FileReader: char 단위로 읽기
        * 단, 기본 문자셋을 사용하므로 파일 인코딩과 실행 환경 문자셋이 다르면 깨질 수 있다.*/

        try(FileReader fr = new FileReader("src/com/ohgiraffers/section02/testReader.txt")) {

            int value;
            while((value = fr.read()) != -1) {
                System.out.println((char)value);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
