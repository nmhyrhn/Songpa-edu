package com.ohgiraffers.section02.variable;

public class Application2 {
    public static void main(String[] args) {

        /* 변수 명명 규칙*/
        /*
        * 1. 컴파일 에러를 발생시키는 규칙
        * - 동일한 범위 내에서 동일산 변수명을 가질 수 없다.
        * */

        int age = 20;
//        int age = 20;

        // 예약어 사용 불가 (자바에서 미리 사용하겠다고 compiler와 약속한 키워드)
//        int true = 1;
//        int for = 20;

        //변수명은 대/소문자를 구분한다.
        int Age = 20;
        int Ture = 1;

        // 변수명은 숫자로 시작할 수 없다.
//        int 1age = 1;
        int age1 = 20;

        // 특수 기호는 '_'와 '$'만 사용 가능하다
//        int sh@ap = 10;
//        int -age = 10;
        int _age = 10;
        int $age = 10;

        /*
        * 2. 컴파일 에러를 발생시키지는 않지만 개발자들끼리의 암묵적 규칙*/

        //변수명의 길이 제한은 없지만 적당히
        int dasdjaskasdaskdjasdklsdffsd;

        //합성어로 이루어진 경우 첫 단어는 소문자, 두번째 시작 단어는 대문자로 작성
        int maxAge = 10;
        int max_age = 20; //단어와 단어 사이의 연결을 언더스코어(_)로 하지 않는다.

        int 나이; // 한글로 변수명 선언은 가능하나 권장하지 않는다.

        // 어떤 의미를 가지는지 명확하게 표현하
        String s; // 가능하나 의미가 없음
        String name;

        //전형적 변수 이름이 있다면 가급적 사용
        int sum = 0;
        int max = 10;
        int min = 1;
        int count = 2;

        //boolean형은 가급적 긍정형태로 네이밍
        boolean isAlive = true;
        boolean isDeath = false; //부정형 보다는 긍정형이 더 나은 방식

    }
}