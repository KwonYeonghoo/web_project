package com.example.web_project.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.web_project.model.DAO.UserDao;
import com.example.web_project.model.DTO.UserDto;
import com.example.web_project.model.Entity.UserEntity;
import com.example.web_project.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void deleteUser(String userId) {
        // TODO Auto-generated method stub
        userDao.deleteUser(userId);
    }

    @Override
    public List<UserDto> getAllUser() {
        // TODO Auto-generated method stub

        List<UserEntity> userList = userDao.getAllUser();
        List<UserDto> dtoList = new ArrayList<>();
        for(UserEntity user : userList) {
            UserDto dto = new UserDto();
            dto.setUserId(user.getUserId());
            dto.setUserName(user.getUserName());
            dto.setUserPw(user.getUserPw());
            dto.setUserAge(user.getUserAge());
            dto.setUserEmail(user.getUserEmail());
            dto.setUserAddress(user.getUserAddress());

            dtoList.add(dto);
        }
        
        return dtoList;
    }

    @Override
    public UserDto getUserByName(String userId) {
        // TODO Auto-generated method stub
        log.info("[UserServiceImpl][getUserByName] userId >>> " + userId);
        UserEntity entity = userDao.getUserByName(userId);
        UserDto dto = new UserDto();
        log.info("[UserServiceImpl][getUserByName] entity >>> " + entity);

        dto.setUserId(entity.getUserId());
        dto.setUserName(entity.getUserName());
        dto.setUserPw(entity.getUserPw());
        dto.setUserAge(entity.getUserAge());
        dto.setUserEmail(entity.getUserEmail());
        dto.setUserAddress(entity.getUserAddress());
        
        return dto;
    }

    @Override
    public void insertUser(UserDto dto) {
        // TODO Auto-generated method stub
        UserEntity entity = new UserEntity();
        entity.setUserId(dto.getUserId());
        entity.setUserName(dto.getUserName());
        entity.setUserPw(dto.getUserPw());
        entity.setUserEmail(dto.getUserEmail());
        entity.setUserAddress(dto.getUserAddress());
        entity.setUserAge(dto.getUserAge());

        userDao.insertUser(entity);
    }

    @Override
    public void updateUser(UserDto dto) {
        // TODO Auto-generated method stub
        UserEntity entity = userDao.getUserByName(dto.getUserName());
        entity.setUserId(dto.getUserId());
        entity.setUserName(dto.getUserName());
        entity.setUserPw(dto.getUserPw());
        entity.setUserEmail(dto.getUserEmail());
        entity.setUserAddress(dto.getUserAddress());
        entity.setUserAge(dto.getUserAge());
        entity.setIsLogin(dto.getIsLogin());
        entity.setUserRole(dto.getUserRole());
        
        userDao.updateUser(entity);
    }

    @Override
    public void updateIsLoginByName(String userId, Boolean isLogin) {
        // TODO Auto-generated method stub
        UserEntity entity = userDao.getUserByName(userId);
        entity.setIsLogin(isLogin);
        userDao.updateUser(entity);
    }

    // 회원가입
    @Override
    public void joinUser(UserDto dto) {
        // TODO Auto-generated method stub
        // 권한 적용
        dto.setUserRole("USER");
        if(dto.getUserId().equals("admin")) {
            dto.setUserRole("ADMIN");
        } else if(dto.getUserId().equals("manager")) {
            dto.setUserRole("MANAGER");
        }

        // 비밀번호 암호화 적용
        String rawPwd = dto.getUserPw();
        String encodedPwd = bCryptPasswordEncoder.encode(rawPwd);
        dto.setUserPw(encodedPwd);

        dto.setIsLogin(false);

        // 신규 유저 database에 저장
        UserEntity entity = new UserEntity(); 
        entity.setUserId(dto.getUserId());
        entity.setUserName(dto.getUserName());
        entity.setUserPw(dto.getUserPw());
        entity.setUserEmail(dto.getUserEmail());
        entity.setUserAddress(dto.getUserAddress());
        entity.setUserAge(dto.getUserAge());
        entity.setIsLogin(dto.getIsLogin());
        entity.setUserRole(dto.getUserRole());
        
        userDao.insertUser(entity);
    }

    

    
    
}
