package com.example.Linkedup.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public Map<UUID, List<FeedPostDto>> getFeedForConnections(List<UUID> connectionIds) {

        // 1. Fetch all posts from connections (single query)
        List<Post> feedPosts =
                postRepository.findByOwnerInOrderByCreatedAtDesc(connectionIds);

        // 2. Collect repost IDs (post_id of original posts)
        Set<UUID> repostIds = feedPosts.stream()
                .map(Post::getRepostOf)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        // 3. Fetch original reposted posts (single query)
   Map<UUID, Post> repostMap =
        repostIds.isEmpty()
            ? Map.of()
            : postRepository.findRepostOriginals(new ArrayList<>(repostIds))
                .stream()
                .collect(Collectors.toMap(
                    Post::getPost_id,
                    post -> post
                ));
        // 4. Group by owner + attach reposted post
        return feedPosts.stream()
                .collect(Collectors.groupingBy(
                        Post::getOwner,
                        Collectors.mapping(post -> {
                            FeedPostDto dto = new FeedPostDto(post);

                            if (post.getRepostOf() != null) {
                                dto.setRepostedPost(
                                        repostMap.get(post.getRepostOf())
                                );
                            }

                            return dto;
                        }, Collectors.toList())
                ));
    }
}