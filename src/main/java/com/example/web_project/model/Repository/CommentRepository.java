package com.example.web_project.model.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.web_project.model.Entity.CommentEntity;


public interface CommentRepository extends JpaRepository<CommentEntity,Long> {
     
    @Query(value = "select * from comment  where  comment_postid= :postid",nativeQuery = true)
    public List<CommentEntity> findComment(@Param("postid") int postid);
}
