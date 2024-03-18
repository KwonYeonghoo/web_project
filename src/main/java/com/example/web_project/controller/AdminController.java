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
import com.example.web_project.model.Repository.PostRepository;
import com.example.web_project.model.Repository.UserRepository;
import com.example.web_project.service.UserService;
import com.example.web_project.service.impl.PostServiceImpl;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/v1/web")
@Slf4j
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostServiceImpl postService;

    @Autowired
    private PostRepository postRepository;

    // 관리자 메인화면
    @GetMapping("/admin/index")
    public String userIndexPage(Authentication authentication, Model model, @PageableDefault(page = 0,size= 6, sort="postDate" ) Pageable pageable) {
        
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        log.info("[UserController][userDetails] userName >> " + userDetails.getUsername());
        
        model.addAttribute("lt", postService.findAllByOrderByPostIdDesc(pageable));
        model.addAttribute("mostViewed", postService.findMostViewedPost());
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("check", postService.getListCheck(pageable));

        model.addAttribute("userId", userDetails.getUsername());

        return "/bootstrapMain/admin/index";
    }

    // 관리자 페이지
    @GetMapping("/admin/setting")
    public String adminSetting(Authentication authentication, Model model) {
        model.addAttribute("admin", authentication.getName());
        model.addAttribute("userlist", userRepository.findAll());
        model.addAttribute("postId", postRepository.findAll());
        log.info("[admin]: " + userRepository.findAll());

        return "/bootstrapMain/admin/memberlist";
    }

    @GetMapping("/userdelete")
    public String deleteUser(@RequestParam String id, HttpServletResponse response) throws Exception{

        System.out.println(id);
        log.info("[UserContoller][deleteUser] userId >>> "+id);

        userService.deleteUser(id);
        ScriptUtils.alertAndMovePage(response, "계정삭제완료", "/v1/web/admin/setting");

        return "redirect:/v1/web/admin/setting";
    }

    // 관리자 작성글
    @GetMapping("/admin/write")
    public String adminWritePage(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        log.info("[AdminController][adminWrite] userName >> " + userDetails.getUsername());
        return "/bootstrapMain/admin/write";
    }

    @PostMapping("/admin/write")
    public String adminWrite(@Valid @ModelAttribute PostDto dto, MultipartFile file, Authentication authentication) throws Exception{
        
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        log.info("[AdminController][adminWritePage] userDetails >>" + userDetails.getUsername());

        Date now = new Date();
        dto.setPostDate(now);
        dto.setPostWriter(userDetails.getUsername()); // 작성자 id 반환

        PostEntity entity = postService.insertPost(dto, file);

        dto.setPostFileName(entity.getPostFileName());
        dto.setPostFilePath(entity.getPostFilePath());

        log.info("[AdminController][adminWrite] dto >>> "+dto);

        return "redirect:/v1/web/admin/index";
    }

    // 관리자 게시글
    @GetMapping("/admin/post2")
    public String view(Model model, @RequestParam String postId, Authentication authentication) {
        
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userDetails.getUsername();
        log.info("[PostController][view] userName >> " + userId);

        Long longpostId = Long.parseLong(postId);
        PostDto dto = postService.getByPostId(longpostId);
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
        model.addAttribute("postViewNum", dto.getPostViewNum());
        
        return "/bootstrapMain/admin/post";
    }

    @GetMapping("/admin/postdelete")
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
            ScriptUtils.alertAndMovePage(response, "게시물을 삭제했습니다.", "/v1/web/user/index");
            return "redirect:/v1/web/user/index";
        } 
        else if (postWriter.equals(userId) || userDetails.getUsername().equals("admin")) {
            log.info("[PostController][deletePost] IF");
            postService.deletePost(postId);
            ScriptUtils.alertAndMovePage(response, "게시물을 삭제했습니다.", "/v1/web/admin/index");
            return "redirect:/v1/web/admin/index";
        } 
        else {
            log.info("[PostController][deletePost] ELSE");
            ScriptUtils.alertAndMovePage(response, "게시물을 삭제할 권한이 없습니다.", "/v1/web/admin/post2?postId="+postId);
            return "redirect:/v1/web/user/post2?postId="+postId;
            // 경고창 띄우고싶음
        }
    }

    // 관리자 수정
    @GetMapping("/admin/postupdate")
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
            
            return "/bootstrapMain/admin/update";
        } else {
            ScriptUtils.alertAndMovePage(response, "게시글을 수정할 권한이 없습니다.", "/v1/web/admin/post2?postId="+longpostId);
            return ""; // 실행안됨
        }

    }

    @PostMapping("/admin/postupdate")
    public String postupdate(@Valid @ModelAttribute PostDto dto, MultipartFile file ,@RequestParam String id, Authentication authentication, HttpServletResponse response) throws Exception{

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Long postId = Long.valueOf(id);
        PostDto post = postService.getByPostId(postId);
        
        Date now = new Date();
        post.setPostDate(now);
        post.setPostTitle(dto.getPostTitle());
        post.setPostContent(dto.getPostContent());

        postService.updatePost(post,file);

        ScriptUtils.alertAndMovePage(response, "게시물을 수정했습니다.", "/v1/web/admin/post2?postId="+postId);
        return "/v1/web/admin/post2?postId="+postId; // 실행안됨
    }

    // 관리자 게시글 삭제는 postcontroller에 권한 설정 추가함
}
