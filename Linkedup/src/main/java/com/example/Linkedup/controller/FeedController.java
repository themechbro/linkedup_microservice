package com.example.Linkedup.controller;

// import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Linkedup.dto.FeedPostDto;
import com.example.Linkedup.dto.FeedResponseDto;
// import com.example.Linkedup.dto.ApiResponse;
import com.example.Linkedup.entity.Post;
import com.example.Linkedup.service.FeedService;

@RestController
@RequestMapping("/api/feed")
public class FeedController {

    private final FeedService feedService;

    public FeedController(FeedService feedService) {
        this.feedService = feedService;
    }

     @PostMapping
    public ResponseEntity<List<FeedPostDto>> getFeed(
            @RequestBody List<UUID> connectionIds,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "0") int offset
    ) {

        List<FeedPostDto> feed =
                feedService.getFeedForConnections(connectionIds, limit, offset);

        return ResponseEntity.ok(feed);
    }


 @PostMapping("/latest")
public ResponseEntity<FeedPostDto> getLatestFeedPost(
        @RequestBody List<UUID> connectionIds
) {
    FeedPostDto dto = feedService.getLatestPostId(connectionIds);
    return ResponseEntity.ok(dto);
}


@GetMapping("/fetch-posts-brands")
public ResponseEntity<FeedResponseDto> fetchPostsBrands(

            @RequestParam UUID owner,

            @RequestParam(defaultValue = "0")
            int offset,

            @RequestParam(defaultValue = "10")
            int limit

    ) {

        FeedResponseDto response =
                feedService.getfeedofBrands(
                        owner,
                        offset,
                        limit
                );

        return ResponseEntity.ok(response);
    }
}
