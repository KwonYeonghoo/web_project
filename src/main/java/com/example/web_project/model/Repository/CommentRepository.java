package com.example.web_project.model.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.web_project.model.Entity.CommentEntity;

public interface CommentRepository extends JpaRepository<CommentEntity,Long> {
    public CommentEntity getByCommentId(Long commentId);
}
