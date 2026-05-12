package com.ohgiraffers.section03.filterstream;

import java.io.*;

public class Application {
    public static void main(String[] args) {

        /*
        * BufferedWriter / BufferedReader
        * - 기본 스트림(FileWriter / FileReader)를 감싸서 사용하는 보조 스트림
        * - 버퍼는 데이터를 잠깐 모아두는 메모리 공간이다.
        * - 데이터를 바로바로 파일에 쓰거나 읽는 것보다, 버퍼를 거치면 입출력 횟수를 줄일 수 있다.
        * - BufferedReader는 readLine()을 제공하여 텍스트를 한 줄씩 읽기 편하다
        * */

        try(BufferedWriter bw = new BufferedWriter(new FileWriter("src/com/ohgiraffers/section03/filterstream/testBuffered.txt"))){
            //FileWriter가 실제 파일과 연결되는 기본 스트림이고, BufferedWriter는 그 위에 버퍼 기능을 붙이는 보조 스트림이다.
            bw.write("반갑습니다");
            bw.write("안녕");

            //flush()로 버퍼에 남은 내용을 즉시 파일로 밀어낸다.
            bw.flush();
            //지금은 try-with-resources 가 끝나면 close()가 호출되고,
            //close() 과정에서 flush()도 함계 처리된다.

        } catch (IOException e) {
            e.printStackTrace();
        }

        //BufferedReader : FileReader를 감싸서 버퍼 + readLine() 기능 추가
        try(BufferedReader br = new BufferedReader(new FileReader("src/com/ohgiraffers/section03/filterstream/testBuffered.txt"))) {


            //readLine(): 파일 내용을 한 줄씩 읽어 String으로 반환
            //더 이상 읽을 줄이 없으면 null 반환

            String temp;
            while ((temp = br.readLine()) != null) {
                System.out.println(temp);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
