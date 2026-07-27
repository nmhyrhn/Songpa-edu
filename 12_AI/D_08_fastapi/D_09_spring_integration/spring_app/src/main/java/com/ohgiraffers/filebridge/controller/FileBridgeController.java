package com.ohgiraffers.filebridge.controller;

import com.ohgiraffers.filebridge.client.FastApiFileClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

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
    public ResponseEntity<?> health(){
        try{
            return ResponseEntity.ok(fastApiFileClient.checkHealth());
        } catch (RestClientException e) {
            return unavailable("FastAPI서버에 연결할 수 없습니다.");
        }
    }

    private ResponseEntity<?> unavailable(String message) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of("code", "FASTAPI_UNAVAILABLE", "message", message));
    }
}
