package com.example.web_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import com.example.ScriptUtils;
import com.example.web_project.model.DTO.UserDto;
import com.example.web_project.model.Repository.PostRepository;
import com.example.web_project.model.Repository.UserRepository;
import com.example.web_project.service.UserService;
import com.example.web_project.service.impl.PostServiceImpl;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/v1/web")
@Slf4j
public class WebController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostServiceImpl postService;

    @Autowired
    private PostRepository postRepository;

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
    public String register(@Valid @ModelAttribute UserDto dto, HttpServletResponse response) throws Exception{
        log.info("[WebController][register] dto > " + dto.toString());
        userService.joinUser(dto);

        ScriptUtils.alertAndMovePage(response, "회원가입에 성공하였습니다. 로그인 페이지로 이동합니다!", "/v1/web/loginPage");
        return "/v1/web/loginPage"; // 실행 안됨
    }

    @GetMapping("/admin/setting")
    public String adminSetting(Authentication authentication, Model model) {
        model.addAttribute("admin", authentication.getName());
        model.addAttribute("userlist", userRepository.findAll());
        model.addAttribute("postId", postRepository.findAll());
        log.info("[admin]: " + userRepository.findAll());

        return "/bootstrapMain/admin/index";
    }
    
}