package com.example.web_project.model.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.web_project.model.Entity.PostEntity;

public interface PostRepository extends JpaRepository<PostEntity, Long>{
    public PostEntity getByPostId(Long postId);

    public Page<PostEntity> findAllByOrderByPostIdDesc(Pageable pageable);

    @Query(value = "select * from post order by post_view_num desc limit 1", nativeQuery = true)
    public PostEntity findMostViewedPost();
}
