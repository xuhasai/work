package work.service.impl;

import org.springframework.stereotype.Service;
import work.entity.Approval;
import work.entity.Company;
import work.entity.SearchCompany;
import work.mapper.ApprovalMapper;
import work.mapper.CompanyMapper;
import work.service.CompanyService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Resource
    private CompanyMapper companyMapper;

    @Resource
    ApprovalMapper approvalMapper;


    @Override
    public void addCompany(Company company) {
        companyMapper.addCompany(company);
    }

    @Override
    public List<Company> getAllCompany(int start, int end,String recruitmentuserId) {
        return companyMapper.getAllCompany(start, end,recruitmentuserId);
    }

    @Override
    public void deleteCompany(List<String> ids) {
        companyMapper.deleteCompany(ids);
    }

    @Override
    public void updateCompany(Company company) {
        companyMapper.updateCompany(company);
    }

    @Override
    public List<Company> searchCompany(SearchCompany searchCompany) {
        if(searchCompany.getSalary1() != null && searchCompany.getSalary2() != null){
            Double salary1 = searchCompany.getSalary1();
            Double salary2 = searchCompany.getSalary2();
            searchCompany.setSalary1(salary1>salary2?salary2:salary1);
            searchCompany.setSalary2(salary1>salary2?salary1:salary2);
        }
        return companyMapper.searchCompany(searchCompany);
    }

    @Override
    public List<Company> getAllCompanyByStatus(Approval approval) {
        List<Company> allCompany = companyMapper.getAllCompany(approval.getStart(), approval.getEnd(),null);
        List<Approval> approvalList = approvalMapper.getApproval(approval);
        if(allCompany != null && allCompany.size() > 0 && approvalList != null && approvalList.size() > 0){
            for(Company company : allCompany){
                for(Approval approval1 : approvalList){
                    if(company.getId().equals(approval1.getCompanyId())){
                        company.setApproval(true);
                    }
                }
            }
        }
        return allCompany;
    }

    @Override
    public List<Company> getCompanyByApproval(Approval approval) {
        List<Approval> approvalList = approvalMapper.getApproval(approval);
        List<String> ids = new ArrayList<>();
        for (Approval approval1 : approvalList) {
            ids.add(approval1.getCompanyId());
        }
        List<Company> allCompany = companyMapper.getCompanyByIds(approval.getStart(),approval.getEnd(),ids);
        if(allCompany != null && allCompany.size() > 0 && approvalList != null && approvalList.size() > 0){
            for(Company company : allCompany){
                for(Approval approval1 : approvalList){
                    if(company.getId().equals(approval1.getCompanyId())){
                        company.setApproval(true);
                        company.setStatus(approval1.getStatus());
                    }
                }
            }
        }
        return allCompany;
    }

    @Override
    public List<Company> getCompanyByApprovalAndJobseekers(Approval approval) {
        List<Approval> approvalList = approvalMapper.getApproval(approval);
        List<Company> companyList = new ArrayList<>();
        for(Approval approval1 : approvalList){
            Company company = companyMapper.getCompanyById(approval1.getCompanyId());
            if(company != null){
                company.setJobseekersId(approval1.getJobseekersId());
                company.setStatus(approval1.getStatus());
                companyList.add(company);
            }
        }



        return companyList;
    }


}
