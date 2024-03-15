package com.example.web_project.controller;

import java.io.PrintWriter;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.ScriptUtils;
import com.example.web_project.model.DTO.PostDto;
import com.example.web_project.service.impl.PostServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/v1/web")
@Slf4j
public class PostController {

    @Autowired
    private PostServiceImpl postService;
    

    //     System.out.println(id);
    //     Long postId = Long.valueOf(id);

    //     postService.deletePost(postId);

    //     return "redirect:index";
    // }

    
    
    
    @PostMapping("/write")
    public String insertPost(@Valid @ModelAttribute PostDto dto, MultipartFile file) throws Exception{
        
        Date now = new Date();
        dto.setPostDate(now);
        
        dto.setPostWriter(String.valueOf(Math.random()));
        postService.insertPost(dto,file);

        return "redirect:index";
    }

    @GetMapping("/write")
    public String write(){
        return "/bootstrapWrite/write";
    }

    
    @GetMapping("/user/post2")
    public String view(Model model, @RequestParam String postId, Authentication authentication) {
        
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userDetails.getUsername();
        log.info("[PostController][view] userName >> " + userId);

        Long longpostId = Long.parseLong(postId);
        PostDto dto = postService.getByPostId(longpostId);

    

        System.out.println(dto.toString());

        model.addAttribute("postWriter",dto.getPostWriter());
        model.addAttribute("postTitle",dto.getPostTitle());
        model.addAttribute("postContent",dto.getPostContent());
        model.addAttribute("postFilePath",dto.getPostFilePath());
        model.addAttribute("postDate",dto.getPostDate());
        model.addAttribute("postId",dto.getPostId());
        
        return "/bootstrapMain/user/post";
    }

    @GetMapping("/index")
    public String boardList(Model model, @PageableDefault(page = 0,size= 6, sort="postDate" ) Pageable pageable) {
        model.addAttribute("lt", postService.getAllPost(pageable));
        
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("check", postService.getListCheck(pageable));

        return "/bootstrapMain/index";
    }

    @GetMapping("/postupdate")
    public String update(Model model,@RequestParam String id){


        Long longpostId = Long.parseLong(id);
        PostDto dto= postService.getByPostId(longpostId);

        model.addAttribute("postWriter",dto.getPostWriter());
        model.addAttribute("postTitle",dto.getPostTitle());
        model.addAttribute("postContent",dto.getPostContent());
        model.addAttribute("postFilePath",dto.getPostFilePath());
        model.addAttribute("postDate",dto.getPostDate());
        model.addAttribute("postId",dto.getPostId());
        

        return "/bootstrapWrite/update";


    }

    @PostMapping("/postupdate")
    public String postupdate(@Valid @ModelAttribute PostDto dto, MultipartFile file ,@RequestParam String id )throws Exception{

        Date now = new Date();
        dto.setPostDate(now);
        Long postid = Long.parseLong(id);
        dto.setPostId(postid);
        dto.setPostWriter(String.valueOf(Math.random()));

        postService.updatePost(dto,file);
    

        return "redirect:index";
    }
        

        

    

    @GetMapping("/user/postdelete")
    public String deletePost(@RequestParam String id, Authentication authentication, HttpServletResponse response) throws Exception{
        
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userDetails.getUsername();
        log.info("[PostController][deletePost] userId >>> " + userId);
        // System.out.println(Id);
        Long postId = Long.valueOf(id);
        PostDto dto = postService.getByPostId(postId);
        String postWriter = dto.getPostWriter();
        log.info("[PostController][deletePost] postWrite >>> " + postWriter);

        if (postWriter.equals(userId)) {
            log.info("[PostController][deletePost] IF");
            postService.deletePost(postId);
            ScriptUtils.alertAndMovePage(response, "게시물을 삭제했습니다.", "/v1/web/user/index");
            return "redirect:/v1/web/user/index";
        } else {
            log.info("[PostController][deletePost] ELSE");
            ScriptUtils.alertAndMovePage(response, "게시물을 삭제할 권한이 없습니다.", "/v1/web/user/post2?postId="+postId);
            return "redirect:/v1/web/user/post2?postId="+postId;
            // 경고창 띄우고싶음
        }

        // return "redirect:/v1/web/user/index";
    }

    // @GetMapping("/postupdate")
    // public String updatePost(@RequestParam String id, Authentication authentication) {
        
    //     UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    //     String userId = userDetails.getUsername();
    //     log.info("[PostController][updatePost] userName >> " + userId);

    //     return "redirect:/v1/web/user/index";
    // }

}
