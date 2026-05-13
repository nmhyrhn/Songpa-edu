package com.ohgiarffers.section01;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Application {
    public static void main(String[] args) {

        /*
        * Stream API
        * 컬렉션에 들어있는 여러 데이터를 조건에 따라 거르고, 바꾸고, 모으는 작업을
        * 읽기 좋게 만들어주는 API 입니다.
        * */

        List<String> names = Arrays.asList("홍길동", "유관순", "이순신", "신사임당", "장보고", "강감찬", "이성계");

        //기본 방식 : for-each문을 사용한 외부 반복
        for(String name : names) {
            System.out.println(name);
        }
        System.out.println("------");

        //Stream 방식 - forEach를 사용한 내부 반복
        names.stream().forEach(name -> System.out.println(name));

        /*이름이 3글자인 사람만 찾아서, "[홍길동]"과 같은 형태로 변환하여 출력*/
        for(String name : names) {
            if(name.length() ==3) {
                String decoratedName = "[" + name + "]";
                System.out.println(decoratedName);
            }
        }
        System.out.println("=======");

        names.stream()
                .filter(name -> name.length() == 3)
                .map(name -> "[" + name + "]")
                .forEach(decoratedName -> System.out.println(decoratedName));

        /*결과물(새로운 List) 만들기
        * 성이 '이' 씨인 사람들을 찾아서, 알파벳 순서로 정렬한 후, 새로운 List로 만들어라*/

        List<String> leeFamily = names.stream()
                .filter(name -> name.startsWith("이"))
                .sorted(Comparator.reverseOrder()) //reverseOrder() 가나다 역순으로 정렬
                .collect(Collectors.toList()); //최종 결과물들을 새로운 List로 수집

        System.out.println(leeFamily);

        //최종 연산
        //1. count(): 조건에 맞는 요소의 개수 세기
        long leeCount = names.stream()
                .filter(name -> name.startsWith("이"))
                .count();
        System.out.println(leeCount);

        //2. anyMatch(): 조건에 맞는 요소가 하나라도 있는지 확인
        boolean hasJang = names.stream()
                .anyMatch(name -> name.equals("장보고")); //하나라도 조건 만족하면 ture
        System.out.println(hasJang);

        //3. allMatch(): 모든 요소가 조건을 만족하는지 확인
        boolean isAllThreeLetters = names.stream()
                .allMatch(name -> name.length() == 3);//모든 요소가 조건을 만족하면 true
        System.out.println(isAllThreeLetters);


    }
}
