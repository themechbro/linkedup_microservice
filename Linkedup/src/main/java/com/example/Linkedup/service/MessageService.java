package com.example.Linkedup.service;

import java.util.Arrays;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.Linkedup.entity.Message;
import com.example.Linkedup.repository.MessageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository repo;

    public Message getMessageByMessageId(UUID messageID){
        return repo.findByMessageId(messageID).orElseThrow(()-> new RuntimeException("Message not found"+ messageID));
    }

  public int like(UUID messageID, UUID likedByID) {

    Message message = repo.findById(messageID)
        .orElseThrow(() -> new RuntimeException("Message not found"));

    Integer currentLikes = message.getLikes();
    if (currentLikes == null) {
        currentLikes = 0;
    }

    UUID[] existing = message.getLikedBy();
    if (existing == null) {
        existing = new UUID[0];
    }

    // prevent duplicate likes
    for (UUID id : existing) {
        if (id.equals(likedByID)) {
            return currentLikes;
        }
    }

    UUID[] updated = Arrays.copyOf(existing, existing.length + 1);
    updated[existing.length] = likedByID;

    message.setLikedBy(updated);
    message.setLikes(currentLikes + 1);

    repo.save(message);
    return message.getLikes();
}



}
