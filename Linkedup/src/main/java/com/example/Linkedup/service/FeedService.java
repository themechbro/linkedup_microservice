package com.example.Linkedup.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.Linkedup.dto.FeedPostDto;
import com.example.Linkedup.dto.FeedResponseDto;
import com.example.Linkedup.entity.Post;
import com.example.Linkedup.repository.CommentRepository;
import com.example.Linkedup.repository.PostRepository;
import org.springframework.data.domain.Page;




 @Service
public class FeedService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public FeedService(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository=commentRepository;
    }

    public  List<FeedPostDto> getFeedForConnections(
            List<UUID> connectionIds,
            int limit,
            int offset,
            UUID authenticatedUserId
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
// Comment Counts v 1.7.0
List <UUID> postIds= feedPosts.stream().map(Post::getPost_id).toList();

 Map<UUID, Long> commentCountMap =
            commentRepository.countCommentsByPostIds(postIds)
                    .stream()
                    .collect(Collectors.toMap(
                            projection -> projection.getPostId(),
                            projection -> projection.getCount()
                    ));
        // 5. Group + attach repost
      return feedPosts.stream()
            .map(post -> {

                long commentCount =
                        commentCountMap.getOrDefault(
                                post.getPost_id(),
                                0L
                        );

                FeedPostDto dto =
                        new FeedPostDto(post, commentCount);

                if (post.getRepostOf() != null) {
                    dto.setRepostedPost(
                            repostMap.get(post.getRepostOf())
                    );
                }

                return dto;
            })
            .toList();
    }

public FeedPostDto getLatestPostId(List<UUID> connectionIds, UUID authenticatedUserId){
       Post latest= postRepository.findTopByOwnerInOrderByCreatedAtDesc(connectionIds);
       if (latest == null) return null;
//For version 1.7.0 commentCount
    long commentCount =
        commentRepository.countCommentsByPostIds(
                List.of(latest.getPost_id())
        )
        .stream()
        .findFirst()
        .map(p -> p.getCount())
        .orElse(0L);

FeedPostDto dto =
        new FeedPostDto(latest, commentCount);

    // handle repost
    if (latest.getRepostOf() != null) {
        Post original = postRepository
                .findRepostOriginals(List.of(latest.getRepostOf()))
                .stream()
                .findFirst()
                .orElse(null);

        dto.setRepostedPost(original);
    }

    return dto;
}



public FeedResponseDto getfeedofBrands(UUID owner, int offset, int limit){

    Pageable pageable =
        PageRequest.of(offset / limit, limit);

    Page<Post> page =
        postRepository.findPostsofBrand(owner, pageable);

    List<Post> fetchedPostsofBrands =
        page.getContent();

    // Fetch repost IDs
    Set<UUID> foundRepostId =
        fetchedPostsofBrands.stream()
            .map(Post::getRepostOf)
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());

    // Fetch original reposted posts
    Map<UUID, Post> repostMap =
        foundRepostId.isEmpty()
            ? Map.of()
            : postRepository
                .findRepostOriginals(new ArrayList<>(foundRepostId))
                .stream()
                .collect(Collectors.toMap(
                    Post::getPost_id,
                    post -> post
                ));

    // Fetch comment counts
    List<UUID> postIds =
        fetchedPostsofBrands.stream()
            .map(Post::getPost_id)
            .toList();

    Map<UUID, Long> commentCountMap =
        commentRepository.countCommentsByPostIds(postIds)
            .stream()
            .collect(Collectors.toMap(
                projection -> projection.getPostId(),
                projection -> projection.getCount()
            ));

    // Build DTOs
    List<FeedPostDto> dtos =
        fetchedPostsofBrands.stream()
            .map(post -> {

                long commentCount =
                    commentCountMap.getOrDefault(
                        post.getPost_id(),
                        0L
                    );

                FeedPostDto dto =
                    new FeedPostDto(post, commentCount);

                if (post.getRepostOf() != null) {
                    dto.setRepostedPost(
                        repostMap.get(post.getRepostOf())
                    );
                }

                return dto;

            }).toList();

    return new FeedResponseDto(
        dtos,
        page.hasNext()
    );
}



}
