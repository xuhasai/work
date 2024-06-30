package hasai.mapper;

import hasai.entity.AdministratorUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdministratorUserMapper {

    @Select("select * from administratoruser where username = #{name}")
    public AdministratorUser getUserByName(String name);


}
