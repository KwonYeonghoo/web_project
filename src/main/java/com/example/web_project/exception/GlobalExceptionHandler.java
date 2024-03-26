package com.example.web_project.exception;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.ScriptUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    @ExceptionHandler(value = Exception.class)
    protected  void handleCustomException(Exception e,HttpServletResponse response,HttpServletRequest request) throws IOException {
        
        log.info("오류 : "+e.getMessage());
        ScriptUtils.alertAndBackPage(response, e.getMessage());
        
        

    
    }

    // @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    // protected String handleHttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException e,HttpServletRequest request) {
    //     log.error("handleHttpRequestMethodNotSupportedException: {}", e.getMessage());
    //     String preUrl = request.getHeader("Referer");
    //     return "redirect:"+ preUrl;
    // }

    // @ExceptionHandler(Exception.class)
    // public String handleException(final Exception e,HttpServletRequest request) {
    //     log.error("handleException: {}", e.getMessage());
    //     String preUrl = request.getHeader("Referer");
    //     return "redirect:"+ preUrl;
    // }



}
