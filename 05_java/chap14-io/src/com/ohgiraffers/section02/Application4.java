package com.ohgiraffers.section02;

import java.io.FileWriter;
import java.io.IOException;

public class Application4 {
    public static void main(String[] args) {

        /*FileWriter: 프로그램의 데이터를 1글자 단위로 파일로 내보내기 위한 출력 스트림*/

        try(FileWriter fw = new FileWriter("src/com/ohgiraffers/section02/testWriter.txt")) {

            fw.write(97);
            fw.write('A');
            fw.write(new char[] {'a', 'p', 'p', 'l', 'e'});
            fw.write("안녕하세요 반가워요");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

/*
* 바이트 스트림
* 읽기 - FileInputStream
* 쓰기 - FileOutputStream
* 단위 - 1bye
* 한글 - 깨질 수 있다
* 파일 - 이미지, 영상 등 모든 파일
*
* 문자스트림
* 읽기 - FileReader
* 쓰기 - FileWriter
* 단위 - 1문자(char)
* 한글 - 인코딩이 맞으면 정상
* 파일 - 텍스트 파일
* */
