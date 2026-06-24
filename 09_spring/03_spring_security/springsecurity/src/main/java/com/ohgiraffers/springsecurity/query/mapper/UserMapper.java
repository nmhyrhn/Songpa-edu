package com.ohgiraffers.springsecurity.query.mapper;

import com.ohgiraffers.springsecurity.query.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    UserDto findUserByUsername(String username);

    List<UserDto> findAllUsers();
}
