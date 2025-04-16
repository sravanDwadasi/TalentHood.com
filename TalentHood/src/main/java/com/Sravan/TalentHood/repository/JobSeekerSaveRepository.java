package com.Sravan.TalentHood.repository;

import com.Sravan.TalentHood.entity.JobPostActivity;
import com.Sravan.TalentHood.entity.JobSeekerProfile;
import com.Sravan.TalentHood.entity.JobSeekerSave;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobSeekerSaveRepository extends JpaRepository<JobSeekerSave, Integer> {

    public List<JobSeekerSave> findByUserId(JobSeekerProfile userAccountId);

    List<JobSeekerSave> findByJob(JobPostActivity job);
}
