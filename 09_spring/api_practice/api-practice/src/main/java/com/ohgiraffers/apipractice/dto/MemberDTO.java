package com.ohgiraffers.apipractice.dto;

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
public class MemberDTO {
    private int memberNo;

    @NotBlank(message = "회원 아이디는 필수입니다.")
    @Size(min = 4, max = 20, message = "회원 아이디는 4자 이상 20자 이하로 입력해야 합니다.")
    private String id;

    @NotBlank(message ="회원 이름은 필수입니다.")
    private String name;

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    private LocalDate joinedAt;


}
