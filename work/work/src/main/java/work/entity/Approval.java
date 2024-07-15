package work.entity;

import lombok.Data;

@Data
public class Approval {
    private String id;
    private String companyId;
    private String recruitmentId;
    private String jobseekersId;
    private String status;
}
