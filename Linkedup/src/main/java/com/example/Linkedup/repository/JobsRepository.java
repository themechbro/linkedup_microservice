package com.example.Linkedup.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Linkedup.entity.Jobs;

public interface JobsRepository extends JpaRepository <Jobs, UUID> {
Optional <Jobs> findByJobId(UUID jobId);
    
}
