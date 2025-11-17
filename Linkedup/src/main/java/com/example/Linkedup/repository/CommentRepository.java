package com.example.Linkedup.repository;

import com.example.Linkedup.entity.Comment;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
}

