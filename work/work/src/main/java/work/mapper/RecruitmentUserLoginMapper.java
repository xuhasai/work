package work.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import work.entity.RecruitmentUser;

@Mapper
public interface RecruitmentUserLoginMapper {
    @Select("select * from recruitmentuser where username = #{name}")
    public RecruitmentUser getUserByName(String name);
}
