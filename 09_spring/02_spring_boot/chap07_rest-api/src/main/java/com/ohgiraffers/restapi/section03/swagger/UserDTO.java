package com.ohgiraffers.restapi.section03.swagger;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Schema(description = "회원정보 DTO")
public class UserDTO {

    //데이터 모델 설명을 붙이는 어노테이션
    @Schema(description = "회원번호(PK)")
    private Integer no;
    private String id;
    private String pwd;
    private String name;
    private Date enrollDate;
    @Schema(description = "회원 활성 상태", example = "active")
    private String status;

}
