package com.example.web_project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.web_project.model.DAO.CommentDao;
import com.example.web_project.model.DTO.CommentDto;
import com.example.web_project.model.Entity.CommentEntity;
import com.example.web_project.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentDao commentDao;

    @Override
    public List<CommentEntity> commentList(int id)  throws Exception{
        // TODO Auto-generated method stub

        
        List<CommentEntity> postList = commentDao.findComment(id);
        return postList;
    }

    @Override
    public void deleteComment(Long commentId) throws Exception {
        // TODO Auto-generated method stub
        commentDao.deleteComment(commentId);
    }

    @Override
    public void updateComment(CommentDto dto, Long commentId)  throws Exception{
        // TODO Auto-generated method stub
        CommentEntity entity = new CommentEntity();
        entity.setCommentId(dto.getCommentId());
        entity.setCommentComment(dto.getCommentComment());
        entity.setCommentDate(dto.getCommentDate());
        entity.setCommentPostid(dto.getCommentPostid());
        entity.setCommentUserid(dto.getCommentUserid());

        commentDao.updateComment(entity);
    }

    @Override
    public void writeComment(CommentDto dto, Long boardId) throws Exception {
        // TODO Auto-generated method stub

        CommentEntity entity = new CommentEntity();
        entity.setCommentId(dto.getCommentId());
        entity.setCommentComment(dto.getCommentComment());
        entity.setCommentDate(dto.getCommentDate());
        entity.setCommentPostid(dto.getCommentPostid());
        entity.setCommentUserid(dto.getCommentUserid());

        commentDao.insertComment(entity);
        
    }
    
    
}
