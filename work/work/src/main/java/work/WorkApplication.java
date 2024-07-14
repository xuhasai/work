package work;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class WorkApplication {

    public static void main(String[] args) {
        PasswordEncoder pass = new BCryptPasswordEncoder();
        System.out.println(pass.encode("123456"));
        SpringApplication.run(WorkApplication.class, args);
    }

}
