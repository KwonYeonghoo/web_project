package com.example.web_project.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.ScriptUtils;
import com.example.web_project.model.DTO.CommentDto;
import com.example.web_project.model.DTO.PostDto;
import com.example.web_project.model.Entity.CommentEntity;
import com.example.web_project.model.Entity.PostEntity;
import com.example.web_project.service.CommentService;
import com.example.web_project.service.impl.PostServiceImpl;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/user/v2/web")
@Slf4j
public class UserController {

    @Autowired
    private PostServiceImpl postService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/index")
    public String userIndexPage(Authentication authentication, Model model, @PageableDefault(page = 0,size= 6, sort="postDate" ) Pageable pageable) throws Exception {
        
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        log.info("[UserController][userDetails] userName >> " + userDetails.getUsername());
        
        // model.addAttribute("lt", postService.getAllPost(pageable));
        model.addAttribute("lt", postService.findAllByOrderByPostIdDesc(pageable));
        model.addAttribute("mostViewed", postService.findMostViewedPost());
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("check", postService.getListCheck(pageable));

        model.addAttribute("userId", userDetails.getUsername());
        return "/bootstrapMain/user/index";

    }

    @GetMapping("/write")
    public String userWritePage(Authentication authentication) throws Exception {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        log.info("[UserController][userWrite] userName >> " + userDetails.getUsername());
        return "/bootstrapMain/user/write";
    }

    @PostMapping("/write")
    public String userWrite(@Valid @ModelAttribute PostDto dto, MultipartFile file, Authentication authentication)throws Exception {
        
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        log.info("[UserController][userWritePage] userDetails >>" + userDetails.getUsername());

        Date now = new Date();
        dto.setPostDate(now);
        dto.setPostWriter(userDetails.getUsername()); // 작성자 id 반환
        //Optional.dto.getPostTitle().
        log.info("[UserController][userWrite] dto >>> "+dto);
        

        
            PostEntity entity = postService.insertPost(dto, file);
            dto.setPostFileName(entity.getPostFileName());
            dto.setPostFilePath(entity.getPostFilePath());
        
        
        
    
        
        
        

       
        

        return "redirect:/user/v2/web/index";
    }

    @GetMapping("/post2")
    public String userView(Model model, @RequestParam String postId, Authentication authentication) throws Exception {
        
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userDetails.getUsername();
        log.info("[PostController][view] userName >> " + userId);

        Long longpostId = Long.parseLong(postId);
        PostDto dto = postService.getByPostId(longpostId);
        int intpostid = Integer.parseInt(postId);
        List<CommentEntity> dto2 = commentService.commentList(intpostid);
        
        String Path = System.getProperty("user.dir") + "/src/main/resources/static/";

        File file = new File(Path + dto.getPostFilePath());

        ResponseEntity<byte[]> result = null;
        
        

        try {
			
			HttpHeaders header = new HttpHeaders();
			
			header.add("Content-type", Files.probeContentType(file.toPath()));
			

			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
			
		}catch (IOException e) {
			e.printStackTrace();
		}

        System.out.println(file.toPath());

        model.addAttribute("f_path", result);
        


        log.info("[PostController][view] PostViewNum >>> "+ dto.getPostViewNum());
        
        dto.setPostViewNum(dto.getPostViewNum() + 1);
        log.info("[PostController][view] PostViewNum >>> "+ dto.getPostViewNum());
        postService.saveDto(dto);
    

        // System.out.println(dto.toString());


        model.addAttribute("postWriter",dto.getPostWriter());
        model.addAttribute("postTitle",dto.getPostTitle());
        model.addAttribute("postContent",dto.getPostContent());
        model.addAttribute("postFilePath",dto.getPostFilePath());
        model.addAttribute("postDate",dto.getPostDate());
        model.addAttribute("postId",dto.getPostId());

        model.addAttribute("comment",dto2);
        
        
        

        return "/bootstrapMain/user/post";
    }

    @GetMapping("/postupdate")
    public String update(Model model,@RequestParam String id, Authentication authentication, HttpServletResponse response) throws Exception{
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userDetails.getUsername();

        Long longpostId = Long.parseLong(id);
        PostDto post = postService.getByPostId(longpostId);
        String postWriter = post.getPostWriter();

        if(userId.equals(postWriter)) {
            PostDto dto= postService.getByPostId(longpostId);

            model.addAttribute("postWriter",dto.getPostWriter());
            model.addAttribute("postTitle",dto.getPostTitle());
            model.addAttribute("postContent",dto.getPostContent());
            model.addAttribute("postFilePath",dto.getPostFilePath());
            model.addAttribute("postDate",dto.getPostDate());
            model.addAttribute("postId",dto.getPostId());
            model.addAttribute("postViewNum", dto.getPostViewNum());
            
            return "/bootstrapMain/user/update";
        } else {
            ScriptUtils.alertAndMovePage(response, "게시글을 수정할 권한이 없습니다.", "/user/v2/web/post2?postId="+longpostId);
            return ""; // 실행안됨
        }
    }

    @PostMapping("/postupdate")
    public String postupdate(@Valid @ModelAttribute PostDto dto, MultipartFile file ,@RequestParam String id, Authentication authentication, HttpServletResponse response) throws Exception{

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        log.info("[UserController][postupdate] userName >>> "+userDetails.getUsername());

        Long postId = Long.valueOf(id);
        PostDto post = postService.getByPostId(postId);
        
        Date now = new Date();
        post.setPostDate(now);
        post.setPostTitle(dto.getPostTitle());
        post.setPostContent(dto.getPostContent());

        postService.updatePost(post,file);

        ScriptUtils.alertAndMovePage(response, "게시물을 수정했습니다.", "/user/v2/web/post2?postId="+postId);
        return "/user/v2/web/post2?postId="+postId; // 실행안됨
    }

    @GetMapping("/postdelete")
    public String deletePost(@RequestParam String id, Authentication authentication, HttpServletResponse response) throws Exception{
        
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userDetails.getUsername();
        log.info("[PostController][deletePost] userId >>> " + userId);
        // System.out.println(Id);
        Long postId = Long.valueOf(id);
        PostDto dto = postService.getByPostId(postId);
        String postWriter = dto.getPostWriter();
        log.info("[PostController][deletePost] postWriter >>> " + postWriter);

        if (postWriter.equals(userId)) {
            log.info("[PostController][deletePost] IF");
            postService.deletePost(postId);
            ScriptUtils.alertAndMovePage(response, "게시물을 삭제했습니다.", "/user/v2/web/index");
            return "redirect:/user/v2/web/index";
        } 

        else {
            log.info("[PostController][deletePost] ELSE");
            ScriptUtils.alertAndMovePage(response, "게시물을 삭제할 권한이 없습니다.", "/user/v2/web/post2?postId="+postId);
            return "redirect:/user/v2/web/post2?postId="+postId;
            // 경고창 띄우고싶음
        }
    }

    @PostMapping("/comment")
    public String insertPost(@Valid @ModelAttribute CommentDto dto ,@RequestParam long id, Authentication authentication) throws Exception {
            
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        String userId = userDetails.getUsername();

        Date now = new Date();
        dto.setCommentDate(now);
        dto.setCommentPostid(id);
        dto.setCommentUserid(userId);
        commentService.writeComment(dto, (long)Math.random());


        return String.format("redirect:/user/v2/web/post2?postId=%d",id);
    }
}