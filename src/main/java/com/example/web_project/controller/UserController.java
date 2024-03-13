package com.example.web_project.controller;

import java.util.Date;

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
import org.springframework.web.multipart.MultipartFile;

import com.example.web_project.model.DTO.PostDto;
import com.example.web_project.model.DTO.UserDto;
import com.example.web_project.service.UserService;
import com.example.web_project.service.impl.PostServiceImpl;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/v1/web")
@Slf4j
public class UserController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private PostServiceImpl postService;

    @GetMapping("/user/index")
    public String userIndexPage(Authentication authentication, Model model, @PageableDefault(page = 0,size= 5, sort="postDate" ) Pageable pageable) {
        
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        log.info("[UserController][userDetails] userName >> " + userDetails.getUsername());
        
        model.addAttribute("lt", postService.getAllPost(pageable));

        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("check", postService.getListCheck(pageable));

        return "/bootstrapMain/user/index";
    }

    @GetMapping("/user/write")
    public String userWritePage(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        log.info("[UserController][userWrite] userName >> " + userDetails.getUsername());
        return "/bootstrapMain/user/write";
    }

    @PostMapping("/user/write")
    public String userWrite(@Valid @ModelAttribute PostDto dto, MultipartFile file, Authentication authentication) throws Exception{
        
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        log.info("[UserController][userWritePage] userDetails >>" + userDetails.getUsername());

        Date now = new Date();
        dto.setPostDate(now);
        
        dto.setPostWriter(String.valueOf(Math.random()));
        postService.insertPost(dto, file);

        return "redirect:/v1/web/user/index";
    }

    // @GetMapping("/user/write")
    // public String userWritePage(Authentication authentication, Model)
}