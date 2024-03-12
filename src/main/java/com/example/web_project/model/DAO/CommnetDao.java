package com.example.web_project.model.DAO;

import java.util.List;

import com.example.web_project.model.Entity.CommentEntity;



public interface CommnetDao {

    public CommentEntity getByCommentId(Long commentId);

    public List<CommentEntity> getAllComment();

    // insert
    public void insertComment(CommentEntity entity);

    // update
    public void updateComment(CommentEntity entity);

    // delete
    public void deleteComment(Long commentId);
} 