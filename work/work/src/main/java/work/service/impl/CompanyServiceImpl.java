package work.service.impl;

import org.springframework.stereotype.Service;
import work.entity.Company;
import work.mapper.CompanyMapper;
import work.service.CompanyService;

import javax.annotation.Resource;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Resource
    private CompanyMapper companyMapper;
    @Override
    public void addCompany(Company company) {
        companyMapper.addCompany(company);
    }
}
