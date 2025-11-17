package com.example.Linkedup.controller;

import com.example.Linkedup.entity.Profile;
import com.example.Linkedup.service.ProfileService;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/{user_id}")
    public ResponseEntity<?> getProfile(@PathVariable("user_id") UUID userId) {

        Profile profile = profileService.getProfileByUserId(userId);

        return ResponseEntity.ok(profile);
    }
}



