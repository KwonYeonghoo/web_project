package com.example.web_project.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.example.web_project.model.DTO.PostDto;
import com.example.web_project.model.Entity.PostEntity;

public interface PostService {
    // select
    public PostDto getByPostId(Long postId);

    public Page<PostEntity> getAllPost(Pageable pageable);

    // insert
    public void insertPost(PostDto dto,MultipartFile file) throws Exception;

    // update
    public void updatePost(PostDto dto);

    // delete
    public void deletePost(Long postId);

    public void viewPost(PostDto dto);

    
}
