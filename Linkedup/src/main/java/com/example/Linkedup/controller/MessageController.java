package com.example.Linkedup.controller;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Linkedup.service.MessageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/message_micro")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping( value = "/likeMessage/{message_id}",
    consumes = "application/json")
    public ResponseEntity <?> like(@PathVariable UUID message_id,
        @RequestBody Map <String, UUID> body
    ){
            UUID likedBy = body.get("likedBy");

    int count = messageService.like(message_id, likedBy);
    return ResponseEntity.ok(count);
    }
}
