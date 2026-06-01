package com.ohgiraffers.fileupload;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/files")
public class FileUploadController {

    private final Path uploadRoot;

    public FileUploadController(@Value("${file.upload-dir}") String uploadDir) {
        this.uploadRoot = Paths.get(uploadDir).toAbsolutePath().normalize();
    }

    @PostMapping("/single")
    public ResponseEntity <Map<String, Object>> uploadSingleFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value="description", required = false) String description
            ) throws IOException {

            FileDTO uploadedFile = saveFile(file, "single", description);

                Map<String, Object> response = new LinkedHashMap<>();
                response.put("message", "단일 파일 업로드 성공");
                response.put("file", uploadedFile);

                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            }

    private FileDTO saveFile(MultipartFile file, String directoryName, String description) throws IOException {
        validateFile(file);

        //기본 업로드 경로 뒤에 하위 디렉토리 이름을 붙인다 (chap05_file-upload/uploads/single)
        Path uploadPath = uploadRoot.resolve(directoryName).normalize();
        Files.createDirectories(uploadPath);

        String originFileName = file.getOriginalFilename();
        String extension = extractExtension(originFileName);

        String savedFileName = UUID.randomUUID().toString().replace("-", "") + extension;
        Path targetPath = uploadPath.resolve(savedFileName).normalize();

        //실제로 파일을 디스크에 저장한다.
        file.transferTo(targetPath);

        return new FileDTO(
                originFileName,
                savedFileName,
                targetPath.toString(),
                description,
                file.getSize(),
                file.getContentType()
        );

    }

    // 파일 유효성 검사
    private void validateFile(MultipartFile file) {
        /*빈 파일 요청은 정상 업로드 처리하지 않음*/


        if(file.isEmpty() || file == null) {
            throw new IllegalArgumentException("업로드할 파일이 비어 있습니다.");
        }

        /*확장자 추출과 저장 파일명 생성을 위해 원본 파일명이 필요*/
        if(file.getOriginalFilename() == null || file.getOriginalFilename().isBlank()) {
            throw new IllegalArgumentException("원본 파일명이 없습니다.");
        }
    }

    //원본 파일명에서 확장자 추출
    private String extractExtension(String originFileName) {

        //파일명에 점이 여러 개 있을 수 있으니 마지막 점을 확장자 기준으로 삼는다.
        int lastDotIndex = originFileName.lastIndexOf(".");

        if(lastDotIndex == -1) {
            return "";
        }

        //먀지막 점부터 끝까지 잘라서 .png, .txt 처럼 점을 포함한 확장자 반환
        return originFileName.substring(lastDotIndex);

    }



}
