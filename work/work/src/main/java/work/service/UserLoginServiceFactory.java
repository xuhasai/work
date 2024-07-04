package work.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserLoginServiceFactory {

    @Autowired
    AdministratorUserLoginService adminLoginService;

    @Autowired
    JobSeekersUserLoginService jobLoginService;

    public UserDetails getUserDetails(String username,String password, String loginType) {
        if("admin".equals(loginType)){
            return adminLoginService.loadUserByUsername(username,password);
        }else if("jobseekers".equals(loginType)){
            return jobLoginService.loadUserByUsername(username,password);
        }else if("recruitment".equals(loginType)){
            return null;
        }else {
            return null;
        }
    }



}
