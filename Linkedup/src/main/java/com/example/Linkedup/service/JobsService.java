package com.example.Linkedup.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.Linkedup.dto.JobDto;
import com.example.Linkedup.entity.Jobs;
import com.example.Linkedup.repository.JobsRepository;
import com.example.Linkedup.repository.ProfileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobsService {
    private final JobsRepository repo;
    private final ProfileRepository repo2; 

  public Jobs getJobByJobID(UUID jobId){
   return repo.findByJobId(jobId).orElseThrow(()-> new RuntimeException("Job not found"));
  }

  public int applyCounter(UUID jobId){
    Jobs job= repo.findById(jobId).orElseThrow(()-> new RuntimeException("Job not found"));

    Integer currCount= job.getApplyCount();

    if(currCount==null) currCount=0;

job.setApplyCount(job.getApplyCount()+1);
repo.save(job);

return job.getApplyCount();
}



public List <JobDto> ThreeJobsforBrand(UUID postedBy){


List <Jobs> threeJob= repo.find3JobsByBrandProfilePage(postedBy);

return threeJob.stream().map(job->{
  JobDto dto= new JobDto(job);
  return dto;
}).toList();

}
}
