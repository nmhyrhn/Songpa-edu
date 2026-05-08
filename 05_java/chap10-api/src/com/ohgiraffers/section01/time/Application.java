package com.ohgiraffers.section01.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Application {
    public static void main(String[] args) {
        /*
        * java.time 패키지의 클래스
        * -LocalDate: 날짜 정보만 표현 (2025-12-25)
        * -LocalTime: 시간 정보만 표현 (13:08:00)
        * -LocalDateTime: 날짜와 시간 모두 표현 (2025-12-15T13:08:00)
        * */

        LocalDate departureDate = LocalDate.of(2026, 12, 25);
        LocalTime departureTime = LocalTime.of(13, 8);
        System.out.println("Departure date: " + departureDate);
        System.out.println("Departure time: " + departureTime);

        LocalDateTime trainDeparture = LocalDateTime.of(departureDate, departureTime);
        System.out.println("부산행 KTX 출발 시각: " + trainDeparture);

        System.out.println(trainDeparture.getYear());
        System.out.println(trainDeparture.getMonth());
        System.out.println(trainDeparture.getMonthValue());
        System.out.println(trainDeparture.getDayOfWeek());
        System.out.println(trainDeparture.getHour());

        LocalDateTime arrivalDeadLine = trainDeparture.minusMinutes(20);
        System.out.println(arrivalDeadLine);

        //trainDeparture 시간이 now 시간보다 isBefore 인가?
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);

        boolean isPast = trainDeparture.isBefore(now);
        System.out.println(isPast);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String ticket = trainDeparture.format(formatter);
        System.out.println("ticket: " + ticket);

        //반대로 특정 형식의 문자열을 날짜 객체로 변환(분석, Parse)
        String christmasInput = "261225";

        DateTimeFormatter christmasParser = DateTimeFormatter.ofPattern("yyMMdd");

        //LocalDate.parse()에 문자열과 패턴 전달
        LocalDate parsedChristmas = LocalDate.parse(christmasInput, christmasParser);
        System.out.println("parsedChristmas: " + parsedChristmas);


    }
}
