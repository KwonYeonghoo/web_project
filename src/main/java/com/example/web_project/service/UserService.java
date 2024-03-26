package com.example.web_project.service;

import java.io.IOException;
import java.util.List;

import com.example.web_project.model.DTO.UserDto;

import jakarta.servlet.http.HttpServletResponse;

public interface UserService {
    // select
    public UserDto getUserByName(String userId);

    public List<UserDto> getAllUser();

    // insert
    public void insertUser(UserDto dto)throws Exception;

    // update
    public void updateUser(UserDto dto);

    // delete
    public void deleteUser(String userId);

    // 로그인 성공 시 >> 로그인 유무 저장
    public void updateIsLoginByName(String userId, Boolean isLogin);

    // 회원가입
    public void joinUser(UserDto dto, HttpServletResponse response) throws Exception;

    // id 중복확인
    public void checkDuplicate(String userId, HttpServletResponse response) throws IOException;
}
