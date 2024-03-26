package com.example.web_project.model.DAO.impl;



import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.web_project.exception.CustomException;
import com.example.web_project.model.DAO.PostDao;
import com.example.web_project.model.Entity.PostEntity;
import com.example.web_project.model.Repository.PostRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PostDaoImpl implements PostDao{

    @Autowired
    private PostRepository postRepository;

    @Override
    public void deletePost(Long postId) {
        // TODO Auto-generated method stub
        postRepository.deleteById(postId);
    }

    @Override
    public Page<PostEntity> getAllPost(Pageable page) {
        // TODO Auto-generated method stub
        return postRepository.findAll(page);
    }


    @Override
    public Page<PostEntity> findAllByOrderByPostIdDesc(Pageable page) {
        // TODO Auto-generated method stub
        return postRepository.findAllByOrderByPostIdDesc(page);
    }

    

    @Override
    public PostEntity findMostViewedPost() {
        // TODO Auto-generated method stub
        return postRepository.findMostViewedPost();
    }
    

    @Override
    public PostEntity getByPostId(Long postId) {
        // TODO Auto-generated method stub
         return postRepository.getByPostId(postId);
    }

    @Override
    public void insertPost(PostEntity entity)   {
        // TODO Auto-generated method stub
        
        postRepository.save(entity);
       
        
    }

    @Override
    public void updatePost(PostEntity entity) {
        // TODO Auto-generated method stub
        postRepository.save(entity);
    }
    
}
