package work.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import work.entity.JobSeekersUser;
import work.mapper.JobSeekersUserLoginMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
@Service
public class JobSeekersUserLoginService {

    @Autowired
    JobSeekersUserLoginMapper jobLoginMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    public UserDetails loadUserByUsername(String username,String password) throws UsernameNotFoundException {
        JobSeekersUser user = jobLoginMapper.getUserByName(username);
        if (user!=null) {
            if(passwordEncoder.matches(password,user.getPassword())){
                ArrayList<String> arr = new ArrayList<String>();
                arr.add(user.getPermissions());
                return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),getAuthorities(user.getPermissions()));
            }else{
                throw new UsernameNotFoundException("用户名密码错误");
            }
        }else {
            throw new UsernameNotFoundException("用户名密码错误");
        }
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_" + role));
    }
}
