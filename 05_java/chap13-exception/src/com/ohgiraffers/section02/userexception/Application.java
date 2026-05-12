package com.ohgiraffers.section02.userexception;

import com.ohgiraffers.section02.userexception.exception.MoneyNegativeException;
import com.ohgiraffers.section02.userexception.exception.NotEnoughMoneyException;
import com.ohgiraffers.section02.userexception.exception.PriceNegativeException;

public class Application {
    public static void main(String[] args) {

        ExceptionTest et = new ExceptionTest();

        try {
            //et.checkEnoughMoney(-3000, 10000);
            //et.checkEnoughMoney(3000, -10000);
            //et.checkEnoughMoney(3000, 1000);
            et.checkEnoughMoney(3000, 10000);

            // multi-catch: 여러 예외 타입을 | 기호로 묶어 하나의 catch 블록에서 처리 가능
        } catch (PriceNegativeException | MoneyNegativeException e) {
            System.out.println("진단명: " + e.getClass() + "발생 " + e.getMessage());
        } catch (NotEnoughMoneyException e) {
            System.out.println("진단명: " + e.getClass() + "발생 " + e.getMessage());
        } catch(Exception e){
            //혹시 모를 모든 예외를 잡는 안전망
            e.printStackTrace();
        } finally {
            //예외 발생 여부와 상관없이 실행
            //try 블럭에서 return을 만나도 finally는 실행됨
            System.out.println("계산이 끝나 영수증을 출력합니다.");
        }

        System.out.println("프로그램을 정상적으로 종료합니다.");

    }
}
