package com.ohgiraffers.filebridge.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.PropertyNamingStrategy;
import tools.jackson.databind.annotation.JsonNaming;

/*
* FastAPI의 python함수를 직접 실행하지 않고 HTTP요청으로 호출한다.
* */
@Service
public class FastApiFileClient {

    private final RestClient restClient;

    public FastApiFileClient(@Value("${fastapi.url}") String baseUrl) {

        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .requestFactory(new SimpleClientHttpRequestFactory())
                .build();
    }

    public HealthResponse checkHealth(){
        return restClient.get()
                .uri("/health")
                .retrieve()
                .body(HealthResponse.class);
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record HealthResponse(String status, String service, String version){}

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record FileInfo(
            String id,
            String originalName,
            String contentType,
            long size,
            String description,
            String storedNames
    ) {}

}


