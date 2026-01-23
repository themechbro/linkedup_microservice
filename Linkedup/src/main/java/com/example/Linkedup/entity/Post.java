package com.example.Linkedup.entity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="posts")
public class Post {
    
    @Id
    @Column(name="id")
    private UUID post_id;

@Column(name="owner")
private UUID owner;

@Column(name="content")
 private String content;

 @Column(columnDefinition = "json")
 private String mediaUrl;

 @Column (name="likes")
 private Integer liKes;

@JdbcTypeCode(SqlTypes.ARRAY)
 @Column(name="liked_by", columnDefinition="uuid[]")
 private  UUID[] likedBy;

 @Column (name="status")
 private String staTus;

 @Column(name="repost_of")
 private UUID repostOf;

  @Column (name="repost_count")
 private Integer repostCount;

 @Column (name="created_at")
 private Instant createdAt;
}



