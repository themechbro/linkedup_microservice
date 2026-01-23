package com.example.Linkedup.controller;

import java.time.Instant;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Linkedup.dto.ApiResponse;
import com.example.Linkedup.entity.Post;
import com.example.Linkedup.service.PostService;

// @RestController
// @RequestMapping("/api/posts")
// public class PostController {
    
//     private final PostService postService;

//     public PostController(PostService postService){
//         this.postService=postService;
//     }

//     @DeleteMapping("/{postId}")
//     public ResponseEntity<?> deletePost(@PathVariable UUID postId){
//         boolean deleted= postService.deletePost(postId);

// if (!deleted) {
//             return ResponseEntity.status(404).body(
//                 new ApiResponse(false, "Post not found")
//             );
//         }
//         return ResponseEntity.ok(
//             new ApiResponse(true, "Post deleted successfully")
//         );

//     }
// }

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService){
        this.postService = postService;
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(
            @PathVariable UUID postId,
            @RequestHeader("X-User-Id") UUID requesterId   // 👈 The logged-in user's ID
    ) {

        // 1. Check if post exists
        Post post = postService.getPostById(postId);
        if (post == null) {
            return ResponseEntity.status(404).body(
                new ApiResponse(false, "Post not found", Instant.now())
            );
        }

        // 2. Check ownership
        if (!post.getOwner().equals(requesterId)) {
            return ResponseEntity.status(403).body(
                new ApiResponse(false, "You are not allowed to delete this post", Instant.now())
            );
        }

        // 3. Delete post
        postService.deletePost(postId);

        return ResponseEntity.ok(
            new ApiResponse(true, "Post deleted successfully", Instant.now())
        );
    }

}




