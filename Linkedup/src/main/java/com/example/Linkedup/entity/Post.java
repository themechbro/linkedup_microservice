package com.example.Linkedup.entity;

import java.util.UUID;

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
}
