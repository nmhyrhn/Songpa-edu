package chap06_class_and_object.com.greedy.level01.basic.student.run;

import chap06_class_and_object.com.greedy.level01.basic.student.model.dto.StudentDTO;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {

        // 최대 10명의 학생 정보를 기록할 수 있게 배열을 할당한다.
        StudentDTO[] stu = new StudentDTO[10];

        Scanner sc = new Scanner(System.in);
        int cnt = 0;

        // while문을 사용하여 학생들의 정보를 계속 입력 받고
        // 한 명씩 추가 될 때마다 카운트함
        // 계속 추가할 것인지 물어보고, 대소문자 상관없이 ‘y’이면 계속 객체 추가
        // 3명 정도의 학생 정보를 입력 받아 각 객체에 저장함
        // 현재 기록된 학생들의 각각의 점수 평균을 구함
        while (cnt < 10) {
            System.out.println("학년:");
            int grade = sc.nextInt();

            System.out.println("반:");
            int classroom = sc.nextInt();

            sc.nextLine();

            System.out.println("이름:");
            String name = sc.nextLine();

            System.out.println("국어점수:");
            int kor = sc.nextInt();

            System.out.println("영어점수:");
            int eng = sc.nextInt();

            System.out.println("수학점수:");
            int math = sc.nextInt();

            stu[cnt] = new StudentDTO(grade, classroom, name, kor, eng, math);
            cnt++;

            System.out.println("계속 추가할 겁니까 ? (y/n) : ");
            String add = sc.next();

            if (add.equalsIgnoreCase("n")) {
                break;
            }
        }

        // 학생들의 정보를 모두 출력 (평균 포함)
        for (StudentDTO s : stu) {
            if (s == null) break;
            System.out.println(s.getInformation());
        }

    }
}
