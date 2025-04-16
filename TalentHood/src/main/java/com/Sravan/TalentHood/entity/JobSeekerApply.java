package com.Sravan.TalentHood.entity;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"userId", "job"})
})
public class JobSeekerApply implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId", referencedColumnName = "user_account_id")
    private JobSeekerProfile userId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "job", referencedColumnName = "jobPostId")
    private JobPostActivity job;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date applyDate;


    private String coverLetter;


    public JobSeekerApply() {
    }

    public JobSeekerApply(Date applyDate, String coverLetter, Integer id, JobPostActivity job, JobSeekerProfile userId) {
        this.applyDate = applyDate;
        this.coverLetter = coverLetter;
        this.id = id;
        this.job = job;
        this.userId = userId;
    }

    public Date getapplyDate() {
        return applyDate;
    }

    public void setapplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public String getCoverLetter() {
        return coverLetter;
    }

    public void setCoverLetter(String coverLetter) {
        this.coverLetter = coverLetter;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public JobPostActivity getJob() {
        return job;
    }

    public void setJob(JobPostActivity job) {
        this.job = job;
    }

    public JobSeekerProfile getUserId() {
        return userId;
    }

    public void setUserId(JobSeekerProfile userId) {
        this.userId = userId;
    }


    @Override
    public String toString() {
        return "JobSeekerApply{" +
                "applyDate=" + applyDate +
                ", id=" + id +
                ", userId=" + userId +
                ", job=" + job +
                ", coverLetter='" + coverLetter + '\'' +
                '}';
    }
}
