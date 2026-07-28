package com.ohgiraffers.filebridge.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.io.IOException;

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



    public FileInfo upload(MultipartFile file, String description) throws IOException {

        ByteArrayResource resource = new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        };

        HttpHeaders fileHeaders = new HttpHeaders();
        fileHeaders.setContentType(
                file.getContentType() == null
                        ? MediaType.APPLICATION_OCTET_STREAM
                        : MediaType.parseMediaType(file.getContentType())
        );

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new HttpEntity<>(resource, fileHeaders));
        body.add("description", description);

        return restClient.post()
                .uri("/api/v1/files")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(body)
                .retrieve()
                .body(FileInfo.class);
    }



















    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record HealthResponse(String status, String service, String version) {}

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record FileInfo(
            String id,
            String originalName,
            String contentType,
            long size,
            String description,
            String storedName
    ) {}
}