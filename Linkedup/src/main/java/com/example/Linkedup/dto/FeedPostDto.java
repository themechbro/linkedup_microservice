package com.example.Linkedup.dto;

import java.time.Instant;
import java.util.UUID;

import com.example.Linkedup.entity.Post;

import lombok.Data;

@Data
public class FeedPostDto {

    private UUID postId;
    private UUID owner;
    private String content;
    private String mediaUrl;
    private Instant createdAt;
    private UUID repostOf;

    private Post repostedPost;

    public FeedPostDto(Post post) {
        this.postId = post.getPost_id();   // respecting your constraint
        this.owner = post.getOwner();
        this.content = post.getContent();
        this.mediaUrl = post.getMediaUrl();
        this.createdAt = post.getCreatedAt();
        this.repostOf = post.getRepostOf();
    }
}


