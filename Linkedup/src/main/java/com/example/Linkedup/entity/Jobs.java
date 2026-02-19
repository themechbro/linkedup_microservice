package com.example.Linkedup.entity;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="jobs")
public class Jobs {
    @Id
    @Column(name="id")
    private UUID jobId;

    @Column(name="title")
    private String title;

    @Column(name="company")
    private String company;

    @Column(name="location")
    private String location;

    @Column(name="job_type")
    private String jobType;

    @Column(name="description")
    private String description;

    @Column(name="posted_by")
    private UUID postedBy;

    @Column(name = "is_brand")
    private Boolean isBrand;

    @Column(name="created_at")
    private Instant createdAt;

     @Column(name="updated_at")
    private Instant updatedAt;

     @Column(name="status")
    private String status;

    @Column(name="applylink")
    private String applyLink;

    @Column(name="apply_count")
    private Integer applyCount;
}
