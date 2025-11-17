package com.example.Linkedup.entity;

import java.util.UUID;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class Profile {

    @Id
    @Column(name = "user_id")   // Supabase column
    private UUID userId;      // Java field (camelCase)

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "full_name")
    private String fullName;    // NICE: camelCase instead of full_name

    @Column(name = "headline")
    private String headline;

    @Column(name = "profile_picture")
    private String profilePicture;

    @Column(name = "cover_pic")
    private String coverPic;
}
