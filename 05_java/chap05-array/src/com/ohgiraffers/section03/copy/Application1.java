package com.ohgiraffers.section03.copy;

public class Application1 {
    public static void main(String[] args) {
        /*
        * [얕은복사]
        * stack의 주소값만 복사하므로, 두 변수가 동일한 배열(heap)을 참조한다.
        * 따라서 한쪽에서 배열을 수정하면 다른 쪽에도 영향이 간다.
        * */

        //원본 배열
        int[] originArr = {1,2,3,4,5};

        int[] copyArr = originArr;

        // hashCode()로 통해 두 변수가 같은 주소를 가리키는지 확인
        System.out.println(originArr.hashCode());
        System.out.println(copyArr.hashCode());

        System.out.println(originArr[4]);
        copyArr[4] = 90;
        System.out.println(originArr[4]); //값이 같이 변경됨
    }
}
