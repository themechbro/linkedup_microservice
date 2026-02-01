package com.example.Linkedup.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.Linkedup.entity.Post;

public interface PostRepository extends JpaRepository <Post, UUID>{


    List <Post> findByOwner(UUID owner);
    List<Post> findTop10ByOwnerOrderByCreatedAtDesc(UUID owner);
   List<Post> findByOwnerInOrderByCreatedAtDesc(List<UUID> owners);



   @Query("""
           SELECT p FROM Post p WHERE p.owner IN:owners
           ORDER BY p.createdAt DESC
           """)
           List<Post> findFeedPage(
    @Param("owners") List<UUID> owners,
    org.springframework.data.domain.Pageable pageable
);
    // repost originals (EXPLICIT QUERY — REQUIRED)
    @Query("SELECT p FROM Post p WHERE p.post_id IN :ids")
    List<Post> findRepostOriginals(@Param("ids") List<UUID> ids);

    
@Query("""
    SELECT p FROM Post p
    WHERE p.owner IN :owners
    ORDER BY p.createdAt DESC
""")
List<Post> findLatestPostFromConnections(
        @Param("owners") List<UUID> owners,
        Pageable pageable
);

}





