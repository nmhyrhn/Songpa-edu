package chap02_operator;

public class practice {
    public static void main(String[] args) {

        /*실습문제 1

        두 개의 정수형 변수를 선언하고, 삼항 연산자를 사용하여
        두 수 중 큰 수를 출력하는 프로그램을 작성해본다.

        ---출력예시
        두 수 중 큰 수는 20입니다.
         */

        int a = 10;
        int b = 20;

        int result = (a > b) ? a : b;

        System.out.println("---실습 1---");
        System.out.println("두 수 중 큰 수는 " + result + "입니다.");

        /*실습문제 2

        정수형 변수를 선언하여 점수를 저장하고,
        삼항 연산자를 사용하여 점수가 60점 이상이면 “합격입니다”,
        그렇지 않으면 “아쉽지만 불합격입니다.” 을 출력하는 프로그램을 작성해본다.

        ---출력예시
        합격입니다~~!!!
        또는 아쉽지만 불합격입니다….
        */

        int score = 70;

        String result1 = (score > 60) ? "합격" : "불합격";

        System.out.println("---실습 2---");
        System.out.println(result1);

        /*실습문제 3
        정수형 변수를 선언하고, 삼항연산자를 사용하여
        입력된 수가 짝수인지 홀수인지 출력하는 프로그램을 작성해본다.

        참고사항 : 조건식에 %를 활용하여 짝수인지 홀수인지를 판단해 보세요~~

        출력예시
        입력하신 수는 짝수입니다.
        또는 입력하신 수는 홀수입니다.
         */

        int num1 = 4;
        String result3 = (num1 % 2 == 0) ? "짝수" : "홀수" ;

        System.out.println("---실습 3---");
        System.out.println(result3);

        /*실습문제 4

        실수를 변수로 선언하여 점수를 저장하고, 이를 정수로 변환하여
        점수가 90점 이상이면 ”A”,
        80점 이상이면 “B”,
        70점 이상이면 “C”,
        60점 이상이면”D
        60점 미만이면 “F”를 출력하는 프로그램을 작성해본다.

        출력예시
        홍길동의 이번 점수등급은 B입니다.
        */

        double score1 = 64.23;

        System.out.println("---실습 4---");

        int tranScore = (int)(score1 / 10);
        switch(tranScore) {

            case 10:
            case 9:
                System.out.println("홍길동의 이번 점수등급은 A입니다.");
                break;
            case 8:
                System.out.println("홍길동의 이번 점수등급은 B입니다.");
                break;
            case 7:
                System.out.println("홍길동의 이번 점수등급은 C입니다.");
                break;
            case 6:
                System.out.println("홍길동의 이번 점수등급은 D입니다.");
                break;
            default:
                System.out.println("홍길동의 이번 점수등급은 F입니다.");
        }

        /*- 실습문제 5

        정수형 변수를 두개 선언하여 회원의 월(month)과 일(day)를 저장합니다.
        월이 1월부터 6월까지이면서, 일이 1일부터 15일까지인 경우 “배민 쿠폰”을,
        월이 7월부터 12월까지이면서, 일이 16일부터 31일까지인 경우 “스타벅스 커피”를
        그 외의 경우는 “사탕”이 선물로 선택되는 프로그램을 작성해본다.

        출력예시
        “본인이름”의 선물은 스타벅스 커피 입니다.
        또는 “본인이름”의 선물은 사탕 입니다.
        */

        int month = 4;
        int day = 23;
        String name = "홍길동"; // 이름을 변수로 관리하면 편리해요.

        System.out.println("---실습 5---");

        if ((month >= 1 && month <= 6) && (day >= 1 && day <= 15)) {
            System.out.println(name + "의 선물은 배민 쿠폰 입니다.");
        }
        else if ((month >= 7 && month <= 12) && (day >= 16 && day <= 31)) {
            System.out.println(name + "의 선물은 스타벅스 커피 입니다.");
        }
        else {
            System.out.println(name + "의 선물은 사탕 입니다.");
        }


    }
}