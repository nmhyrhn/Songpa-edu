package com.ohgiraffers.section01.list.run;

import java.util.LinkedList;
import java.util.Queue;

public class Application5 {
    public static void main(String[] args) {


        //Queue: 선입선출(FIFO) - 먼저 넣은 것이 먼저 나온다

        //Queue는 인터페이스이므로 LinkedList로 구현체 생성
        Queue<String> queue = new LinkedList<>();

        queue.offer("first");
        queue.offer("second");
        queue.offer("third");
        System.out.println(queue);

        System.out.println("peek : " + queue.peek()); //꺼내고 제거x, 확인만
        System.out.println(queue);

        System.out.println("poll : " + queue.poll()); //꺼내고 제거
        System.out.println(queue);

        queue.poll();
        queue.poll();
        System.out.println(queue.poll()); //null - 빈 큐에서 poll은 null 반환

    }
}
