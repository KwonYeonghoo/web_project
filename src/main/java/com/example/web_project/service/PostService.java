package com.example.web_project.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.example.web_project.model.DAO.CommentDao;
import com.example.web_project.model.DTO.CommentDto;
import com.example.web_project.model.DTO.PostDto;
import com.example.web_project.model.Entity.PostEntity;

public interface PostService {
    // select
    public PostDto getByPostId(Long postId);

    public Page<PostEntity> getAllPost(Pageable pageable);

    public Page<PostEntity> findAllByOrderByPostIdDesc(Pageable pageable);

    public PostDto findMostViewedPost();
    // insert
    public PostEntity insertPost(PostDto dto,MultipartFile file) throws Exception;

    // update
    public PostEntity updatePost(PostDto dto, MultipartFile file) throws Exception;

    public void saveDto(PostDto dto);

    // delete
    public void deletePost(Long postId);

    public void viewPost(PostDto dto);







    
}
