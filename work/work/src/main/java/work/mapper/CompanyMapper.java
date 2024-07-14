package work.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import work.entity.Company;

import java.util.List;

@Mapper
public interface CompanyMapper {
    void addCompany(Company company);
    List<Company> getAllCompany(int start,int end,String recruitmentuserId);
    void deleteCompany(List<String> ids);
    void updateCompany(Company company);
}
