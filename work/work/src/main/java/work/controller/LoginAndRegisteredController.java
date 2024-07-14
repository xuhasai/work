package work.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.*;
import work.entity.ResutSet;
import work.mapper.AdministratorUserLoginMapper;
import work.mapper.JobSeekersUserMapper;
import work.mapper.RecruitmentUserMapper;
import work.service.AdministratorUserLoginService;
import work.service.JobSeekersUserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import work.service.RecruitmentUserLoginService;
import work.service.UserLoginServiceFactory;
import work.util.JWTUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginAndRegisteredController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    AdministratorUserLoginMapper administratorUserLoginMapper;

    @Autowired
    JobSeekersUserMapper jobSeekersUserMapper;

    @Resource
    RecruitmentUserMapper recruitmentUserMapper;

    @Autowired
    UserLoginServiceFactory userLoginServiceFactory;

    @Autowired
    AdministratorUserLoginService adminLoginService;

    @Autowired
    JobSeekersUserLoginService jobLoginService;

    @Autowired
    RecruitmentUserLoginService recruitmentLoginService;

    @Resource
    JWTUtil jwtUtil;


    /*@PostMapping("/login")
    @ResponseBody
    public ResutSet login(HttpServletRequest request, HttpServletResponse response, @RequestParam("username")String username, @RequestParam("password")String password, @RequestParam("loginType")String loginType){
        if("admin".equals(loginType)){
            AdministratorUser administratorUser =  administratorUserMapper.getUserByName(username);
            if(administratorUser != null){
                if(!password.isEmpty() && password.length()>=1){
                    String dncryptPassword = DecryptPassword.decryptPassword(administratorUser.getPassword());
                    if(dncryptPassword.equals(password)){
                        SecurityContext context = SecurityContextHolder.getContext();
                        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(administratorUser, null,
                                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_admin"));  //手动创建一个UsernamePasswordAuthenticationToken对象，也就是用户的认证信息，角色需要添加ROLE_前缀，权限直接写
                        token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        //Authentication authentication = authenticationManager.authenticate(token);
                        context.setAuthentication(token);  //手动为SecurityContext设定认证信息
                        ResutSet<AdministratorUser> resutSet = new ResutSet<AdministratorUser>();
                        resutSet.setMessage("登录成功");
                        resutSet.setStatus("200");
                        resutSet.setData(administratorUser);
                        return resutSet;
                    }
                }
            }else{
                return null;
            }
        }else if("jobseekers".equals(loginType)){
            return null;
        } else if ("recruitment".equals(loginType)) {
            return null;
        }else {
            return null;
        }
        return null;
        *//*SecurityContext context = SecurityContextHolder.getContext();  //获取SecurityContext对象（当前会话肯定是没有登陆的）
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("Test", null,
                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_admin"));  //手动创建一个UsernamePasswordAuthenticationToken对象，也就是用户的认证信息，角色需要添加ROLE_前缀，权限直接写
        token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        //Authentication authentication = authenticationManager.authenticate(token);
        context.setAuthentication(token);  //手动为SecurityContext设定认证信息*//*
    }*/


    @PostMapping("/login")
    @ResponseBody
    public ResutSet login(HttpServletRequest request,@RequestParam String username, @RequestParam String password, @RequestParam String loginType) {
        try {
            ResutSet resutSet = new ResutSet();
            UserDetails userDetails = null;
            if("admin".equals(loginType)){
                userDetails = adminLoginService.loadUserByUsername(username,password);
                resutSet.setData(administratorUserLoginMapper.getUserByName(username));
            }else if("jobseekers".equals(loginType)){
                userDetails = jobLoginService.loadUserByUsername(username,password);
                resutSet.setData(jobSeekersUserMapper.getUserByName(username));
            }else if("recruitment".equals(loginType)){
                userDetails = recruitmentLoginService.loadUserByUsername(username,password);
                resutSet.setData(recruitmentUserMapper.getUserByName(username));
            }else {
                resutSet.setMessage("用户名密码错误");
                resutSet.setStatus(HttpStatus.UNAUTHORIZED+"");
                resutSet.setData(null);
                return resutSet;
                //return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
            }
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userDetails, "******", userDetails.getAuthorities());
            authRequest.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authRequest);
            String jwt = jwtUtil.createJwt(userDetails);
            resutSet.setMessage("登录成功");
            resutSet.setStatus("200");
            resutSet.setToken(jwt);
            return resutSet;
        } catch (AuthenticationException e ) {
            ResutSet resutSet = new ResutSet();
            resutSet.setMessage(e.getMessage());
            resutSet.setStatus(HttpStatus.UNAUTHORIZED+"");
            resutSet.setData(null);
            return resutSet;
            //return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
        }
    }
    @PostMapping("/registered")
    @ResponseBody
    public ResutSet registered(@RequestBody JSONObject userData) {
        ResutSet resutSet = new ResutSet();
        try {
            if("jobseekers".equals(userData.get("permissions"))){
                jobLoginService.registeredJobUser(userData);
            }else if("recruitment".equals(userData.get("permissions"))){
                recruitmentLoginService.registeredRecruitmentUser(userData);
            }else{
                resutSet.setMessage("注册失败");
                resutSet.setStatus("500");
                return resutSet;
            }
            resutSet.setMessage("注册成功");
            resutSet.setStatus("200");
            return resutSet;
        } catch (AuthenticationException e ) {
            resutSet.setMessage(e.getMessage());
            resutSet.setStatus("500");
            return resutSet;
            //return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
        }

    }

    @RequestMapping("/login3")
    public String login3(HttpServletRequest request, HttpServletResponse response){
        System.out.println("11111111111111111111111111111111");
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        //User user = (User)authentication.getPrincipal();
        return "/logout.html";
    }





}
