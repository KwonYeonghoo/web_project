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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.ScriptUtils;
import com.example.web_project.model.DTO.PostDto;
import com.example.web_project.model.Entity.PostEntity;
import com.example.web_project.service.UserService;
import com.example.web_project.service.impl.PostServiceImpl;

import jakarta.servlet.http.HttpServletResponse;
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
    public String userIndexPage(Authentication authentication, Model model, @PageableDefault(page = 0,size= 6, sort="postDate" ) Pageable pageable) {
        
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
        dto.setPostWriter(userDetails.getUsername()); // 작성자 id 반환

        PostEntity entity = postService.insertPost(dto, file);

        dto.setPostFileName(entity.getPostFileName());
        dto.setPostFilePath(entity.getPostFilePath());

        log.info("[UserController][userWrite] dto >>> "+dto);

        return "redirect:/v1/web/user/index";
    }

    // @GetMapping("/user/write")
    // public String userWritePage(Authentication authentication, Model)

    @GetMapping("/userdelete")
    public String deleteUser(@RequestParam String id, HttpServletResponse response) throws Exception{

        System.out.println(id);
        log.info("[UserContoller][deleteUser] userId >>> "+id);

        userService.deleteUser(id);
        ScriptUtils.alertAndMovePage(response, "계정삭제완료", "/v1/web/admin/setting");

        return "redirect:/v1/web/admin/setting";
    }
}