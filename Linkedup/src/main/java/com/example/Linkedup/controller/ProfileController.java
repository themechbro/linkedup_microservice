package com.example.Linkedup.controller;

import com.example.Linkedup.dto.ApiResponse;
import com.example.Linkedup.entity.Profile;
import com.example.Linkedup.service.ProfileService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> updateWebsite(@PathVariable("user_id") UUID userId, @RequestBody Map<String, String> body, HttpServletRequest request) {
        
        String jwtUserId= (String) request.getAttribute("internalUserId");
        if (!userId.toString().equals(jwtUserId)) {
        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(new ApiResponse(false, "User mismatch", Instant.now()));
    }

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
public ResponseEntity<?> updateIndustry(
    @PathVariable("user_id") UUID userId,
    @RequestBody Map<String, String> body,
    HttpServletRequest request
) {
    String jwtUserId = (String) request.getAttribute("internalUserId");

    if (!userId.toString().equals(jwtUserId)) {
        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(new ApiResponse(false, "User mismatch", Instant.now()));
    }

    Profile profile = profileService.getProfileByUserId(userId);

    if (!Boolean.TRUE.equals(profile.getIsBrand())) {
        return ResponseEntity.badRequest()
            .body(new ApiResponse(false, "User not a Brand", Instant.now()));
    }

    String industry = body.get("industry");

    if (industry == null || industry.trim().isEmpty()) {
        return ResponseEntity.badRequest()
            .body(new ApiResponse(false, "Industry cannot be empty", Instant.now()));
    }

    profile.setInduStry(industry);
    profileService.updateProfile(profile);

    return ResponseEntity.ok(
        new ApiResponse(true, "Industry Updated Successfully", Instant.now())
    );
}

@PostMapping("/update-about/{user_id}")
public ResponseEntity<?> updateAbout(
    @PathVariable("user_id") UUID userId,
    @RequestBody Map<String, String> body,
    HttpServletRequest request
){
        String jwtUserId = (String) request.getAttribute("internalUserId");
 if (!userId.toString().equals(jwtUserId)) {
        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(new ApiResponse(false, "User mismatch", Instant.now()));
    }

    Profile profile = profileService.getProfileByUserId(userId);
    
    String about= body.get("about");
if (about == null || about.trim().isEmpty()) {
        return ResponseEntity.badRequest()
            .body(new ApiResponse(false, "About cannot be empty", Instant.now()));
    }

    profile.setAbOut(about);
    profileService.updateProfile(profile);

    return ResponseEntity.ok(
        new ApiResponse(true, "About Update Successfully", Instant.now())
    );

}

@PostMapping("/update-companyhq/{user_id}")
public ResponseEntity<?> updateCompanyHq(
    @PathVariable("user_id") UUID userId,
    @RequestBody Map <String, String> body,
    HttpServletRequest request
){
    String jwtUserId = (String) request.getAttribute("internalUserId");
    if (!userId.toString().equals(jwtUserId)) {
        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(new ApiResponse(false, "User mismatch", Instant.now()));
    }
 Profile profile = profileService.getProfileByUserId(userId);
 if (!Boolean.TRUE.equals(profile.getIsBrand())) {
        return ResponseEntity.badRequest()
            .body(new ApiResponse(false, "User not a Brand", Instant.now()));
    }

    String companySize= body.get("companysize");
    String headQuarters= body.get("hq");

    if ((companySize == null || companySize.trim().isEmpty()) && (headQuarters==null||headQuarters.trim().isEmpty())) {
        return ResponseEntity.badRequest()
            .body(new ApiResponse(false, "Company size and Headquarters must be filled", Instant.now()));
    }
    profile.setCompanySize(companySize);
    profile.setHeadQuarters(headQuarters);
    profileService.updateProfile(profile);

    return ResponseEntity.ok(
        new ApiResponse(true, "Company size and Headquarters Updated Successfully", Instant.now())
    );
}

}





