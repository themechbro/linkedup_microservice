package com.example.Linkedup.dto;

import java.util.List;

public class FeedResponseDto {
     private List<FeedPostDto> posts;
    private boolean hasMore;

    public FeedResponseDto(
        List<FeedPostDto> posts,
        boolean hasMore
    ){
        this.posts = posts;
        this.hasMore = hasMore;
    }

    public List<FeedPostDto> getPosts(){
        return posts;
    }

    public boolean isHasMore(){
        return hasMore;
    }
}
