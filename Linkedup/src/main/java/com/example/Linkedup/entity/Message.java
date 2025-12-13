package com.example.Linkedup.entity;

import java.time.LocalDateTime;
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
@Table(name="messages")
public class Message {
    @Id
    @Column(name="message_id")
    private UUID messageId;

    @Column(name="sender_id")
    private UUID senderId;

    @Column(name="receiver_id")
    private UUID receiverId;

    @Column(name="content")
    private String content;

    @Column(name="read")
    private Boolean read;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @Column(name="likes")
    private Integer likes=0;



    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "liked_by", columnDefinition = "uuid[]")
    private UUID[] likedBy= new UUID[0];
}
