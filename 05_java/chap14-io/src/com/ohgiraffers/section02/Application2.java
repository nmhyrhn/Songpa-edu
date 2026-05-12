package com.ohgiraffers.section02;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Application2 {
    public static void main(String[] args) {

        /*FileOutputStream : 데이터를 1바이트씩 파일에 출력하는 스트림*/

        //try-with-resources : try() 소괄호 안에서 자원을 선언하면
        //try 블럭이 끝날때(정상이든 예외든) 자동으로 close() 호출
        try (FileOutputStream fout= new FileOutputStream("src/com/ohgiraffers/section02/testOutputStream.txt")) {

            fout.write(97); //아스키코드 97 -> a

            //10: 개행문자(엔터)
            byte[] bar = new byte[] {10, 98, 99, 100, 101, 102};

            //배열 일부만 쓰기
            //write(byte[]), offset, length);

            fout.write(bar);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //try 블록 끝 -> 자동으로 fout.close()호출됨

    }
}
