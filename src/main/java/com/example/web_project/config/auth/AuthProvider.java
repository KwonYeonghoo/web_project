package com.example.web_project.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class AuthProvider implements AuthenticationProvider{
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthUserService securityUserService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // TODO Auto-generated method stub
        log.info("[AuthProvider][authenticate] Start");

        String name = authentication.getName();
        String pwd = (String)authentication.getCredentials();
        log.info("[AuthProvider][authenticate] name: "+name+" / pwd: "+pwd);

        UserDetails userDetails = (AuthUserDto)securityUserService.loadUserByUsername(name); // 인증에 성공한 user의 객체
        log.info("[AuthProvider][authenticate] userDetails >>> " + userDetails);
        if(userDetails == null) {
            throw new UsernameNotFoundException("There is no username >>> " + name);
        }
        else if (isNotMatches(pwd, userDetails.getPassword())) {
            throw new BadCredentialsException("Your password is incorrect.");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // TODO Auto-generated method stub
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    private boolean isNotMatches(String password, String encodePassword) {
        return !bCryptPasswordEncoder.matches(password, encodePassword);
    }
    
}
