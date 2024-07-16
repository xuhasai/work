package work.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import work.entity.Approval;
import work.service.ApprovalService;

import javax.annotation.Resource;

@RestController
public class ApprovalController {

    @Resource
    ApprovalService approvalService;

    @RequestMapping("/addApproval")
    public void addApproval(@RequestBody Approval approval) {
        approvalService.addApproval(approval);
    }

    @RequestMapping("/updateApproval")
    public void updateApproval(@RequestBody Approval approval) {
        approvalService.updateApproval(approval);
    }


}
