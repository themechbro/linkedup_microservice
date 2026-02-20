package com.example.Linkedup.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.Linkedup.dto.JobWithProfileDto;
import com.example.Linkedup.entity.Jobs;
import com.example.Linkedup.entity.Profile;
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



public List <JobWithProfileDto> ThreeJobsforBrand(UUID postedBy){
  Pageable limit= PageRequest.of(0, 3);

List <Object[]> results= repo.findJobsByBrandProfilePage(postedBy, limit);

return results.stream().map(row-> {
  Jobs job= (Jobs) row[0];
  Profile profile= (Profile) row[1];
  return new JobWithProfileDto(job, profile);
}).toList();
}
}
