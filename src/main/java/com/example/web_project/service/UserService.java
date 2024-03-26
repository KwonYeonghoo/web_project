package com.example.web_project.service;

import java.util.List;

import com.example.web_project.model.DTO.UserDto;

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
    public void joinUser(UserDto dto) throws Exception;
}
