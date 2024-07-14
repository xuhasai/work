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
import work.entity.RecruitmentUser;
import work.mapper.RecruitmentUserMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

@Service
public class RecruitmentUserLoginService {

    @Autowired
    RecruitmentUserMapper recruitmentMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    public UserDetails loadUserByUsername(String username,String password) throws UsernameNotFoundException {
        RecruitmentUser user = recruitmentMapper.getUserByName(username);
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

    public void registeredRecruitmentUser(JSONObject userData) throws UsernameNotFoundException {
        if (userData.get("username") !=null && userData.get("password") != null && !userData.get("username").toString().isEmpty() && !userData.get("password").toString().isEmpty()) {
            if(recruitmentMapper.getUserByName(userData.get("username").toString()) == null){
                String uuid = UUID.randomUUID().toString();
                RecruitmentUser recruitmentUser = new RecruitmentUser();
                recruitmentUser.setId(uuid);
                recruitmentUser.setUsername(userData.get("username").toString());
                recruitmentUser.setPassword(passwordEncoder.encode(userData.get("password").toString()));
                recruitmentUser.setName(userData.get("name").toString());
                if(userData.get("gender").toString().equals("nan")){
                    recruitmentUser.setGender("男");
                }else if(userData.get("gender").toString().equals("nv")){
                    recruitmentUser.setGender("女");
                }
                recruitmentUser.setAge(Integer.parseInt(userData.get("age").toString()));
                recruitmentUser.setPermissions("recruitment");
                recruitmentMapper.addUser(recruitmentUser);
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
