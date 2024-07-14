package work.service.impl;

import org.springframework.stereotype.Service;
import work.entity.Company;
import work.entity.SearchCompany;
import work.mapper.CompanyMapper;
import work.service.CompanyService;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Resource
    private CompanyMapper companyMapper;
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

}
