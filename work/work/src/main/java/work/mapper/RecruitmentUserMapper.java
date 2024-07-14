package work.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import work.entity.Company;
import work.entity.RecruitmentUser;

import java.util.List;

@Mapper
public interface RecruitmentUserMapper {
    @Select("select * from recruitmentuser where username = #{name}")
    public RecruitmentUser getUserByName(String name);
    void addUser(RecruitmentUser user);
    void deleteRecruitment(List<String> ids);
    void updateRecruitment(RecruitmentUser user);
    List<RecruitmentUser> getAllRecruitment(int start,int end);



}
