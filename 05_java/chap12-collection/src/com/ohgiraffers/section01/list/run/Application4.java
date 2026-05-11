package com.ohgiraffers.section01.list.run;

import java.util.EmptyStackException;
import java.util.Stack;

public class Application4 {
    public static void main(String[] args) {

        //stack : 후입선출(LIFO) - 마지막에 넣은 것이 먼저 나온다.
        Stack<Integer> stack = new Stack<>();

        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);

        //peek: 맨 위의 값을 확인(제거는 하지 않음)
        System.out.println("peek:" + stack.peek());
        System.out.println(stack);

        //pop: 맨 위의 값을 꺼내고 제거
        System.out.println("pop:" + stack.pop());
        System.out.println(stack);


        try{
            stack.pop();
            stack.pop();
            stack.pop();
            stack.pop();

        } catch (EmptyStackException e) {
            System.out.println("스택이 비어있습니다.");
        }

    }
}
