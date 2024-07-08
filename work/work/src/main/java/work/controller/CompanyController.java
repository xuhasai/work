package work.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import work.entity.Company;
import work.service.CompanyService;

import javax.annotation.Resource;
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


}
