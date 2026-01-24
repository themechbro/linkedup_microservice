package com.example.Linkedup.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.Linkedup.dto.FeedPostDto;
import com.example.Linkedup.entity.Post;
import com.example.Linkedup.repository.PostRepository;



 @Service
public class FeedService {

    private final PostRepository postRepository;

    public FeedService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public  List<FeedPostDto> getFeedForConnections(
            List<UUID> connectionIds,
            int limit,
            int offset
    ) {

        // 1. Pageable (LIMIT + OFFSET)
        Pageable pageable = PageRequest.of(
                offset / limit,
                limit,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        // 2. Fetch paginated feed posts
        List<Post> feedPosts =
                postRepository.findFeedPage(connectionIds, pageable);

        // 3. Collect repost IDs
        Set<UUID> repostIds = feedPosts.stream()
                .map(Post::getRepostOf)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        // 4. Fetch repost originals
        Map<UUID, Post> repostMap =
                repostIds.isEmpty()
                        ? Map.of()
                        : postRepository.findRepostOriginals(new ArrayList<>(repostIds))
                                .stream()
                                .collect(Collectors.toMap(
                                        Post::getPost_id,
                                        post -> post
                                ));

        // 5. Group + attach repost
       return feedPosts.stream()
    .map(post -> {
        FeedPostDto dto = new FeedPostDto(post);

        if (post.getRepostOf() != null) {
            dto.setRepostedPost(
                repostMap.get(post.getRepostOf())
            );
        }

        return dto;
    })
    .toList();
    }
}
