// package com.example.Linkedup.dto;

// import java.time.Instant;
// import java.util.UUID;

// import com.example.Linkedup.entity.Post;

// import lombok.Data;

// @Data
// public class FeedPostDto {

//     private UUID postId;
//     private UUID owner;
//     private String content;
//     private String mediaUrl;
//     private Instant createdAt;
//     private UUID repostOf;
//     private Integer likes;
//     private UUID[] likedBy;
//     private Integer repostCount;
//     private String staTus;

//     private Post repostedPost;

//     public FeedPostDto(Post post) {
//         this.postId = post.getPost_id();   // respecting your constraint
//         this.owner = post.getOwner();
//         this.content = post.getContent();
//         this.mediaUrl = post.getMediaUrl();
//         this.createdAt = post.getCreatedAt();
//         this.repostOf = post.getRepostOf();
//          this.likes = post.getLiKes();
//         this.likedBy = post.getLikedBy();
//         this.repostCount = post.getRepostCount();
//         this.staTus = post.getStaTus();
//     }
// }



package com.example.Linkedup.dto;

import java.time.Instant;
import java.util.UUID;

import com.example.Linkedup.entity.Post;

import lombok.Data;

@Data
public class FeedPostDto {

    // ─── FEED POST (WRAPPER) ─────────────────────────────
    private UUID postId;
    private UUID owner;            // reposter OR original owner
    private Instant createdAt;

    // ─── CONTENT (FOR NORMAL POSTS) ─────────────────────
    private String content;
    private String mediaUrl;

    // ─── REPOST INFO ────────────────────────────────────
    private UUID repostOf;
    private Post repostedPost;     // original post

    // ─── ENGAGEMENT ─────────────────────────────────────
    private Integer likes;
    private UUID[] likedBy;
    private Integer repostCount;
    private String staTus;

    // ─── 🆕 REPOSTER PROFILE (CRITICAL FIX) ──────────────
    private String reposterUsername;
    private String reposterFullName;
    private String reposterProfilePicture;

    // ─── CONSTRUCTOR ────────────────────────────────────
    public FeedPostDto(Post post) {
        this.postId = post.getPost_id();   // respecting your constraint
        this.owner = post.getOwner();
        this.content = post.getContent();
        this.mediaUrl = post.getMediaUrl();
        this.createdAt = post.getCreatedAt();
        this.repostOf = post.getRepostOf();

        this.likes = post.getLiKes();
        this.likedBy = post.getLikedBy();
        this.repostCount = post.getRepostCount();
        this.staTus = post.getStaTus();
    }
}

