package com.ohgiraffers.restapi.section01.responseentity;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserDTO {

    private Integer no;
    private String id;
    private String pwd;
    private String name;
    private Date enrollDate;
    private String status;

}
