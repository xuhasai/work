package work.config;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import work.entity.AdministratorUser;
import work.entity.ResutSet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import work.filter.JwtAuthenticationFilter;
import work.util.JWTUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @Resource
    JWTUtil jwtUtil;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


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
                .antMatchers("/login").permitAll()
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
                .logoutSuccessHandler(this::logoutSuccess)
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
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
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication2 = context.getAuthentication();
        response.setContentType("application/json;charset=utf8");
        String authorization = request.getHeader("Authorization");
        if(authorization != null && !authorization.isEmpty() && authorization.length()>0){
            ResutSet resutSet = new ResutSet();
            if(jwtUtil.signOut(authorization)){
                resutSet.setMessage("退出登录成功");
                resutSet.setStatus("200");
                resutSet.setData(null);
                response.getWriter().print(resutSet);
            }else{
                resutSet.setMessage("滚");
                resutSet.setStatus("200");
                response.getWriter().print(resutSet);
            }
        }


    }




}
