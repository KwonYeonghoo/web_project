package com.example.web_project.model.DAO.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.web_project.model.DAO.CommentDao;
import com.example.web_project.model.Entity.CommentEntity;
import com.example.web_project.model.Repository.CommentRepository;


public class CommentDaoImpl implements CommentDao{

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public void deleteComment(Long commentId) {
        // TODO Auto-generated method stub
        commentRepository.deleteById(commentId);
    }

    @Override
    public List<CommentEntity> getAllComment() {
        // TODO Auto-generated method stub

        return commentRepository.findAll();
    }

    @Override
    public CommentEntity getByCommentId(Long commentId) {
        // TODO Auto-generated method stub
        return commentRepository.getByCommentId(commentId);
    }

    @Override
    public void insertComment(CommentEntity entity) {
        // TODO Auto-generated method stub
        commentRepository.save(entity);
    }

    @Override
    public void updateComment(CommentEntity entity) {
        // TODO Auto-generated method stub
        commentRepository.save(entity);
    }
    
}
