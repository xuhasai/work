package work.config;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import work.entity.AdministratorUser;
import work.entity.ResutSet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import work.service.UserDetailsServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceFactory userDetailsServiceFactory;

    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(loginType -> userDetailsServiceFactory.getUserDetailsService(loginType).loadUserByUsername(loginType))
                .passwordEncoder(new BCryptPasswordEncoder());
    }*/


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    //处理用户登录
    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsServiceFactory.getUserDetailsService("1"))
                .passwordEncoder(new BCryptPasswordEncoder());
    }*/


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/perform_login").permitAll()
                //.antMatchers("/login3").hasRole("bbb")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login.html")
                //登陆成功
                .successHandler(this::loginSuccessHandler)
                .failureHandler(this::loginFailureHandler)
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                //退出登录成功
                .logoutSuccessHandler(this::logoutSuccess);
    }

    //登陆成功
    public void loginSuccessHandler(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        System.out.println("登录成功");
    }

    //登陆失败
    public void loginFailureHandler(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        System.out.println("登录失败");
    }

    //退出登录成功
    public void logoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        response.setContentType("application/json;charset=utf8");
        ResutSet<AdministratorUser> resutSet = new ResutSet<AdministratorUser>();
        resutSet.setMessage("退出登录成功");
        resutSet.setStatus("200");
        resutSet.setData(null);
        response.getWriter().print(resutSet);
        System.out.println("退出登录成功");
    }




}
