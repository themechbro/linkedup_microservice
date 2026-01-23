package com.example.Linkedup.controller;

// import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Linkedup.dto.FeedPostDto;
// import com.example.Linkedup.dto.ApiResponse;
import com.example.Linkedup.entity.Post;
import com.example.Linkedup.service.FeedService;

@RestController
@RequestMapping("/api/feed")
public class FeedController {
    private final FeedService feedService;

public FeedController(FeedService feedService){
    this.feedService=feedService;
}

@PostMapping
public ResponseEntity<Map<UUID, List<FeedPostDto>>> getFeed(
        @RequestBody List<UUID> connectionIds) {

    Map<UUID, List<FeedPostDto>> feed =
        feedService.getFeedForConnections(connectionIds);

    return ResponseEntity.ok(feed);
}

}
