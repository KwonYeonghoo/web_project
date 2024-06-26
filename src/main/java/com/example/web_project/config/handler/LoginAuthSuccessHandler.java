package com.example.web_project.config.handler;

import java.io.IOException;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.JmsProperties.Listener.Session;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.web_project.config.SessionManager;
import com.example.web_project.model.DTO.UserDto;
import com.example.web_project.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LoginAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
    
    @Autowired
    @Lazy
    private UserService userService;

    @Autowired
    private SessionManager sessionManager;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        // TODO Auto-generated method stub
        log.info("[LoginAuthSuccessHandler][onAuthenticationSuccess] Start");

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        userService.updateIsLoginByName(userDetails.getUsername(), true);
        String userName = userDetails.getUsername();

        UserDto userDto = userService.getUserByName(userName);
        // SessionManager.createSession();

        if (userName == null) {
            response.sendRedirect("/v2/web/index");
        } else 
        
        if (userDto.getUserRole().equals("ADMIN")) {
            response.sendRedirect("/admin/v2/web/index");
        } else {
            response.sendRedirect("/user/v2/web/index");
        }
        // ScriptUtils.alertAndMovePage(response, String.format("%s님 환영합니다!", userDetails.getUsername()), "/v1/web/user/index");
        super.onAuthenticationSuccess(request, response, authentication);
    }

}
