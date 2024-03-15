package com.example.web_project.model.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.web.multipart.MultipartFile;

import com.example.web_project.model.DTO.PostDto;
import com.example.web_project.model.Entity.PostEntity;

public interface PostRepository extends JpaRepository<PostEntity, Long>{
    public PostEntity getByPostId(Long postId);

}
