package work.entity;

import lombok.Data;

@Data
public class AdministratorUser {
    private String id;
    private String username;
    private String password;
    private String name;
    private String permissions;
}
