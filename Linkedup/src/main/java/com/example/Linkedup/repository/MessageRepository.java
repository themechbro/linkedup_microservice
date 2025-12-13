package com.example.Linkedup.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Linkedup.entity.Message;

public interface MessageRepository extends JpaRepository <Message, UUID> {
Optional <Message> findByMessageId(UUID messageId);
    
}
