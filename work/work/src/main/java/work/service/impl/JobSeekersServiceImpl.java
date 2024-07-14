package work.service.impl;

import org.springframework.stereotype.Service;
import work.entity.JobSeekersUser;
import work.entity.RecruitmentUser;
import work.mapper.JobSeekersUserMapper;
import work.service.JobSeekersService;

import javax.annotation.Resource;
import java.util.List;
@Service
public class JobSeekersServiceImpl implements JobSeekersService {

    @Resource
    JobSeekersUserMapper jobSeekersUserMapper;

    @Override
    public List<JobSeekersUser> getAllJobSeekers(int start, int end) {
        return jobSeekersUserMapper.getAllJobSeekers(start,end);
    }

    @Override
    public void deleteJobSeekers(List<String> ids) {
        jobSeekersUserMapper.deleteJobSeekers(ids);
    }

    @Override
    public void updateJobSeekers(JobSeekersUser jobSeekersUser) {
        jobSeekersUserMapper.updateJobSeekers(jobSeekersUser);
    }
}
