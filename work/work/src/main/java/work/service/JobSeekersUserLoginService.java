package work.service;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import work.entity.JobSeekersUser;
import work.mapper.JobSeekersUserMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

@Service
public class JobSeekersUserLoginService {

    @Autowired
    JobSeekersUserMapper jobLoginMapper;

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


    public void registeredJobUser(JSONObject userData) throws UsernameNotFoundException {
        if (userData.get("username") !=null && userData.get("password") != null && !userData.get("username").toString().isEmpty() && !userData.get("password").toString().isEmpty()) {
            if(jobLoginMapper.getUserByName(userData.get("username").toString()) == null){
                String uuid = UUID.randomUUID().toString();
                JobSeekersUser jobSeekersUser = new JobSeekersUser();
                jobSeekersUser.setId(uuid);
                jobSeekersUser.setUsername(userData.get("username").toString());
                jobSeekersUser.setPassword(passwordEncoder.encode(userData.get("password").toString()));
                jobSeekersUser.setName(userData.get("name").toString());
                if(userData.get("gender").toString().equals("nan")){
                    jobSeekersUser.setGender("男");
                }else if(userData.get("gender").toString().equals("nv")){
                    jobSeekersUser.setGender("女");
                }
                jobSeekersUser.setAge(Integer.parseInt(userData.get("age").toString()));
                jobSeekersUser.setPermissions("jobseekers");
                jobLoginMapper.addUser(jobSeekersUser);
            }else{
                throw new UsernameNotFoundException("用户名已存在");
            }
        }else {
            throw new UsernameNotFoundException("注册失败");
        }
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_" + role));
    }
}
