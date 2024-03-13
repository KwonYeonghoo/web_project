package com.example.web_project.controller;

import java.util.Date;

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
import org.springframework.web.multipart.MultipartFile;

import com.example.web_project.model.DTO.PostDto;
import com.example.web_project.service.impl.PostServiceImpl;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/v1/web")
public class PostController {

    @Autowired
    private PostServiceImpl postService;
    
    @GetMapping("/postdelete")
    public String deletePost(@RequestParam String id) {

        System.out.println(id);
        Long postId = Long.valueOf(id);

        postService.deletePost(postId);

        return "redirect:/v1/web/index";
    }

   
    
    
    @PostMapping("/write")
    public String insertPost(@Valid @ModelAttribute PostDto dto, MultipartFile file) throws Exception{
        
        Date now = new Date();
        dto.setPostDate(now);
        
        dto.setPostWriter(String.valueOf(Math.random()));
        postService.insertPost(dto,file);

        return "redirect:/v1/web/index";
    }

    @GetMapping("/write")
    public String write(){
        return "/bootstrapWrite/write";
    }

    
    @GetMapping("/post2")
    public String view(Model model, @RequestParam String postId) {


        Long longpostId = Long.parseLong(postId);
        PostDto dto = postService.getByPostId(longpostId);

    

        System.out.println(dto.toString());

        model.addAttribute("postWriter",dto.getPostWriter());
        model.addAttribute("postTitle",dto.getPostTitle());
        model.addAttribute("postContent",dto.getPostContent());
        model.addAttribute("postFilePath",dto.getPostFilePath());
        model.addAttribute("postDate",dto.getPostDate());
        model.addAttribute("postId",dto.getPostId());
        
        

        return "/bootstrapPost/post";
    }

    @GetMapping("/index")
    public String boardList(Model model, @PageableDefault(page = 0,size= 5, sort="postDate" ) Pageable pageable) {
        model.addAttribute("lt", postService.getAllPost(pageable));
        
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("check", postService.getListCheck(pageable));

        
        return "/bootstrapMain/index";
    }

    // @GetMapping("/user/index")
    // public String boardListUser(Model model, @PageableDefault(page = 0,size= 5, sort="postDate" ) Pageable pageable) {
    //     model.addAttribute("lt", postService.getAllPost(pageable));
        
    //     model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
    //     model.addAttribute("next", pageable.next().getPageNumber());
    //     model.addAttribute("check", postService.getListCheck(pageable));

        
    //     return "/bootstrapMain/user/index";
    // }
}
