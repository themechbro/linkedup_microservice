package com.example.Linkedup.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.Linkedup.entity.Jobs;
import com.example.Linkedup.entity.Profile;


public interface JobsRepository extends JpaRepository <Jobs, UUID> {
Optional <Jobs> findByJobId(UUID jobId);

@Query(
    """
            SELECT j, p
            from Jobs j
            JOIN Profile p ON j.postedBy=p.userId 
            WHERE j.postedBy= :postedBy
            ORDER BY j.createdAt DESC 
            """
)
List <Object[]> findJobsByBrandProfilePage(@Param("postedBy") UUID postedBy, Pageable pageable);
    
}
