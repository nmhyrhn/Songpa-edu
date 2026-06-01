package com.ohgiraffers.fileupload;

public class FileDTO {

    /*
    * 업로드 결과를 클라이언트에 JSON으로 내려주기 위한 DTO
    * 실제 파일 데이터 자체가 아니라 저장된 파일의 메타 정보를 담는다.
    * */

    private String originFileName; //원본 파일명
    private String savedFileName; //파일 저장명
    private String filePath; //서버에 저장된 경로
    private String fileDescription; //사용자가 함께 보낸 파일 설명
    private long fileSize; //파일 크기
    private String contentType; //text/plain, image/png 같은 파일 MIME XKDLQ


    public FileDTO() {
    }

    public FileDTO(String originFileName, String savedFileName, String filePath, String fileDescription, long fileSize, String contentType) {
        this.originFileName = originFileName;
        this.savedFileName = savedFileName;
        this.filePath = filePath;
        this.fileDescription = fileDescription;
        this.fileSize = fileSize;
        this.contentType = contentType;
    }

    public String getOriginFileName() {
        return originFileName;
    }

    public void setOriginFileName(String originFileName) {
        this.originFileName = originFileName;
    }

    public String getSavedFileName() {
        return savedFileName;
    }

    public void setSavedFileName(String savedFileName) {
        this.savedFileName = savedFileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileDescription() {
        return fileDescription;
    }

    public void setFileDescription(String fileDescription) {
        this.fileDescription = fileDescription;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public String toString() {
        return "FIleDTO{" +
                "originFileName='" + originFileName + '\'' +
                ", savedFileName='" + savedFileName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", fileDescription='" + fileDescription + '\'' +
                ", fileSize=" + fileSize +
                ", contentType='" + contentType + '\'' +
                '}';
    }
}
