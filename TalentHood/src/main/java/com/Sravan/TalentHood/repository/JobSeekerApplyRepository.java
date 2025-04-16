package com.Sravan.TalentHood.repository;

import com.Sravan.TalentHood.entity.JobPostActivity;
import com.Sravan.TalentHood.entity.JobSeekerApply;
import com.Sravan.TalentHood.entity.JobSeekerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobSeekerApplyRepository extends JpaRepository<JobSeekerApply, Integer> {

    List<JobSeekerApply> findByUserId(JobSeekerProfile userId);

    List<JobSeekerApply> findByJob(JobPostActivity job);

}
