package com.example.web_project.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import com.example.ScriptUtils;
import com.example.web_project.config.constant.AuthenticationTypes;
import com.example.web_project.model.DTO.PostDto;
import com.example.web_project.model.DTO.UserDto;
import com.example.web_project.service.UserService;
import com.example.web_project.service.impl.PostServiceImpl;

import lombok.extern.slf4j.Slf4j;

/*
 * 로그인 안 된 상태의 페이지
 */

@Controller
@RequestMapping("/v2/web")
@Slf4j
public class WebController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private PostServiceImpl postService;

    @GetMapping("/post")
    public String getPost()throws Exception {
        return "/bootstrapPost/post";
    }

    @GetMapping("/loginPage")
    public String getLoginPage(@RequestParam(value = "errorMessage", required = false) String errorMessage, Model model) {
        model.addAttribute("errorMessage", errorMessage);
        return "/bootstrapJL/login";
    }

    @GetMapping("/registerPage")
    public String getRegisterPage() throws Exception{
        return "/bootstrapJL/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute UserDto dto, HttpServletResponse response) throws Exception{
        log.info("[WebController][register] dto > " + dto.toString());
        userService.joinUser(dto, response);

        ScriptUtils.alert(response, "회원가입에 성공하였습니다. 로그인 페이지로 이동합니다!");
        return "/v2/web/loginPage"; // 실행 안됨
    }

    @GetMapping("/index")
    public String boardList(Model model, @PageableDefault(page = 0,size= 6, sort="postDate" ) Pageable pageable)  throws Exception{
        // model.addAttribute("lt", postService.getAllPost(pageable));
        model.addAttribute("lt", postService.findAllByOrderByPostIdDesc(pageable));
        model.addAttribute("mostViewed", postService.findMostViewedPost());
        log.info("[PostController][boardList] mostViewed >>> "+postService.findMostViewedPost());
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("check", postService.getListCheck(pageable));

        return "/bootstrapMain/index";
    }

    @GetMapping("/post2")
    public String indexView(Model model, @RequestParam String postId)  throws Exception{
        
        Long longpostId = Long.parseLong(postId);
        PostDto dto = postService.getByPostId(longpostId);
        log.info("[PostController][view] PostViewNum >>> "+ dto.getPostViewNum());
        
        dto.setPostViewNum(dto.getPostViewNum() + 1);
        log.info("[PostController][view] PostViewNum >>> "+ dto.getPostViewNum());
        postService.saveDto(dto);

        model.addAttribute("postWriter",dto.getPostWriter());
        model.addAttribute("postTitle",dto.getPostTitle());
        model.addAttribute("postContent",dto.getPostContent());
        model.addAttribute("postFilePath",dto.getPostFilePath());
        model.addAttribute("postDate",dto.getPostDate());
        model.addAttribute("postId",dto.getPostId());
        model.addAttribute("postViewNum", dto.getPostViewNum());
        
        return "/bootstrapPost/post";
    }

    @GetMapping("/checkDuplicate")
    public void checkDuplicate(@RequestParam String userId, HttpServletResponse response) throws IOException{
        userService.checkDuplicate(userId, response);
    }
}