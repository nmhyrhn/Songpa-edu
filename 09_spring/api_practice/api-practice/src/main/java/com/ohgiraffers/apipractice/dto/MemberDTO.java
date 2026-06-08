package com.ohgiraffers.apipractice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Schema(description = "회원 DTO")
public class MemberDTO {

    @Schema(description = "회원 번호", example = "1")
    private Integer memberNo;

    @Schema(description = "회원 아이디", example = "reader01")
    @NotBlank(message = "회원 아이디는 필수입니다.")
    @Size(min = 4, max = 20, message = "회원 아이디는 4자 이상 20자 이하로 입력해야 합니다.")
    private String id;

    @Schema(description = "회원 이름", example = "홍길동")
    @NotBlank(message ="회원 이름은 필수입니다.")
    private String name;

    @Schema(description = "회원 이메일", example = "reader01@test.com")
    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @Schema(description = "가입일", example = "2026-06-08")
    private LocalDate joinedAt;


}
