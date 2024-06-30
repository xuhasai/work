package hasai.mapper;

import hasai.entity.JobSeekersUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface JobSeekersMapper {

    @Select("select * from jobseekers where username = #{name}")
    public JobSeekersUser getUserByName(String name);


}
