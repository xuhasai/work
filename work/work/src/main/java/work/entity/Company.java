package work.entity;

import lombok.Data;

@Data
public class Company {
    private String id;
    private String name;
    private String address;
    private String job;
    private double salary;
    private String detail;
    private String recruitmentuserId;
    private boolean isApproval = false;
    private String status;
    private String jobseekersId;

}
