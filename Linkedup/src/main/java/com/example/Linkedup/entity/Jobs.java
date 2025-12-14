package com.example.Linkedup.entity;

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

    @Column(name="apply_count")
    private Integer applyCount;
}
