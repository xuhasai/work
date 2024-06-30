package hasai.config;

import hasai.entity.AdministratorUser;
import hasai.entity.ResutSet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {




    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                //.antMatchers("/login3").hasRole("bbb")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login.html")
                //登陆成功
                .successHandler(this::loginSuccessHandler)
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

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


}
