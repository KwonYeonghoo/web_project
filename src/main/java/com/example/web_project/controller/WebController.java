package com.example.web_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import jakarta.validation.Valid;


import com.example.web_project.model.DTO.UserDto;
import com.example.web_project.service.UserService;
import com.example.web_project.service.impl.PostServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/v1/web")
@Slf4j
public class WebController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private PostServiceImpl postService;

    @GetMapping("/post")
    public String getPost() {
        return "/bootstrapPost/post";
    }

    @GetMapping("/loginPage")
    public String getLoginPage() {
        return "/bootstrapJL/login";
    }

    @GetMapping("/registerPage")
    public String getRegisterPage() {
        return "/bootstrapJL/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute UserDto dto) {
        log.info("[WebController][register] dto > " + dto.toString());
        userService.joinUser(dto);
        return "redirect:/v1/web/loginPage";
    }

    
}