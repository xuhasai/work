package work.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import work.entity.Company;
import work.entity.SearchCompany;

import java.util.List;

@Mapper
public interface CompanyMapper {
    void addCompany(Company company);
    List<Company> getAllCompany(int start,int end,String recruitmentuserId);
    void deleteCompany(List<String> ids);
    void updateCompany(Company company);
    List<Company> searchCompany(SearchCompany searchCompany);
    List<Company> getCompanyByIds(int start,int end,List<String> ids);
    Company getCompanyById(String id);

}
