package com.ohgiraffers.section01.string;

import java.util.Arrays;

public class Application {
    public static void main(String[] args) {

        String rawData = " [PROD-001] Apple iphone 17 Pro | a17 bionic | 2500000 ";
        System.out.println(rawData);

        //trim() : 앞 뒤 공백 제거
        String trimmedData = rawData.trim();
        System.out.println(trimmedData);

        //toUpperCase() : 대문자 변환 (검색/비교 시 대소문자 통일)
        String upperCaseData = trimmedData.toUpperCase();
        System.out.println(upperCaseData);

        //indexOf(): 특정 문자의 인덱스 찾기
        int codeStart = upperCaseData.indexOf('[') + 1;
        int codeEnd = upperCaseData.indexOf(']');
        int nameEnd = upperCaseData.indexOf('|') ;

        //존재하지 않으면 -1 반환
        int name = upperCaseData.indexOf("*");
        System.out.println("name = " + name);

        if(codeStart == -1 || codeEnd == -1) {
            System.out.println("데이터 형식이 올바르지 않습니다.");
            return;
        }

        //substring(start, end): 특정 구간 잘라내기
        String productCode = upperCaseData.substring(codeStart, codeEnd);
        System.out.println(productCode);

        String productName = upperCaseData.substring(codeEnd + 2, nameEnd);
        System.out.println(productName);

        //replace(): 특정 문자열 치환
        String replaced = productName.replace("APPLE IPHONE 17 PRO", "GALAXY S26 ULTRA");
        System.out.println(replaced);

        // == vs equals
        String str1 = "java"; // 리터럴 방식
        String str2 = "java";
        String str3 = new String("java");//new 연산자 방식
        String str4 = new String("java");

        System.out.println(str1 == str2);
        System.out.println(str3 == str4);
        System.out.println(str3.equals(str4));

        //불변성 확인
        str2 += " mysql"; //기존 문자열이 수정되는게 아니라, 완전히 새로운 객체 생성
        System.out.println(str1 == str2);

        //split(): 문자열 분리하기
        String emp = "100/홍길동/서울/영업부";
        String[] empArr = emp.split("/");
        for(String info : empArr){
            System.out.println(info.isEmpty() ? "정보 없음" : info);
        }
        System.out.println(Arrays.toString(empArr));

        // 이스케이프 문자
        System.out.println("안녕하세요. \n저는 개발자 입니다.");
        System.out.println("주특기는 \t 'java'와 \"Spring\"입니다.");
        System.out.println("파일 경로: C:\\Users\\student");
    }
}
