package com.example.Linkedup.dto;

import java.time.Instant;
import java.util.UUID;

import com.example.Linkedup.entity.Jobs;

import lombok.Data;

@Data
public class JobDto {
    private UUID jobId;
    private String title;
    private String company;
    private String location;
    private String jobType;
    private String description;
    private UUID postedBy;
    private Boolean isBrand;
    private Instant createdAt;
    private Instant updatedAt;
    private String status;
    private String applyLink;
    private int applyCount;



    // 


    public JobDto(Jobs job){
        this.jobId= job.getJobId();
        this.title= job.getTitle();
        this.company= job.getCompany();
        this.location= job.getLocation();
        this.jobType= job.getJobType();
        this.description= job.getDescription();
        this.postedBy= job.getPostedBy();
        this.isBrand= job.getIsBrand();
        this.createdAt= job.getCreatedAt();
        this.updatedAt= job.getUpdatedAt();
        this.status= job.getStatus();
        this.applyLink= job.getApplyLink();
        this.applyCount= job.getApplyCount();

    }
}
