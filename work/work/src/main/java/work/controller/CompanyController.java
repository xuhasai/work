package work.controller;

import org.springframework.web.bind.annotation.*;
import work.entity.Company;
import work.service.CompanyService;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@RestController
public class CompanyController {

    @Resource
    private CompanyService companyService;

    @RequestMapping("/addCompany")
    public void addCompany(@RequestBody Company company){
        String uuid = UUID.randomUUID().toString();
        company.setId(uuid);
        companyService.addCompany(company);
    }

    @RequestMapping("/getAllCompany")
    public List<Company> getAllCompany(int start,int end){
        return companyService.getAllCompany(start,end);
    }


    @RequestMapping("/deleteCompany")
    public void deleteCompany(@RequestBody List<String> deleteId){
        if(deleteId.size()>0){
            companyService.deleteCompany(deleteId);
        }
    }

    @RequestMapping("/updateCompany")
    public void updateCompany(@RequestBody Company company){
        if(company.getId()!=null && company.getId().length()>0){
            companyService.updateCompany(company);
        }
    }




}
