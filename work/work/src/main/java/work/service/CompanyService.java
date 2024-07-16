package work.service;

import work.entity.Approval;
import work.entity.Company;
import work.entity.SearchCompany;

import java.util.List;

public interface CompanyService {
    void addCompany(Company company);
    List<Company> getAllCompany(int start, int end,String recruitmentuserId);
    void deleteCompany(List<String> ids);
    void updateCompany(Company company);
    List<Company> searchCompany(SearchCompany searchCompany);
    List<Company> getAllCompanyByStatus(Approval approval);
    List<Company> getCompanyByApproval(Approval approval);
    List<Company> getCompanyByApprovalAndJobseekers(Approval approval);
}
