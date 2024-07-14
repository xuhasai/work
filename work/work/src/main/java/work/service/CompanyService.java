package work.service;

import work.entity.Company;
import work.mapper.CompanyMapper;

import javax.annotation.Resource;
import java.util.List;

public interface CompanyService {
    void addCompany(Company company);
    List<Company> getAllCompany(int start, int end,String recruitmentuserId);
    void deleteCompany(List<String> ids);
    void updateCompany(Company company);
}
