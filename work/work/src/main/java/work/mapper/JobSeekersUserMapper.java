package work.mapper;

import work.entity.JobSeekersUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import work.entity.RecruitmentUser;

import java.util.List;

@Mapper
public interface JobSeekersUserMapper {

    @Select("select * from jobseekers where username = #{name}")
    JobSeekersUser getUserByName(String name);

    void addUser(JobSeekersUser user);
    void deleteJobSeekers(List<String> ids);
    void updateJobSeekers(JobSeekersUser user);
    List<JobSeekersUser> getAllJobSeekers(int start,int end);


}
