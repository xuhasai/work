package work.entity;

import lombok.Data;

@Data
public class SearchCompany {
    private String name;
    private String address;
    private String job;
    private Double salary1;
    private Double salary2;
    private int start;
    private int end;
}
