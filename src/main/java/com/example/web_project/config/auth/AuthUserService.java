package com.example.web_project.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.web_project.model.DTO.UserDto;
import com.example.web_project.service.UserService;

@Service
public class AuthUserService implements UserDetailsService{

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        UserDto userDto = userService.getUserByName(userId);
        // UserDto dto = new UserDto();
        // dto.setUserId(userEntity.getUserId());
        // dto.setUserName(userEntity.getUserName());
        // dto.setUserPw(userEntity.getUserPw());
        // dto.setUserEmail(userEntity.getUserEmail());
        // dto.setUserAge(userEntity.getUserAge());
        // dto.setUserAddress(userEntity.getUserAddress());
        // dto.setUserRole(userEntity.getUserRole());
        // dto.setIsLogin(userEntity.getIsLogin());

        if(userDto != null) {
            return new AuthUserDto(userDto); // 인증에 성공한 user 객체
        }
        // 존재하지 않음
        return null;
    }

    
}
