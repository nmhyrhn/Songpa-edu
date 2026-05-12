package com.ohgiraffers.section03.filterstream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Application2 {
    public static void main(String[] args) {

        /* 형변환 보조스트림(InputStreamReader / OutputStreamWriter)
        * - byte 기반 스트림은 char 기반 스트림으로 연결할 때 사용
        * - System.in은 키보드 입력을 바이트 단위로 받는 InputStream이다.
        * - InputStreamReader는 그 바이트를 문자로 해석하여 Reader로 바꿔준다
        * - BufferedReader는 Reader를 감싸서 버퍼 기능과 readLine()을 제공한다.
        * */

        //감싸는 순서
        //1. System.in : 키보드 입력을 받는 바이트 스트림
        //2. InputStreamReader: 바이트 스트림을 문자 스트림으로 변환
        //3. BufferedReader: 버퍼 기능과 readLine()추가

        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.print("문자열 입력: ");
            String value = br.readLine(); //엔터를 누를 때까지 입력한 한 줄을 문자열로 읽는다.
            System.out.println("입력값 : " + value);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
