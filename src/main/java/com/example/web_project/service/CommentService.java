package com.example.web_project.service;

import java.util.List;

import com.example.web_project.model.DTO.CommentDto;
import com.example.web_project.model.Entity.CommentEntity;

public interface CommentService {


    void writeComment(CommentDto commentRequestDTO, Long boardId)  throws Exception;

    
    List<CommentEntity> commentList(int id)  throws Exception;

    
    void updateComment(CommentDto commentRequestDTO, Long commentId)  throws Exception;

    
    void deleteComment(Long commentId)  throws Exception;
}
