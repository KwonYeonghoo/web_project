package com.example.web_project.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.web_project.model.DAO.PostDao;
import com.example.web_project.model.DTO.PostDto;
import com.example.web_project.model.Entity.PostEntity;
import com.example.web_project.service.PostService;

import jakarta.transaction.Transactional;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    private PostDao postDao;

    @Override
    public void deletePost(Long postId) {
        // TODO Auto-generated method stub
        postDao.deletePost(postId);
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

        return dto;
    }

    @Override
    public void insertPost(PostDto dto , MultipartFile file) throws Exception {
        // TODO Auto-generated method stub
        PostEntity entity = new PostEntity();
        entity.setPostId(dto.getPostId());
        entity.setPostTitle(dto.getPostTitle());
        entity.setPostContent(dto.getPostContent());
        entity.setPostWriter(dto.getPostWriter());
        entity.setPostDate(dto.getPostDate());

        //이미지 파일 삽입 


        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files";

        File sfile = new File(projectPath);
        if(!sfile.exists()) {
            sfile.mkdir();
        }

        UUID uuid =UUID.randomUUID();

        String fileName = uuid+"_"+file.getOriginalFilename();

        File saveFile = new File(projectPath,fileName);
       
        file.transferTo(saveFile);
       
        entity.setPostFileName(fileName);
        entity.setPostFilePath("/files/"+fileName);

        System.out.println(fileName);

        postDao.insertPost(entity);
    }

    @Override
    public void updatePost(PostDto dto) {
        // TODO Auto-generated method stub
        PostEntity entity = postDao.getByPostId(dto.getPostId());
        entity.setPostTitle(dto.getPostTitle());
        entity.setPostContent(dto.getPostContent());
        entity.setPostWriter(dto.getPostWriter());
        entity.setPostDate(dto.getPostDate());

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
