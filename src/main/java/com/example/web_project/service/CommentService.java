package com.example.web_project.service;

import java.util.List;

import com.example.web_project.model.DTO.CommentDto;
import com.example.web_project.model.Entity.CommentEntity;

public interface CommentService {


    void writeComment(CommentDto commentRequestDTO, Long boardId);

  
    List<CommentEntity> commentList(int id);

   
    void updateComment(CommentDto commentRequestDTO, Long commentId);

   
    void deleteComment(Long commentId);
}
