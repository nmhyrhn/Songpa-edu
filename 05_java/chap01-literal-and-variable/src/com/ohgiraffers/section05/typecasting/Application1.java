package com.ohgiraffers.section05.typecasting;

public class Application1 {
    public static void main(String[] args) {
        /*
        * 자동 형변환
        *
        * 자바에서 다른 타입끼리의 연산은 피연산자들이 모두 같은 타입인 경우 실행할 수 있다.
        * 즉, 같은 데이터 타입끼리만 연산을 수행할 수 있다.
        * */

        //1. 작은 자료형에서 큰 자료형으로는 자동 형변환이 된다.
        byte bnum = 1;
        short snum = bnum;
        int inum = snum;
        long lnum = inum;


        //연산 시에도 자동으로 큰 자료형에 맞춰 계산
        int num1 = 10;
        long num2 = 10;

//        int result = num1 + num2; //long형으로 반환되서 int에 담을 수 없음
        long result = num1 + num2;

        /*2. 정수는 실수로 자동 형변환 된다.*/
        long eight = 8;
        float four = eight;

        float result2 = eight * four;

        /*3. 논리형은 형변환 규칙에서 제외
        * 어느 자료형이든 boolean을 형변환 해서 담을 수 없음*/
        boolean isTrue = true;
//        byte b = isTrue;
//        short s = isTrue;

        /*강제형변환
        * 바꾸려는 자료형으로 캐스트 연산자를 이용하여 형변환한다.
        * (바꿀자료형) 값
        * */

        long lnum1 = 8;
//        int inum1 = lnum1; // 컴파일 에러가 남
        int inum1 = (int)lnum1;

        /*실수를 정수로 변경 시 강제 형변환이 필요*/

        float fnum2 = 4.0f;
        long lnum3 = (long)fnum2; // .(소수점) 뒤에 있는 데이터는 손실

    }
}
