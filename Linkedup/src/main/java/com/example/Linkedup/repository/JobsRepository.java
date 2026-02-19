package com.example.Linkedup.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.Linkedup.entity.Jobs;

public interface JobsRepository extends JpaRepository <Jobs, UUID> {
Optional <Jobs> findByJobId(UUID jobId);

@Query(
    """
            SELECT j from Jobs j 
            WHERE j.postedBy= :postedBy
            ORDER BY j.createdAt DESC 
            LIMIT 3
            """
)
List <Jobs> find3JobsByBrandProfilePage(@Param("postedBy") UUID postedBy);
    
}
