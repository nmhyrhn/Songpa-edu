package com.ohgiraffers.filebridge.controller;

import com.ohgiraffers.filebridge.client.FastApiFileClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/files")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class FileBridgeController {

    private final FastApiFileClient fastApiFileClient;

    public FileBridgeController(FastApiFileClient fastApiFileClient) {
        this.fastApiFileClient = fastApiFileClient;
    }

    @GetMapping("/health")
    public ResponseEntity<?> health() {
        try{
            return ResponseEntity.ok(fastApiFileClient.checkHealth());
        } catch(RestClientException e) {
            return unavailable("FastAPI서버에 연결할 수 없습니다.");
        }
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> upload(
            @RequestPart("file") MultipartFile file,
            @RequestParam("description") String description
    ){
        try{
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(fastApiFileClient.upload(file, description));
        } catch(RestClientResponseException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .body(Map.of(
                            "code", "FASTAPI_REJECTED",
                            "message", e.getResponseBodyAsString()
                    ));
        } catch(IOException | RestClientException e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(Map.of("code", "UPLOAD_FAILED",
                            "message", "파일 전달에 실패했습니다."));
        }
    }

    private ResponseEntity<?> unavailable(String message) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of("code", "FASTAPI_UNAVAILABLE", "message", message));
    }
}