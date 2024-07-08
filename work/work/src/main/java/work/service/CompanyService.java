package work.service;

import work.entity.Company;
import work.mapper.CompanyMapper;

import javax.annotation.Resource;

public interface CompanyService {
    void addCompany(Company company);
}
