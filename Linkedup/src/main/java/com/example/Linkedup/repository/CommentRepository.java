package com.example.Linkedup.repository;

import com.example.Linkedup.entity.Comment;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface CommentRepository extends JpaRepository<Comment, UUID> {
@Query("""
        SELECT c FROM Comment c WHERE c.postId=:postId
        """)
    List <Comment> findCommentsForSpecificPost(@Param("postId") UUID postId);


}

