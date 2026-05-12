package com.ohgiraffers.section02;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Application {
    public static void main(String[] args) {

        /*FileInputStream : File로부터 데이터를 1바이트씩 읽어오는 스트림
        * 주요 대상: 모든 종류의 파일(이미지, 영상, 텍스트 등)
        * */

        FileInputStream fin = null;

        try {
            fin = new FileInputStream("src/com/ohgiraffers/section02/testInputStream.txt");

            int value;

            //read():파일에 기록된 값을 1바이트씩 읽어온다. 더 이상 읽을 데이터가 없으면 -1을 반환
            while((value = fin.read()) != -1) {
                System.out.println(value); // 바이트 숫자값 (ASCII)
                System.out.println((char)value);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            //사용이 끝난 스트림을 반드시 닫아준다. (자원 반납)
            if(fin != null ) {
                try {
                    fin.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
}
