package work.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import work.entity.AdministratorUser;
import work.mapper.AdministratorUserMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;


@Service
public class AdministratorUserService implements UserDetailsService {
    @Autowired
    private AdministratorUserMapper userTable1Repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdministratorUser user = userTable1Repository.getUserByName(username);
        if (user==null) {
            throw new UsernameNotFoundException("User not found");
        }
        ArrayList<String> arr = new ArrayList<String>();
        arr.add("ROLE_admin");
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),getAuthorities("admin"));
    }


    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_" + role));
    }


}
