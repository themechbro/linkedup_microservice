package com.example.Linkedup.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Linkedup.entity.Post;

public interface PostRepository extends JpaRepository <Post, UUID>{

    
}





