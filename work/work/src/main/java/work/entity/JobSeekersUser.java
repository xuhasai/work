package work.entity;

import lombok.Data;

@Data
public class JobSeekersUser {
    private String id;
    private String username;
    private String password;
    private String name;
    private String gender;
    private int age;
}
