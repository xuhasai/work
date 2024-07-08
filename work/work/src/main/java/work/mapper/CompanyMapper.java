package work.mapper;

import org.apache.ibatis.annotations.Mapper;
import work.entity.Company;
@Mapper
public interface CompanyMapper {
    void addCompany(Company company);
}
