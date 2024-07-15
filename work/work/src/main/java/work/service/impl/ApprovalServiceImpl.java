package work.service.impl;

import org.springframework.stereotype.Service;
import work.entity.Approval;
import work.mapper.ApprovalMapper;
import work.service.ApprovalService;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Service
public class ApprovalServiceImpl implements ApprovalService {

    @Resource
    ApprovalMapper approvalMapper;


    @Override
    public void addApproval(Approval approval) {
        List<Approval> approvalList = approvalMapper.getApproval(approval);
        if(approvalList == null || approvalList.isEmpty()){
            String uuid = UUID.randomUUID().toString();
            approval.setId(uuid);
            approvalMapper.addApproval(approval);
        }
    }
}
