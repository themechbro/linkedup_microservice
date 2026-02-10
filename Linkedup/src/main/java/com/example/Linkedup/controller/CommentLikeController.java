package com.example.Linkedup.controller;
import com.example.Linkedup.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;


@RestController //Means this class handles HTTP requests (like app.get, app.post in Express).
@RequestMapping("/api/comments") //@RequestMapping("/api/comments") All routes start with:
@RequiredArgsConstructor

public class CommentLikeController {
    private final CommentLikeService service;

   @PostMapping("/{comment_id}/like")
public ResponseEntity<Map<String, Integer>> like(@PathVariable UUID comment_id) {
    int count = service.like(comment_id);
    return ResponseEntity.ok(Map.of("likes", count));
}

@PostMapping("/{comment_id}/unlike")
public ResponseEntity<Map<String, Integer>> unlike(@PathVariable UUID comment_id) {
    int count = service.unlike(comment_id);
    return ResponseEntity.ok(Map.of("likes", count));
}

@GetMapping("/{comment_id}/likes")
public ResponseEntity<Map<String, Integer>> getLikes(@PathVariable UUID comment_id) {
    int count = service.getLikes(comment_id);
    return ResponseEntity.ok(Map.of("Total Likes", count));
}

@GetMapping("/{postId}/length")
public ResponseEntity <Integer> getCommentLengthforSpecificPost(@PathVariable UUID postId){
    int length= service.commentLength(postId);
    return ResponseEntity.ok(length);
}

}


//@PathVariable Binds {commentId} from the URL.