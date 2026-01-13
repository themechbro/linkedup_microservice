package com.example.Linkedup.controller;

import com.example.Linkedup.dto.ApiResponse;
import com.example.Linkedup.entity.Profile;
import com.example.Linkedup.service.ProfileService;

import lombok.RequiredArgsConstructor;

import java.security.Timestamp;
import java.time.Instant;
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

    @PostMapping("/update-website/{user_id}")
    public ResponseEntity<?> updateWebsite(@PathVariable("user_id") UUID userId, @RequestBody java.util.Map<String, String> body) {
        Profile profile = profileService.getProfileByUserId(userId);
// Check 1
        if (!Boolean.TRUE.equals(profile.getIsBrand())) {
            return ResponseEntity.status(400).body(new ApiResponse(false, "User not a Brand. Bad Gateway", Instant.now()));
        }

        String website = body.get("website");
// Check 2
        if(website==null|| website.trim().isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse(false, "Link field cannot be empty", Instant.now()));
        }
        profile.setWebSite(website.trim());
        profileService.updateProfile(profile);

        return ResponseEntity.ok(new ApiResponse(true, "Website Updated Successfully", Instant.now()));
    }


    @PostMapping("/update-industry/{user_id}")
    public ResponseEntity<?> updateIndustry(@PathVariable("user_id")UUID userId, @RequestBody java.util.Map<String, String> body){
        Profile profile= profileService.getProfileByUserId(userId);
         // Check 1
        if (!Boolean.TRUE.equals(profile.getIsBrand())) {
            return ResponseEntity.status(400).body(new ApiResponse(false, "User not a Brand. Bad Gateway", Instant.now()));
        }

        String industry=body.get("industry");

        // Check 2
        if(industry==null|| industry.trim().isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse(false, "Industry field cannot be empty", Instant.now()));
        }

        profile.setInduStry(industry);
        profileService.updateProfile(profile);

        return ResponseEntity.ok(new ApiResponse(true, "Industry Updated Successfully", Instant.now()));
    }
}





