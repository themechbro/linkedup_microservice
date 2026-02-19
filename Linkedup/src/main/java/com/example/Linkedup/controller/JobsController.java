package com.example.Linkedup.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.Linkedup.dto.JobDto;


import com.example.Linkedup.service.JobsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/jobs_micro")
@RequiredArgsConstructor
public class JobsController {
    private final JobsService jobService;

    @PostMapping("/jobApplyCount/{jobId}")
    public ResponseEntity <?> applyCounter(@PathVariable UUID jobId){
        int count= jobService.applyCounter(jobId);
        return ResponseEntity.ok(count);
    }


    @GetMapping("/get_jobs_for_brand_page/{postedBy}")
    public ResponseEntity<?> fetch3JobsforBrandPage(@PathVariable UUID postedBy){
        List <JobDto> jobs= jobService.ThreeJobsforBrand(postedBy);

        return ResponseEntity.ok(jobs);

    }
}
