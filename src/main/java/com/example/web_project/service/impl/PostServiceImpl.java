package com.example.web_project.service.impl;

import java.io.File;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.web_project.model.DAO.CommentDao;
import com.example.web_project.model.DAO.PostDao;
import com.example.web_project.model.DTO.PostDto;
import com.example.web_project.model.Entity.PostEntity;
import com.example.web_project.service.PostService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PostServiceImpl implements PostService{

    @Autowired
    private PostDao postDao;

    @Autowired
    private CommentDao commentDao;

    @Override
    public void deletePost(Long postId) {
        // TODO Auto-generated method stub
        PostEntity entity = postDao.getByPostId(postId);
        postDao.deletePost(entity.getPostId());
    }

    

    @Override
    public Page<PostEntity> getAllPost(Pageable pageable) {
        // TODO Auto-generated method stub
        Page<PostEntity> postList = postDao.getAllPost(pageable);
        // Page<PostDto> dtoList = new ArrayList<>();
        // for(PostEntity post : postList) {
        //     PostDto dto = new PostDto();
        //     dto.setPostId(post.getPostId());
        //     dto.setPostTitle(post.getPostTitle());
        //     dto.setPostContent(post.getPostContent());
        //     dto.setPostWriter(post.getPostWriter());
        //     dto.setPostDate(post.getPostDate());
        //     dto.setPostTitle(post.getPostTitle());
        //     dto.setPostFilePath(post.getPostFilePath());

        //     dtoList.add(dto);
        // }
        return postList;
    }

    

    @Override
    public Page<PostEntity> findAllByOrderByPostIdDesc(Pageable pageable) {
        // TODO Auto-generated method stub
        Page<PostEntity> postList = postDao.findAllByOrderByPostIdDesc(pageable);
        return postList;
    }

    @Override
    public PostDto getByPostId(Long postId) {
        // TODO Auto-generated method stub
        PostEntity post = postDao.getByPostId(postId);
        PostDto dto = new PostDto();

        dto.setPostId(post.getPostId());
        dto.setPostTitle(post.getPostTitle());
        dto.setPostContent(post.getPostContent());
        dto.setPostWriter(post.getPostWriter());
        dto.setPostDate(post.getPostDate());
        dto.setPostFileName(post.getPostFileName());
        dto.setPostFilePath(post.getPostFilePath());
        dto.setPostViewNum(post.getPostViewNum());

        return dto;
    }

    

    @Override
    public PostDto findMostViewedPost() {
        // TODO Auto-generated method stub
        PostEntity post = postDao.findMostViewedPost();
        PostDto dto = new PostDto();
        if(post == null){
            return null;
        }
        dto.setPostId(post.getPostId());
        dto.setPostTitle(post.getPostTitle());
        dto.setPostContent(post.getPostContent());
        dto.setPostDate(post.getPostDate());
        dto.setPostWriter(post.getPostWriter());
        dto.setPostFileName(post.getPostFileName());
        dto.setPostFilePath(post.getPostFilePath());
        dto.setPostViewNum(post.getPostViewNum());

        return dto;
    }

    @Override
    public PostEntity insertPost(PostDto dto , MultipartFile file) throws Exception {
        // TODO Auto-generated method stub
        PostEntity entity = new PostEntity();
        entity.setPostId(dto.getPostId());
        entity.setPostTitle(dto.getPostTitle());
        entity.setPostContent(dto.getPostContent());
        entity.setPostWriter(dto.getPostWriter());
        entity.setPostDate(dto.getPostDate());

        //이미지 파일 삽입

        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files";
        log.info("[PostServiceImpl][insertPost] projectPath >>> "+projectPath);
        File sfile = new File(projectPath);
        if(!sfile.exists()) {
            sfile.mkdir();
        }

        UUID uuid =UUID.randomUUID();

        String fileName;
        if(file.getOriginalFilename().equals("")){
            fileName = "_default_image.png";
            entity.setPostFileName(fileName);
            entity.setPostFilePath("/files/"+fileName);
        } else {
            fileName = uuid+"_"+file.getOriginalFilename();
            File saveFile = new File(projectPath,fileName);
            log.info("[PostServiceImpl][insertPost] saveFile >>> "+saveFile);
            file.transferTo(saveFile);
            log.info("[PostServiceImpl][insertPost] file.getOriginalFilename >>> "+file.getOriginalFilename());
            entity.setPostFileName(fileName);
            entity.setPostFilePath("/files/"+fileName);
        }

        log.info("[PostServiceImpl][insertPost] entity >>> "+entity);

        postDao.insertPost(entity);
        
        return entity;
    }

    @Override
    public PostEntity updatePost(PostDto dto, MultipartFile file) throws Exception {
        // TODO Auto-generated method stub
        PostEntity entity = postDao.getByPostId(dto.getPostId());
        entity.setPostTitle(dto.getPostTitle());
        entity.setPostContent(dto.getPostContent());
        entity.setPostWriter(dto.getPostWriter());
        entity.setPostDate(dto.getPostDate());
    

        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files";

        File sfile = new File(projectPath);
        if(!sfile.exists()) {
            sfile.mkdir();
        }
        UUID uuid =UUID.randomUUID();
        String fileName;
        if(file.getOriginalFilename().equals("")){
            fileName = "_default_image.png";
            entity.setPostFileName(fileName);
            entity.setPostFilePath("/files/"+fileName);
        } else {
            fileName = uuid+"_"+file.getOriginalFilename();
            File saveFile = new File(projectPath,fileName);
            log.info("[PostServiceImpl][insertPost] saveFile >>> "+saveFile);
            file.transferTo(saveFile);
            log.info("[PostServiceImpl][insertPost] file.getOriginalFilename >>> "+file.getOriginalFilename());
            entity.setPostFileName(fileName);
            entity.setPostFilePath("/files/"+fileName);
        }

        
        System.out.println("Entity :"+ entity.toString());

        postDao.updatePost(entity);

        return entity;
    }


    
    @Override
    public void saveDto(PostDto dto) {
        // TODO Auto-generated method stub
        PostEntity entity = postDao.getByPostId(dto.getPostId());
        entity.setPostViewNum(dto.getPostViewNum());
        postDao.updatePost(entity);
    }

    @Override
    public void viewPost(PostDto dto) {
        // TODO Auto-generated method stub
        PostEntity entity = postDao.getByPostId(dto.getPostId());
        System.err.println(entity.toString());
    }
    @Transactional
    public Boolean getListCheck(Pageable pageable) {
        Page<PostEntity> saved = getAllPost(pageable);
        Boolean check = saved.hasNext();

        return check;
    }



   

}
