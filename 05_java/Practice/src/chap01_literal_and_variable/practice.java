package chap01_literal_and_variable;

public class practice {
    public static void main(String[] args) {

        /*실습문제 1

        정수형 변수 2개를 선언하여,
        각 20과 30으로 초기화 한 후,
        두 수의 더하기, 빼기, 곱하기, 나누기, 나머지를 다음과 같이 출력하세요.

        -- 출력 예시 --
        더하기 결과  : 50
        빼기 결과 : -10
        곱하기 결과 : 600
        나누기한 몫 : 0
        나누기한 나머지 : 20
         */

        int a = 20;
        int b = 30;

        int sum = a + b;
        int sub = a - b;
        int mul = a * b;
        int div = a / b;
        int mod = a%b;

        System.out.println("---실습 1---");
        System.out.println("더하기 결과 : " + sum);
        System.out.println("빼기 결과 : " + sub);
        System.out.println("곱하기 결과 : " + mul);
        System.out.println("나누기한 몫 : " + div);
        System.out.println("나누기한 나머지 : " + mod);


        /*실습문제 2

        사각형의 넓이와 둘레를 구하여 출력합니다.
        너비 12.5 높이 36.4를 변수에 저장하고,
        넓이와 둘레를 계산한 뒤 각각 넓이와 둘레를 변수에 담아 아래와 같이 출력하세요.

        -- 출력 예시 --

        면적 : 455.0
        둘레 : 97.8
         */

        double width = 12.5;
        double height = 36.4;

        System.out.println("---실습 2---");
        System.out.println("면적 : " + (width * height));
        System.out.println("둘레 : " + ((width * 2) + (height * 2)));

        /*
        문자형 변수를 하나 선언하고 'a'를 대입한 후,
                문자 'a'가 가지는 유니코드값을 출력하세요.

        -- 출력 예시 --
        문자 a의 unicode : 97
         */

        char ch = 'a';

        System.out.println("---실습 3---");
        System.out.println("문자 a의 unicode : " + (int)ch);

         /*실습문제 4

        국어점수 80.5점, 수학점수 50.6점, 영어점수 70.8점을 실수 형태로 저장한 뒤,
                총점과 평균을 정수 형태로 출력하세요.

        -- 출력 예시 --
        총점 : 201
        평균 : 67
          */

        double kor = 80.5;
        double math = 50.6;
        double eng = 70.8;

        double sum1 = kor+math+eng;
        double avg = sum1 / 3;

        System.out.println("---실습 4---");
        System.out.println("총점 : " + (int)sum1);
        System.out.println("평균 : " + (int)avg);




    }
}
