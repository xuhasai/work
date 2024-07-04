package work.mapper;

import work.entity.JobSeekersUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface JobSeekersUserLoginMapper {

    @Select("select * from jobseekers where username = #{name}")
    public JobSeekersUser getUserByName(String name);


}
