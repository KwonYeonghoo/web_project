package com.example.web_project.model.DAO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.web_project.model.Entity.PostEntity;

public interface PostDao {
    // select
    public PostEntity getByPostId(Long postId);

    public Page<PostEntity> getAllPost(Pageable pageable);

    public Page<PostEntity> findAllByOrderByPostIdDesc(Pageable pageable);

    public PostEntity findMostViewedPost();

    // insert
    public void insertPost(PostEntity entity);

    // update
    public void updatePost(PostEntity entity);

    // delete
    public void deletePost(Long postId);
}
