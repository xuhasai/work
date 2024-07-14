package work.service;

import work.entity.JobSeekersUser;

import java.util.List;

public interface JobSeekersService {
    List<JobSeekersUser> getAllJobSeekers(int start, int end);
    void deleteJobSeekers(List<String> ids);
    void updateJobSeekers(JobSeekersUser jobSeekersUser);
}
