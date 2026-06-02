package com.ohgiraffers.restapi.section02.valid;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserDTO {

    /*
    * Bean Validation
    * 객체의 필드에 규칙을 붙여두고, 그 객체가 규칙에 맞는지 검사하는 방식
    * */


    private Integer no;

    @NotNull(message = "아이디는 반드시 입력되어야 합니다.")
    @NotBlank(message = "아이디는 공백일 수 없습니다.")
    private String id;

    @NotNull(message = "비밀번호는 반드시 입력 되어야 합니다.")
    @Length(max = 10, message = "비밀번호는 길이 10을 초과할 수 없습니다.")
    private String pwd;

    @NotBlank(message = "이름은 공백일 수 없습니다.")
    @Size(max = 10, message = "이름은 길이 10을 초과할 수 없습니다.")
    private String name;

    @Past(message = "가입일은 현재보다 과거 날짜가 입력 되어야 합니다.")
    private Date enrollDate;




}
