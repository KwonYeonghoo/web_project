package com.example.web_project.service;

import org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.example.web_project.exception.CustomException;
import com.example.web_project.model.DTO.PostDto;
import com.example.web_project.model.Entity.PostEntity;

public interface PostService {
    // select
    public PostDto getByPostId(Long postId) throws Exception;

    public Page<PostEntity> getAllPost(Pageable pageable) throws Exception;

    public Page<PostEntity> findAllByOrderByPostIdDesc(Pageable pageable) throws Exception;

    public PostDto findMostViewedPost() throws Exception;
    // insert
    public PostEntity insertPost(PostDto dto,MultipartFile file) throws Exception;

    // update
    public PostEntity updatePost(PostDto dto, MultipartFile file) throws Exception;

    public void saveDto(PostDto dto) throws Exception;

    // delete
    public void deletePost(Long postId) throws Exception;

    public void viewPost(PostDto dto) throws Exception;







    
}
