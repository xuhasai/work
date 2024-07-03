package work.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceFactory {

    @Autowired
    private AdministratorUserService userTable1Service;


    public UserDetailsService getUserDetailsService(String loginType) {
        if ("admin".equals(loginType)) {
            return userTable1Service;
        } else if ("table2".equals(loginType)) {
            return new UserDetailsService() {
                @Override
                public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                    return null;
                }
            };
        } else {
            return null;
        }
    }


}
