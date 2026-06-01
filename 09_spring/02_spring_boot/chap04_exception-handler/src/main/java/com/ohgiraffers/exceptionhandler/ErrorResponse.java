package com.ohgiraffers.exceptionhandler;

import org.apache.logging.log4j.spi.LocationAwareLogger;

import java.time.LocalDateTime;

public class ErrorResponse {

    /*REST API 실패 응답의 공통 JSON 구조
    * 프론트엔드는 이 구조를 기준으로 에러 메시지와 상태 코드를 처리할 수 있다.*/
    private final LocalDateTime timestamp;
    private final int status;
    private final String error;
    private final String message;
    private final String path;

    public ErrorResponse(int status, String error, String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    /*Jackson이 객체를 JSON으로 바꾸러면 getter 필요*/

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }



}
