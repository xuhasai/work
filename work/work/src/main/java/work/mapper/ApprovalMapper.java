package work.mapper;

import org.apache.ibatis.annotations.Mapper;
import work.entity.Approval;

import java.util.List;

@Mapper
public interface ApprovalMapper {
    void addApproval(Approval approval);
    List<Approval> getApproval(Approval approval);
}
