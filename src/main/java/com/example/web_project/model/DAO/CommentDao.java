package com.example.web_project.model.DAO;

import java.util.List;

import com.example.web_project.model.Entity.CommentEntity;



public interface CommentDao {

    public CommentEntity getByCommentId(Long commentId);

    public List<CommentEntity> getAllComment();

    // insert
    public void insertComment(CommentEntity entity);

    // update
    public void updateComment(CommentEntity entity);

    // delete
    public void deleteComment(Long commentId);

    public List<CommentEntity> findComment(int id);
} 