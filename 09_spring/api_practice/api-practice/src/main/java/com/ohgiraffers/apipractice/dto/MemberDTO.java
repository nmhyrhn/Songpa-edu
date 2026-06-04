package com.ohgiraffers.apipractice.dto;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MemberDTO {
    private int memberNo;
    private String id;
    private String name;
    private String email;
    private LocalDate joinedAt;
}
