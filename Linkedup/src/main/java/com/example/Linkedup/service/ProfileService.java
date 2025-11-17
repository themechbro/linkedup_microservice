package com.example.Linkedup.service;

import com.example.Linkedup.entity.Profile;
import com.example.Linkedup.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository repo;

    public Profile getProfileByUserId(UUID userId) {
        return repo.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found for user_id: " + userId));
    }
}

