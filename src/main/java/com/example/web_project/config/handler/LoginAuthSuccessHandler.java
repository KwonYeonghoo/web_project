package com.example.web_project.config.handler;

import java.io.IOException;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.ScriptUtils;
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

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        // TODO Auto-generated method stub
        log.info("[LoginAuthSuccessHandler][onAuthenticationSuccess] Start");

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        userService.updateIsLoginByName(userDetails.getUsername(), true);
        if (userDetails.getUsername() == null) {
            response.sendRedirect("/v1/web/index");
        } else if (userDetails.getUsername().equals("admin")) {
            response.sendRedirect("/v1/web/admin/index");
        } else {
            response.sendRedirect("/v1/web/user/index");
        }
        // ScriptUtils.alertAndMovePage(response, String.format("%s님 환영합니다!", userDetails.getUsername()), "/v1/web/user/index");
        super.onAuthenticationSuccess(request, response, authentication);
    }

}
